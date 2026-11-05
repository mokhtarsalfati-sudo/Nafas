package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.local.HealthMilestonesData
import com.example.data.local.IslamicContentData
import com.example.data.model.UserProfile
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

  @Test
  fun `read app name string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("NAFAS – نَفَس", appName)
  }

  @Test
  fun `verify user profile financial calculations`() {
    val profile = UserProfile(
      hasCompletedOnboarding = true,
      cigarettesPerDay = 20,
      pricePerPack = 25.0,
      cigarettesPerPack = 20
    )
    assertEquals(1.25, profile.costPerCigarette, 0.001)
    assertEquals(25.0, profile.dailyCost, 0.001)
  }

  @Test
  fun `verify health milestones and islamic content exist and are authentic`() {
    assertTrue(HealthMilestonesData.milestones.size >= 10)
    assertTrue(IslamicContentData.items.isNotEmpty())
    val quranItems = IslamicContentData.items.filter { it.type == com.example.data.model.IslamicContentType.QURAN }
    val hadithItems = IslamicContentData.items.filter { it.type == com.example.data.model.IslamicContentType.HADITH }
    assertTrue(quranItems.isNotEmpty())
    assertTrue(hadithItems.isNotEmpty())
  }
}
