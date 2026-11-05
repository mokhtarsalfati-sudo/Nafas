package com.example.ui.screens.health

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
import androidx.compose.material.icons.filled.HourglassBottom
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.HealthMilestonesData
import com.example.data.localization.AppLanguage
import com.example.data.localization.StringsProvider
import com.example.data.model.HealthMilestone
import com.example.ui.components.MedicalDisclaimerCard
import com.example.ui.theme.EmeraldContainer
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.EmeraldPrimaryLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.ObsidianCardBorder
import com.example.ui.theme.ObsidianSurfaceVariant
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite
import com.example.ui.viewmodel.NafasViewModel

@Composable
fun HealthJourneyScreen(
  viewModel: NafasViewModel,
  modifier: Modifier = Modifier
) {
  val userProfile by viewModel.userProfile.collectAsState()
  val currentTime by viewModel.currentTime.collectAsState()
  val language = viewModel.currentLanguage

  val elapsedMillis = (currentTime - userProfile.quitTimestampMillis).coerceAtLeast(0L)
  val elapsedMinutes = elapsedMillis / (1000L * 60L)

  val completedCount = HealthMilestonesData.milestones.count { it.isCompleted(elapsedMinutes) }
  val totalCount = HealthMilestonesData.milestones.size

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
          .background(ObsidianSurfaceVariant)
          .border(1.dp, EmeraldPrimary.copy(alpha = 0.4f), RoundedCornerShape(24.dp))
          .padding(20.dp)
      ) {
        Column {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = StringsProvider.get("health_journey_title", language),
              style = MaterialTheme.typography.titleLarge.copy(
                color = GoldPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
              )
            )

            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(EmeraldPrimary.copy(alpha = 0.15f))
                .border(1.dp, EmeraldPrimary.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
                .padding(horizontal = 10.dp, vertical = 4.dp)
            ) {
              Text(
                text = "$completedCount / $totalCount",
                style = MaterialTheme.typography.labelMedium.copy(
                  color = EmeraldPrimaryLight,
                  fontWeight = FontWeight.Bold
                )
              )
            }
          }

          Spacer(modifier = Modifier.height(8.dp))

          Text(
            text = StringsProvider.get("health_journey_subtitle", language),
            style = MaterialTheme.typography.bodySmall.copy(
              color = TextMuted,
              lineHeight = 18.sp
            )
          )
        }
      }
    }

    // Medical Disclaimer Card (Crucial for Health Journey)
    item {
      MedicalDisclaimerCard(language = language)
    }

    // Milestones List
    items(HealthMilestonesData.milestones) { milestone ->
      MilestoneCard(
        milestone = milestone,
        elapsedMinutes = elapsedMinutes,
        language = language
      )
    }

    item {
      Spacer(modifier = Modifier.height(16.dp))
    }
  }
}

@Composable
private fun MilestoneCard(
  milestone: HealthMilestone,
  elapsedMinutes: Long,
  language: AppLanguage
) {
  val isCompleted = milestone.isCompleted(elapsedMinutes)
  val progress = milestone.progress(elapsedMinutes)
  val remainingMinutes = milestone.remainingMinutes(elapsedMinutes)

  Box(
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(20.dp))
      .background(
        if (isCompleted) EmeraldContainer.copy(alpha = 0.4f)
        else ObsidianSurfaceVariant
      )
      .border(
        1.dp,
        if (isCompleted) EmeraldPrimary.copy(alpha = 0.6f)
        else ObsidianCardBorder,
        RoundedCornerShape(20.dp)
      )
      .padding(18.dp)
      .testTag("milestone_${milestone.id}")
  ) {
    Column {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.weight(1f)
        ) {
          Box(
            modifier = Modifier
              .size(34.dp)
              .clip(CircleShape)
              .background(
                if (isCompleted) EmeraldPrimary.copy(alpha = 0.2f)
                else ObsidianCardBorder
              ),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = when {
                isCompleted -> Icons.Default.CheckCircle
                progress > 0f -> Icons.Default.HourglassBottom
                else -> Icons.Default.Lock
              },
              contentDescription = "Status",
              tint = when {
                isCompleted -> EmeraldPrimaryLight
                progress > 0f -> GoldPrimary
                else -> TextMuted
              },
              modifier = Modifier.size(20.dp)
            )
          }

          Spacer(modifier = Modifier.width(12.dp))

          Text(
            text = milestone.title(language),
            style = MaterialTheme.typography.titleMedium.copy(
              color = if (isCompleted) EmeraldPrimaryLight else TextWhite,
              fontWeight = FontWeight.Bold,
              fontSize = 15.sp
            )
          )
        }

        // Status badge
        Text(
          text = when {
            isCompleted -> StringsProvider.get("milestone_completed", language)
            progress > 0f -> String.format("%.0f%%", progress * 100)
            else -> StringsProvider.get("milestone_locked", language)
          },
          style = MaterialTheme.typography.labelSmall.copy(
            color = if (isCompleted) EmeraldPrimaryLight else if (progress > 0f) GoldPrimary else TextMuted,
            fontWeight = FontWeight.Bold
          )
        )
      }

      Spacer(modifier = Modifier.height(10.dp))

      Text(
        text = milestone.description(language),
        style = MaterialTheme.typography.bodySmall.copy(
          color = TextMuted,
          lineHeight = 18.sp,
          fontSize = 13.sp
        )
      )

      if (!isCompleted) {
        Spacer(modifier = Modifier.height(12.dp))

        LinearProgressIndicator(
          progress = { progress },
          modifier = Modifier
            .fillMaxWidth()
            .height(6.dp)
            .clip(RoundedCornerShape(3.dp)),
          color = if (progress > 0f) GoldPrimary else EmeraldPrimary,
          trackColor = ObsidianCardBorder
        )

        Spacer(modifier = Modifier.height(6.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Text(
            text = StringsProvider.get("time_left", language),
            style = MaterialTheme.typography.labelSmall.copy(color = TextMuted, fontSize = 11.sp)
          )
          Text(
            text = formatRemainingTime(remainingMinutes, language),
            style = MaterialTheme.typography.labelSmall.copy(
              color = GoldPrimary,
              fontWeight = FontWeight.SemiBold,
              fontSize = 11.sp
            )
          )
        }
      }
    }
  }
}

private fun formatRemainingTime(minutes: Long, language: AppLanguage): String {
  val days = minutes / (24 * 60)
  val hours = (minutes % (24 * 60)) / 60
  val mins = minutes % 60

  return when (language) {
    AppLanguage.ARABIC -> when {
      days > 0 -> "$days يوم و $hours ساعة"
      hours > 0 -> "$hours ساعة و $mins دقيقة"
      else -> "$mins دقيقة"
    }
    AppLanguage.ENGLISH -> when {
      days > 0 -> "$days days, $hours hrs"
      hours > 0 -> "$hours hrs, $mins mins"
      else -> "$mins mins"
    }
    AppLanguage.FRENCH -> when {
      days > 0 -> "$days j, $hours h"
      hours > 0 -> "$hours h, $mins min"
      else -> "$mins min"
    }
  }
}
