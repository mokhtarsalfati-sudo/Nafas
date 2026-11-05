package com.example.ui.screens.achievements

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Lock
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.AchievementsData
import com.example.data.localization.AppLanguage
import com.example.data.localization.StringsProvider
import com.example.data.model.Achievement
import com.example.data.model.AchievementType
import com.example.ui.theme.EmeraldContainer
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.EmeraldPrimaryLight
import com.example.ui.theme.GoldContainer
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.ObsidianCardBorder
import com.example.ui.theme.ObsidianSurfaceVariant
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite
import com.example.ui.viewmodel.NafasViewModel

@Composable
fun AchievementsScreen(
  viewModel: NafasViewModel,
  modifier: Modifier = Modifier
) {
  val userProfile by viewModel.userProfile.collectAsState()
  val currentTime by viewModel.currentTime.collectAsState()
  val cravingCount by viewModel.cravingCount.collectAsState()
  val breathingCount by viewModel.breathingCount.collectAsState()
  val language = viewModel.currentLanguage

  val elapsedMillis = (currentTime - userProfile.quitTimestampMillis).coerceAtLeast(0L)
  val elapsedHours = elapsedMillis.toDouble() / (1000.0 * 3600.0)
  val cigsAvoided = (elapsedMillis.toDouble() / (1000.0 * 86400.0)) * userProfile.cigarettesPerDay
  val totalSaved = cigsAvoided * userProfile.costPerCigarette

  fun getCurrentValue(type: AchievementType): Double {
    return when (type) {
      AchievementType.TIME_HOURS -> elapsedHours
      AchievementType.MONEY_SAVED -> totalSaved
      AchievementType.CRAVINGS_OVERCOME -> cravingCount.toDouble()
      AchievementType.BREATHING_SESSIONS -> breathingCount.toDouble()
    }
  }

  val unlockedCount = AchievementsData.badges.count { badge ->
    badge.isUnlocked(getCurrentValue(badge.type))
  }
  val totalCount = AchievementsData.badges.size

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    // Header
    item {
      Spacer(modifier = Modifier.height(4.dp))
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(24.dp))
          .background(
            Brush.verticalGradient(
              listOf(GoldContainer.copy(alpha = 0.5f), ObsidianSurfaceVariant)
            )
          )
          .border(1.5.dp, GoldPrimary, RoundedCornerShape(24.dp))
          .padding(20.dp)
          .testTag("achievements_header")
      ) {
        Column {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Box(
                modifier = Modifier
                  .size(38.dp)
                  .clip(CircleShape)
                .background(GoldPrimary.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
              ) {
                Icon(
                  imageVector = Icons.Default.EmojiEvents,
                  contentDescription = "Achievements",
                  tint = GoldLight,
                  modifier = Modifier.size(22.dp)
                )
              }
              Spacer(modifier = Modifier.width(12.dp))
              Text(
                text = StringsProvider.get("achievements_title", language),
                style = MaterialTheme.typography.titleLarge.copy(
                  color = GoldLight,
                  fontWeight = FontWeight.Bold,
                  fontSize = 19.sp
                )
              )
            }

            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(EmeraldPrimary.copy(alpha = 0.2f))
                .border(1.dp, EmeraldPrimary, RoundedCornerShape(12.dp))
                .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
              Text(
                text = "$unlockedCount / $totalCount",
                style = MaterialTheme.typography.labelMedium.copy(
                  color = EmeraldPrimaryLight,
                  fontWeight = FontWeight.Bold
                )
              )
            }
          }

          Spacer(modifier = Modifier.height(10.dp))

          Text(
            text = StringsProvider.get("achievements_subtitle", language),
            style = MaterialTheme.typography.bodySmall.copy(
              color = TextMuted,
              lineHeight = 18.sp
            )
          )
        }
      }
    }

    // Badges list
    items(AchievementsData.badges) { badge ->
      val currentValue = getCurrentValue(badge.type)
      BadgeItemCard(
        badge = badge,
        currentValue = currentValue,
        language = language
      )
    }

    item {
      Spacer(modifier = Modifier.height(16.dp))
    }
  }
}

@Composable
private fun BadgeItemCard(
  badge: Achievement,
  currentValue: Double,
  language: AppLanguage
) {
  val isUnlocked = badge.isUnlocked(currentValue)
  val progress = badge.progress(currentValue)

  Box(
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(20.dp))
      .background(
        if (isUnlocked) EmeraldContainer.copy(alpha = 0.35f)
        else ObsidianSurfaceVariant
      )
      .border(
        1.dp,
        if (isUnlocked) GoldPrimary.copy(alpha = 0.8f)
        else ObsidianCardBorder,
        RoundedCornerShape(20.dp)
      )
      .padding(16.dp)
      .testTag("badge_${badge.id}")
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically
    ) {
      // Emoji Icon Box
      Box(
        modifier = Modifier
          .size(52.dp)
          .clip(RoundedCornerShape(16.dp))
          .background(
            if (isUnlocked) GoldContainer
            else ObsidianCardBorder.copy(alpha = 0.5f)
          )
          .border(
            1.dp,
            if (isUnlocked) GoldPrimary else ObsidianCardBorder,
            RoundedCornerShape(16.dp)
          ),
        contentAlignment = Alignment.Center
      ) {
        Text(
          text = badge.iconEmoji,
          fontSize = 26.sp
        )
      }

      Spacer(modifier = Modifier.width(14.dp))

      Column(modifier = Modifier.weight(1f)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = badge.title(language),
            style = MaterialTheme.typography.titleMedium.copy(
              color = if (isUnlocked) GoldLight else TextWhite,
              fontWeight = FontWeight.Bold,
              fontSize = 15.sp
            )
          )

          if (isUnlocked) {
            Icon(
              imageVector = Icons.Default.CheckCircle,
              contentDescription = "Unlocked",
              tint = EmeraldPrimaryLight,
              modifier = Modifier.size(18.dp)
            )
          } else {
            Icon(
              imageVector = Icons.Default.Lock,
              contentDescription = "Locked",
              tint = TextMuted,
              modifier = Modifier.size(16.dp)
            )
          }
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
          text = badge.description(language),
          style = MaterialTheme.typography.bodySmall.copy(
            color = TextMuted,
            fontSize = 12.sp,
            lineHeight = 16.sp
          )
        )

        if (!isUnlocked) {
          Spacer(modifier = Modifier.height(8.dp))
          LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
              .fillMaxWidth()
              .height(5.dp)
              .clip(RoundedCornerShape(3.dp)),
            color = GoldPrimary,
            trackColor = ObsidianCardBorder
          )
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = String.format("%.0f%%", progress * 100),
            style = MaterialTheme.typography.labelSmall.copy(
              color = GoldPrimary,
              fontSize = 10.sp,
              fontWeight = FontWeight.SemiBold
            )
          )
        }
      }
    }
  }
}
