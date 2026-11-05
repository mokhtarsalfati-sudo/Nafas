package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val NafasDarkColorScheme = darkColorScheme(
  primary = EmeraldPrimary,
  onPrimary = ObsidianBackground,
  primaryContainer = EmeraldContainer,
  onPrimaryContainer = EmeraldOnContainer,
  secondary = GoldPrimary,
  onSecondary = ObsidianBackground,
  secondaryContainer = GoldContainer,
  onSecondaryContainer = GoldOnContainer,
  tertiary = CyanCleanAir,
  onTertiary = ObsidianBackground,
  background = ObsidianBackground,
  onBackground = TextWhite,
  surface = ObsidianSurface,
  onSurface = TextWhite,
  surfaceVariant = ObsidianSurfaceVariant,
  onSurfaceVariant = TextSilver,
  error = CrimsonSOS,
  onError = TextWhite,
  errorContainer = CrimsonContainer,
  outline = ObsidianCardBorder,
  outlineVariant = ObsidianBorderSubtle
)

@Composable
fun NafasTheme(
  content: @Composable () -> Unit
) {
  MaterialTheme(
    colorScheme = NafasDarkColorScheme,
    typography = NafasTypography,
    content = content
  )
}
