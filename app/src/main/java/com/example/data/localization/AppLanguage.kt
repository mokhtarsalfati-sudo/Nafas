package com.example.data.localization

import androidx.compose.ui.unit.LayoutDirection

enum class AppLanguage(val code: String, val displayName: String, val layoutDirection: LayoutDirection) {
  ARABIC("ar", "العربية", LayoutDirection.Rtl),
  ENGLISH("en", "English", LayoutDirection.Ltr),
  FRENCH("fr", "Français", LayoutDirection.Ltr);

  companion object {
    fun fromCode(code: String): AppLanguage {
      return entries.find { it.code.equals(code, ignoreCase = true) } ?: ARABIC
    }
  }
}
