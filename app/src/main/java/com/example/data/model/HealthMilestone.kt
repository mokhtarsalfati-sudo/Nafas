package com.example.data.model

import com.example.data.localization.AppLanguage

data class HealthMilestone(
  val id: String,
  val targetMinutes: Long,
  val titleAr: String,
  val titleEn: String,
  val titleFr: String,
  val descAr: String,
  val descEn: String,
  val descFr: String,
  val category: String = "lungs"
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

  fun progress(elapsedMinutes: Long): Float {
    if (targetMinutes <= 0) return 1f
    return (elapsedMinutes.toFloat() / targetMinutes.toFloat()).coerceIn(0f, 1f)
  }

  fun isCompleted(elapsedMinutes: Long): Boolean = elapsedMinutes >= targetMinutes

  fun remainingMinutes(elapsedMinutes: Long): Long =
    (targetMinutes - elapsedMinutes).coerceAtLeast(0L)
}
