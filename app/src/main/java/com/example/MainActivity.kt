package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Savings
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.EmojiEvents
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Savings
import androidx.compose.material.icons.outlined.SelfImprovement
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.localization.StringsProvider
import com.example.ui.components.LanguageDialog
import com.example.ui.components.NafasTopBar
import com.example.ui.screens.achievements.AchievementsScreen
import com.example.ui.screens.health.HealthJourneyScreen
import com.example.ui.screens.home.HomeScreen
import com.example.ui.screens.noor.NoorGuidanceScreen
import com.example.ui.screens.onboarding.OnboardingScreen
import com.example.ui.screens.savings.SavingsScreen
import com.example.ui.screens.settings.SettingsDialog
import com.example.ui.screens.sos.SosScreen
import com.example.ui.theme.EmeraldContainer
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.NafasTheme
import com.example.ui.theme.ObsidianBackground
import com.example.ui.theme.ObsidianCardBorder
import com.example.ui.theme.ObsidianSurface
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite
import com.example.ui.viewmodel.NafasViewModel

enum class NafasTab(
  val labelKey: String,
  val selectedIcon: ImageVector,
  val unselectedIcon: ImageVector
) {
  HOME("nav_home", Icons.Filled.Home, Icons.Outlined.Home),
  HEALTH("nav_health", Icons.Filled.Favorite, Icons.Outlined.FavoriteBorder),
  SOS("nav_sos", Icons.Filled.SelfImprovement, Icons.Outlined.SelfImprovement),
  SAVINGS("nav_savings", Icons.Filled.Savings, Icons.Outlined.Savings),
  NOOR("nav_noor", Icons.Filled.AutoAwesome, Icons.Outlined.AutoAwesome),
  ACHIEVEMENTS("nav_achievements", Icons.Filled.EmojiEvents, Icons.Outlined.EmojiEvents)
}

class MainActivity : ComponentActivity() {

  private val viewModel: NafasViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()

    setContent {
      NafasTheme {
        val userProfile by viewModel.userProfile.collectAsState()
        val language = viewModel.currentLanguage

        CompositionLocalProvider(LocalLayoutDirection provides language.layoutDirection) {
          if (!userProfile.hasCompletedOnboarding) {
            OnboardingScreen(
              viewModel = viewModel,
              onComplete = { /* StateFlow in ViewModel auto transitions to Main */ }
            )
          } else {
            MainAppContent(viewModel = viewModel)
          }
        }
      }
    }
  }
}

@Composable
fun MainAppContent(viewModel: NafasViewModel) {
  var currentTab by remember { mutableStateOf(NafasTab.HOME) }
  var showLanguageDialog by remember { mutableStateOf(false) }
  var showSettingsDialog by remember { mutableStateOf(false) }

  val language = viewModel.currentLanguage

  // BackHandler to return to HOME when in a sub-tab
  BackHandler(enabled = currentTab != NafasTab.HOME) {
    currentTab = NafasTab.HOME
  }

  Scaffold(
    topBar = {
      NafasTopBar(
        currentLanguage = language,
        onOpenLanguage = { showLanguageDialog = true },
        onOpenSettings = { showSettingsDialog = true }
      )
    },
    bottomBar = {
      NavigationBar(
        containerColor = ObsidianSurface,
        tonalElevation = 8.dp,
        modifier = Modifier.testTag("nafas_bottom_bar")
      ) {
        NafasTab.entries.forEach { tab ->
          val isSelected = currentTab == tab
          NavigationBarItem(
            selected = isSelected,
            onClick = { currentTab = tab },
            icon = {
              Icon(
                imageVector = if (isSelected) tab.selectedIcon else tab.unselectedIcon,
                contentDescription = StringsProvider.get(tab.labelKey, language),
                tint = if (isSelected) GoldPrimary else TextMuted
              )
            },
            label = {
              Text(
                text = StringsProvider.get(tab.labelKey, language),
                style = MaterialTheme.typography.labelSmall.copy(
                  fontSize = 10.sp,
                  fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                  color = if (isSelected) TextWhite else TextMuted
                ),
                maxLines = 1
              )
            },
            colors = NavigationBarItemDefaults.colors(
              indicatorColor = EmeraldContainer,
              selectedIconColor = GoldPrimary,
              unselectedIconColor = TextMuted,
              selectedTextColor = TextWhite,
              unselectedTextColor = TextMuted
            ),
            modifier = Modifier.testTag("nav_tab_${tab.name.lowercase()}")
          )
        }
      }
    },
    containerColor = ObsidianBackground
  ) { innerPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .background(ObsidianBackground)
        .padding(innerPadding)
    ) {
      when (currentTab) {
        NafasTab.HOME -> HomeScreen(
          viewModel = viewModel,
          onNavigateToSos = { currentTab = NafasTab.SOS },
          onNavigateToHealth = { currentTab = NafasTab.HEALTH },
          onNavigateToNoor = { currentTab = NafasTab.NOOR }
        )
        NafasTab.HEALTH -> HealthJourneyScreen(viewModel = viewModel)
        NafasTab.SOS -> SosScreen(viewModel = viewModel)
        NafasTab.SAVINGS -> SavingsScreen(viewModel = viewModel)
        NafasTab.NOOR -> NoorGuidanceScreen(viewModel = viewModel)
        NafasTab.ACHIEVEMENTS -> AchievementsScreen(viewModel = viewModel)
      }
    }
  }

  if (showLanguageDialog) {
    LanguageDialog(
      currentLanguage = language,
      onLanguageSelected = { newLang ->
        viewModel.setLanguage(newLang)
        showLanguageDialog = false
      },
      onDismiss = { showLanguageDialog = false }
    )
  }

  if (showSettingsDialog) {
    SettingsDialog(
      viewModel = viewModel,
      onDismiss = { showSettingsDialog = false }
    )
  }
}
