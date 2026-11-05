package com.example.data.model

import com.example.data.localization.AppLanguage

enum class AchievementType {
  TIME_HOURS,
  MONEY_SAVED,
  CRAVINGS_OVERCOME,
  BREATHING_SESSIONS
}

data class Achievement(
  val id: String,
  val type: AchievementType,
  val threshold: Double,
  val titleAr: String,
  val titleEn: String,
  val titleFr: String,
  val descAr: String,
  val descEn: String,
  val descFr: String,
  val iconEmoji: String
) {
  fun title(language: AppLanguage): String = when (language) {
    AppLanguage.ARABIC -> titleAr
    AppLanguage.ENGLISH -> titleEn
    AppLanguage.FRENCH -> titleFr
  }

  fun description(language: AppLanguage): String = when (language) {
    AppLanguage.ARABIC -> descAr
    AppLanguage.ENGLISH -> descEn
    AppLanguage.FRENCH -> descFr
  }

  fun isUnlocked(currentValue: Double): Boolean = currentValue >= threshold

  fun progress(currentValue: Double): Float {
    if (threshold <= 0.0) return 1f
    return (currentValue / threshold).toFloat().coerceIn(0f, 1f)
  }
}
