package com.example.ui.screens.home

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.SmokeFree
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.HealthMilestonesData
import com.example.data.local.IslamicContentData
import com.example.data.localization.AppLanguage
import com.example.data.localization.StringsProvider
import com.example.ui.components.LiveQuitCounter
import com.example.ui.components.MedicalDisclaimerCard
import com.example.ui.components.StatCard
import com.example.ui.theme.AmiriFontFamily
import com.example.ui.theme.CrimsonContainer
import com.example.ui.theme.CrimsonSOS
import com.example.ui.theme.CyanCleanAir
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.EmeraldPrimaryLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.ObsidianCardBorder
import com.example.ui.theme.ObsidianSurfaceVariant
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite
import com.example.ui.viewmodel.NafasViewModel

@Composable
fun HomeScreen(
  viewModel: NafasViewModel,
  onNavigateToSos: () -> Unit,
  onNavigateToHealth: () -> Unit,
  onNavigateToNoor: () -> Unit,
  modifier: Modifier = Modifier
) {
  val userProfile by viewModel.userProfile.collectAsState()
  val currentTime by viewModel.currentTime.collectAsState()
  val cravingCount by viewModel.cravingCount.collectAsState()
  val language = viewModel.currentLanguage

  val elapsedMillis = (currentTime - userProfile.quitTimestampMillis).coerceAtLeast(0L)
  val elapsedSeconds = elapsedMillis / 1000L
  val elapsedMinutes = elapsedSeconds / 60L

  val cigsAvoided = (elapsedSeconds.toDouble() / 86400.0) * userProfile.cigarettesPerDay
  val moneySaved = cigsAvoided * userProfile.costPerCigarette
  val lifeHoursRegained = (cigsAvoided * 11.0) / 60.0

  // Active health milestone
  val activeMilestone = HealthMilestonesData.milestones.firstOrNull { !it.isCompleted(elapsedMinutes) }
    ?: HealthMilestonesData.milestones.last()
  val milestoneProgress = activeMilestone.progress(elapsedMinutes)

  // Daily Islamic reflection (changes deterministically per day)
  val dayIndex = ((currentTime / 86400000L) % IslamicContentData.items.size).toInt()
  val dailyQuote = IslamicContentData.items.getOrElse(dayIndex) { IslamicContentData.items.first() }

  // SOS button pulse animation
  val infiniteTransition = rememberInfiniteTransition(label = "sosPulse")
  val sosScale by infiniteTransition.animateFloat(
    initialValue = 1f,
    targetValue = 1.04f,
    animationSpec = infiniteRepeatable(
      animation = tween(1200),
      repeatMode = RepeatMode.Reverse
    ),
    label = "sosScale"
  )

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    item {
      Spacer(modifier = Modifier.height(4.dp))
      LiveQuitCounter(
        quitTimestampMillis = userProfile.quitTimestampMillis,
        currentTimeMillis = currentTime,
        language = language
      )
    }

    // 4 Key Stat Cards
    item {
      Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          StatCard(
            title = StringsProvider.get("money_saved", language),
            value = String.format("%.2f %s", moneySaved, userProfile.currencySymbol),
            subtitle = String.format("+%.2f %s / يوم", userProfile.dailyCost, userProfile.currencySymbol),
            icon = Icons.Default.AttachMoney,
            accentColor = GoldPrimary,
            modifier = Modifier.weight(1f)
          )

          StatCard(
            title = StringsProvider.get("cigs_avoided", language),
            value = String.format("%.1f", cigsAvoided),
            subtitle = String.format("%.1f علبة", cigsAvoided / userProfile.cigarettesPerPack),
            icon = Icons.Default.SmokeFree,
            accentColor = EmeraldPrimary,
            modifier = Modifier.weight(1f)
          )
        }

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          StatCard(
            title = StringsProvider.get("life_regained", language),
            value = String.format("%.1f س", lifeHoursRegained),
            subtitle = "أكسجين نقي وصحة",
            icon = Icons.Default.Favorite,
            accentColor = CyanCleanAir,
            modifier = Modifier.weight(1f)
          )

          StatCard(
            title = StringsProvider.get("cravings_defeated", language),
            value = cravingCount.toString(),
            subtitle = "انتصارات وثبات",
            icon = Icons.Default.Shield,
            accentColor = EmeraldPrimaryLight,
            modifier = Modifier.weight(1f)
          )
        }
      }
    }

    // Prominent Urgent SOS Emergency Craving Button
    item {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .scale(sosScale)
          .clip(RoundedCornerShape(24.dp))
          .background(
            Brush.horizontalGradient(
              listOf(CrimsonContainer, CrimsonSOS.copy(alpha = 0.25f))
            )
          )
          .border(
            1.5.dp,
            CrimsonSOS.copy(alpha = 0.8f),
            RoundedCornerShape(24.dp)
          )
          .clickable { onNavigateToSos() }
          .padding(vertical = 18.dp, horizontal = 20.dp)
          .testTag("home_sos_button"),
        contentAlignment = Alignment.Center
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.Center,
          modifier = Modifier.fillMaxWidth()
        ) {
          Box(
            modifier = Modifier
              .size(44.dp)
              .clip(CircleShape)
              .background(CrimsonSOS.copy(alpha = 0.2f))
              .border(1.dp, CrimsonSOS, CircleShape),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.Warning,
              contentDescription = "SOS Alert",
              tint = CrimsonSOS,
              modifier = Modifier.size(26.dp)
            )
          }

          Spacer(modifier = Modifier.width(14.dp))

          Column {
            Text(
              text = StringsProvider.get("sos_urgent_button", language),
              style = MaterialTheme.typography.titleMedium.copy(
                color = TextWhite,
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp
              )
            )
            Text(
              text = StringsProvider.get("sos_subtext", language),
              style = MaterialTheme.typography.bodySmall.copy(
                color = TextMuted,
                fontSize = 12.sp
              )
            )
          }
        }
      }
    }

    // Health Milestone Progress Card
    item {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(20.dp))
          .background(ObsidianSurfaceVariant)
          .border(1.dp, ObsidianCardBorder, RoundedCornerShape(20.dp))
          .clickable { onNavigateToHealth() }
          .padding(18.dp)
          .testTag("home_health_preview")
      ) {
        Column {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Health",
                tint = EmeraldPrimary,
                modifier = Modifier.size(20.dp)
              )
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = StringsProvider.get("current_health_milestone", language),
                style = MaterialTheme.typography.titleSmall.copy(
                  color = TextWhite,
                  fontWeight = FontWeight.Bold
                )
              )
            }

            Text(
              text = String.format("%.0f%%", milestoneProgress * 100),
              style = MaterialTheme.typography.titleSmall.copy(
                color = EmeraldPrimaryLight,
                fontWeight = FontWeight.Bold
              )
            )
          }

          Spacer(modifier = Modifier.height(10.dp))

          LinearProgressIndicator(
            progress = { milestoneProgress },
            modifier = Modifier
              .fillMaxWidth()
              .height(8.dp)
              .clip(RoundedCornerShape(4.dp)),
            color = EmeraldPrimary,
            trackColor = ObsidianCardBorder
          )

          Spacer(modifier = Modifier.height(10.dp))

          Text(
            text = activeMilestone.title(language),
            style = MaterialTheme.typography.bodyMedium.copy(
              color = GoldPrimary,
              fontWeight = FontWeight.SemiBold
            )
          )

          Spacer(modifier = Modifier.height(4.dp))

          Text(
            text = activeMilestone.description(language),
            style = MaterialTheme.typography.bodySmall.copy(
              color = TextMuted,
              lineHeight = 18.sp
            ),
            maxLines = 2
          )
        }
      }
    }

    // Daily Islamic Inspiration Card
    item {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(20.dp))
          .background(ObsidianSurfaceVariant)
          .border(1.dp, GoldPrimary.copy(alpha = 0.4f), RoundedCornerShape(20.dp))
          .clickable { onNavigateToNoor() }
          .padding(18.dp)
          .testTag("home_noor_card")
      ) {
        Column {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = StringsProvider.get("quote_of_day", language),
              style = MaterialTheme.typography.titleSmall.copy(
                color = GoldPrimary,
                fontWeight = FontWeight.Bold
              )
            )

            Text(
              text = dailyQuote.reference(language),
              style = MaterialTheme.typography.labelSmall.copy(
                color = TextMuted,
                fontSize = 11.sp
              )
            )
          }

          Spacer(modifier = Modifier.height(12.dp))

          Text(
            text = "«" + dailyQuote.text(language) + "»",
            fontFamily = AmiriFontFamily,
            style = MaterialTheme.typography.bodyLarge.copy(
              color = TextWhite,
              fontSize = 17.sp,
              lineHeight = 26.sp,
              fontWeight = FontWeight.Medium
            )
          )

          if (language != AppLanguage.ARABIC) {
            Spacer(modifier = Modifier.height(6.dp))
            Text(
              text = dailyQuote.translation(language),
              style = MaterialTheme.typography.bodySmall.copy(
                color = TextMuted,
                fontSize = 12.sp
              )
            )
          }
        }
      }
    }

    // Medical Disclaimer Card
    item {
      MedicalDisclaimerCard(language = language)
      Spacer(modifier = Modifier.height(16.dp))
    }
  }
}
