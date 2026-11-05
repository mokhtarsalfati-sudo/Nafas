package com.example.data.model

data class UserProfile(
  val hasCompletedOnboarding: Boolean = false,
  val quitTimestampMillis: Long = System.currentTimeMillis(),
  val cigarettesPerDay: Int = 20,
  val pricePerPack: Double = 25.0,
  val cigarettesPerPack: Int = 20,
  val currencySymbol: String = "ر.س",
  val languageCode: String = "ar"
) {
  val costPerCigarette: Double
    get() = if (cigarettesPerPack > 0) pricePerPack / cigarettesPerPack else 0.0

  val dailyCost: Double
    get() = cigarettesPerDay * costPerCigarette
}
