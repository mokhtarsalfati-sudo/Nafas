package com.example.data.model

import com.example.data.localization.AppLanguage

enum class IslamicContentType {
  QURAN,
  HADITH,
  DUA
}

data class IslamicInspiration(
  val id: String,
  val type: IslamicContentType,
  val arabicText: String,
  val englishTranslation: String,
  val frenchTranslation: String,
  val referenceAr: String,
  val referenceEn: String,
  val benefitNoteAr: String,
  val benefitNoteEn: String,
  val benefitNoteFr: String
) {
  fun text(language: AppLanguage): String = arabicText

  fun translation(language: AppLanguage): String = when (language) {
    AppLanguage.ARABIC -> ""
    AppLanguage.ENGLISH -> englishTranslation
    AppLanguage.FRENCH -> frenchTranslation
  }

  fun reference(language: AppLanguage): String = when (language) {
    AppLanguage.ARABIC -> referenceAr
    AppLanguage.ENGLISH -> referenceEn
    AppLanguage.FRENCH -> referenceEn
  }

  fun benefit(language: AppLanguage): String = when (language) {
    AppLanguage.ARABIC -> benefitNoteAr
    AppLanguage.ENGLISH -> benefitNoteEn
    AppLanguage.FRENCH -> benefitNoteFr
  }
}
