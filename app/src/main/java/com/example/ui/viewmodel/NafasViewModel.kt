package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.BreathingSessionEntity
import com.example.data.local.CravingLogEntity
import com.example.data.local.NafasDatabase
import com.example.data.local.UserPreferencesRepository
import com.example.data.local.WishlistEntity
import com.example.data.localization.AppLanguage
import com.example.data.model.UserProfile
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

enum class BreathingPhase {
  INHALE,
  HOLD,
  EXHALE,
  HOLD_POST
}

class NafasViewModel(application: Application) : AndroidViewModel(application) {

  private val repository = UserPreferencesRepository(application)
  private val database = NafasDatabase.getDatabase(application)
  private val cravingDao = database.cravingDao()
  private val wishlistDao = database.wishlistDao()
  private val breathingDao = database.breathingDao()

  val userProfile: StateFlow<UserProfile> = repository.userProfile

  // Live timer tick
  private val _currentTime = MutableStateFlow(System.currentTimeMillis())
  val currentTime: StateFlow<Long> = _currentTime.asStateFlow()

  // Room flows
  val cravings: StateFlow<List<CravingLogEntity>> = cravingDao.getAllCravings()
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  val cravingCount: StateFlow<Int> = cravingDao.getCravingCount()
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

  val wishlist: StateFlow<List<WishlistEntity>> = wishlistDao.getAllWishlist()
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  val breathingCount: StateFlow<Int> = breathingDao.getSessionCount()
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

  // SOS Breathing State
  private val _isBreathingActive = MutableStateFlow(false)
  val isBreathingActive: StateFlow<Boolean> = _isBreathingActive.asStateFlow()

  private val _breathingPhase = MutableStateFlow(BreathingPhase.INHALE)
  val breathingPhase: StateFlow<BreathingPhase> = _breathingPhase.asStateFlow()

  private val _phaseSecondsLeft = MutableStateFlow(4)
  val phaseSecondsLeft: StateFlow<Int> = _phaseSecondsLeft.asStateFlow()

  private val _phaseTotalSeconds = MutableStateFlow(4)
  val phaseTotalSeconds: StateFlow<Int> = _phaseTotalSeconds.asStateFlow()

  private val _breathingCycle = MutableStateFlow(1)
  val breathingCycle: StateFlow<Int> = _breathingCycle.asStateFlow()

  private val _breathingMode = MutableStateFlow("4-7-8") // "4-7-8" or "box"
  val breathingMode: StateFlow<String> = _breathingMode.asStateFlow()

  private var breathingJob: Job? = null
  private var breathingStartTimestamp: Long = 0

  init {
    // Start 1-second live ticker
    viewModelScope.launch {
      while (isActive) {
        _currentTime.value = System.currentTimeMillis()
        delay(1000)
      }
    }
    // Seed default goals if wishlist is empty
    viewModelScope.launch {
      wishlist.collect { items ->
        if (items.isEmpty() && userProfile.value.hasCompletedOnboarding) {
          seedDefaultWishlist()
        }
      }
    }
  }

  val currentLanguage: AppLanguage
    get() = AppLanguage.fromCode(userProfile.value.languageCode)

  fun setLanguage(lang: AppLanguage) {
    repository.updateLanguage(lang.code)
  }

  fun completeOnboarding(
    quitTimestamp: Long,
    cigsPerDay: Int,
    pricePack: Double,
    cigsPack: Int,
    currency: String,
    langCode: String
  ) {
    val profile = UserProfile(
      hasCompletedOnboarding = true,
      quitTimestampMillis = quitTimestamp,
      cigarettesPerDay = cigsPerDay.coerceAtLeast(1),
      pricePerPack = pricePack.coerceAtLeast(0.1),
      cigarettesPerPack = cigsPack.coerceAtLeast(1),
      currencySymbol = currency,
      languageCode = langCode
    )
    repository.saveProfile(profile)
    viewModelScope.launch {
      seedDefaultWishlist()
    }
  }

  fun updateProfile(
    quitTimestamp: Long,
    cigsPerDay: Int,
    pricePack: Double,
    cigsPack: Int,
    currency: String,
    langCode: String
  ) {
    val updated = userProfile.value.copy(
      quitTimestampMillis = quitTimestamp,
      cigarettesPerDay = cigsPerDay.coerceAtLeast(1),
      pricePerPack = pricePack.coerceAtLeast(0.1),
      cigarettesPerPack = cigsPack.coerceAtLeast(1),
      currencySymbol = currency,
      languageCode = langCode
    )
    repository.saveProfile(updated)
  }

  fun resetCounter() {
    repository.resetCounter()
  }

  fun resetAll() {
    repository.resetAll()
    viewModelScope.launch {
      cravingDao.clearAll()
      wishlistDao.clearAll()
      breathingDao.clearAll()
    }
  }

  // Craving logging
  fun logCraving(intensity: Int, trigger: String, note: String = "") {
    viewModelScope.launch {
      cravingDao.insert(
        CravingLogEntity(
          timestamp = System.currentTimeMillis(),
          intensity = intensity.coerceIn(1, 5),
          trigger = trigger,
          note = note
        )
      )
    }
  }

  // Wishlist actions
  fun addWishlistItem(title: String, targetCost: Double) {
    if (title.isBlank() || targetCost <= 0.0) return
    viewModelScope.launch {
      wishlistDao.insert(
        WishlistEntity(
          title = title.trim(),
          targetCost = targetCost,
          isPurchased = false
        )
      )
    }
  }

  fun toggleWishlistItem(item: WishlistEntity) {
    viewModelScope.launch {
      wishlistDao.update(item.copy(isPurchased = !item.isPurchased))
    }
  }

  fun deleteWishlistItem(item: WishlistEntity) {
    viewModelScope.launch {
      wishlistDao.delete(item)
    }
  }

  private suspend fun seedDefaultWishlist() {
    val currency = userProfile.value.currencySymbol
    val defaultGoals = when (userProfile.value.languageCode) {
      "ar" -> listOf(
        Pair("عمرة إلى بيت الله الحرام 🕋", 1500.0),
        Pair("ساعة ذكية للمشي والرياضة ⌚", 450.0),
        Pair("عشاء فاخر مع العائلة 🍽️", 200.0),
        Pair("هدية بر بالوالدين أو الشريك 🎁", 350.0)
      )
      "fr" -> listOf(
        Pair("Voyage spirituel de l'Omra 🕋", 1500.0),
        Pair("Montre connectée sportive ⌚", 300.0),
        Pair("Dîner d'exception en famille 🍽️", 150.0),
        Pair("Cadeau précieux pour ses proches 🎁", 250.0)
      )
      else -> listOf(
        Pair("Umrah Pilgrimage Trip 🕋", 1500.0),
        Pair("Smart Fitness & Health Watch ⌚", 300.0),
        Pair("Special Dinner with Family 🍽️", 150.0),
        Pair("Cherished Gift for Loved Ones 🎁", 250.0)
      )
    }
    for (goal in defaultGoals) {
      wishlistDao.insert(
        WishlistEntity(
          title = goal.first,
          targetCost = goal.second,
          isPurchased = false
        )
      )
    }
  }

  // SOS Breathing Controls
  fun startBreathing(mode: String = "4-7-8") {
    _breathingMode.value = mode
    _isBreathingActive.value = true
    _breathingCycle.value = 1
    breathingStartTimestamp = System.currentTimeMillis()

    breathingJob?.cancel()
    breathingJob = viewModelScope.launch {
      if (mode == "4-7-8") {
        // 4s Inhale -> 7s Hold -> 8s Exhale
        while (isActive) {
          runPhase(BreathingPhase.INHALE, 4)
          runPhase(BreathingPhase.HOLD, 7)
          runPhase(BreathingPhase.EXHALE, 8)
          _breathingCycle.value += 1
        }
      } else {
        // Box breathing: 4s Inhale -> 4s Hold -> 4s Exhale -> 4s Hold
        while (isActive) {
          runPhase(BreathingPhase.INHALE, 4)
          runPhase(BreathingPhase.HOLD, 4)
          runPhase(BreathingPhase.EXHALE, 4)
          runPhase(BreathingPhase.HOLD_POST, 4)
          _breathingCycle.value += 1
        }
      }
    }
  }

  private suspend fun runPhase(phase: BreathingPhase, seconds: Int) {
    _breathingPhase.value = phase
    _phaseTotalSeconds.value = seconds
    for (s in seconds downTo 1) {
      _phaseSecondsLeft.value = s
      delay(1000)
    }
  }

  fun stopBreathing() {
    _isBreathingActive.value = false
    breathingJob?.cancel()
    breathingJob = null
    val durationSec = ((System.currentTimeMillis() - breathingStartTimestamp) / 1000).toInt()
    if (durationSec >= 20) {
      viewModelScope.launch {
        breathingDao.insert(
          BreathingSessionEntity(
            timestamp = System.currentTimeMillis(),
            mode = _breathingMode.value,
            durationSeconds = durationSec
          )
        )
      }
    }
  }

  override fun onCleared() {
    super.onCleared()
    breathingJob?.cancel()
  }
}
