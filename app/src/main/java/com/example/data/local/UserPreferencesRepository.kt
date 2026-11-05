package com.example.data.local

import android.content.Context
import android.content.SharedPreferences
import com.example.data.model.UserProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class UserPreferencesRepository(context: Context) {
  private val prefs: SharedPreferences =
    context.getSharedPreferences("nafas_user_prefs", Context.MODE_PRIVATE)

  private val _userProfile = MutableStateFlow(loadProfile())
  val userProfile: StateFlow<UserProfile> = _userProfile.asStateFlow()

  private fun loadProfile(): UserProfile {
    val completed = prefs.getBoolean("has_completed_onboarding", false)
    val quitTime = prefs.getLong("quit_timestamp_millis", System.currentTimeMillis())
    val cigsPerDay = prefs.getInt("cigs_per_day", 20)
    val pricePack = prefs.getFloat("price_per_pack", 25.0f).toDouble()
    val cigsInPack = prefs.getInt("cigs_in_pack", 20)
    val currency = prefs.getString("currency_symbol", "ر.س") ?: "ر.س"
    val language = prefs.getString("language_code", "ar") ?: "ar"

    return UserProfile(
      hasCompletedOnboarding = completed,
      quitTimestampMillis = quitTime,
      cigarettesPerDay = cigsPerDay,
      pricePerPack = pricePack,
      cigarettesPerPack = cigsInPack,
      currencySymbol = currency,
      languageCode = language
    )
  }

  fun saveProfile(profile: UserProfile) {
    prefs.edit().apply {
      putBoolean("has_completed_onboarding", profile.hasCompletedOnboarding)
      putLong("quit_timestamp_millis", profile.quitTimestampMillis)
      putInt("cigs_per_day", profile.cigarettesPerDay)
      putFloat("price_per_pack", profile.pricePerPack.toFloat())
      putInt("cigs_in_pack", profile.cigarettesPerPack)
      putString("currency_symbol", profile.currencySymbol)
      putString("language_code", profile.languageCode)
      apply()
    }
    _userProfile.value = profile
  }

  fun updateLanguage(languageCode: String) {
    val current = _userProfile.value
    val updated = current.copy(languageCode = languageCode)
    saveProfile(updated)
  }

  fun resetCounter() {
    val current = _userProfile.value
    val updated = current.copy(quitTimestampMillis = System.currentTimeMillis())
    saveProfile(updated)
  }

  fun resetAll() {
    prefs.edit().clear().apply()
    _userProfile.value = UserProfile()
  }
}
