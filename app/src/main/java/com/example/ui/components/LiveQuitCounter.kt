package com.example.ui.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.localization.AppLanguage
import com.example.data.localization.StringsProvider
import com.example.ui.theme.AmiriFontFamily
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.EmeraldPrimaryLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.ObsidianCardBorder
import com.example.ui.theme.ObsidianSurface
import com.example.ui.theme.ObsidianSurfaceVariant
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite

@Composable
fun LiveQuitCounter(
  quitTimestampMillis: Long,
  currentTimeMillis: Long,
  language: AppLanguage,
  modifier: Modifier = Modifier
) {
  val diffMillis = (currentTimeMillis - quitTimestampMillis).coerceAtLeast(0L)
  val totalSeconds = diffMillis / 1000L
  val days = totalSeconds / 86400L
  val hours = (totalSeconds % 86400L) / 3600L
  val minutes = (totalSeconds % 3600L) / 60L
  val seconds = totalSeconds % 60L

  val infiniteTransition = rememberInfiniteTransition(label = "pulse")
  val pulseAlpha by infiniteTransition.animateFloat(
    initialValue = 0.4f,
    targetValue = 0.9f,
    animationSpec = infiniteRepeatable(
      animation = tween(1800),
      repeatMode = RepeatMode.Reverse
    ),
    label = "alpha"
  )

  Box(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(28.dp))
      .background(
        Brush.verticalGradient(
          listOf(
            ObsidianSurfaceVariant,
            ObsidianSurface
          )
        )
      )
      .border(
        1.5.dp,
        Brush.verticalGradient(
          listOf(
            EmeraldPrimary.copy(alpha = 0.6f),
            GoldPrimary.copy(alpha = 0.3f)
          )
        ),
        RoundedCornerShape(28.dp)
      )
      .padding(20.dp)
      .testTag("live_quit_counter")
  ) {
    Column(
      modifier = Modifier.fillMaxWidth(),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      // Top Live Status Badge
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
          .clip(RoundedCornerShape(20.dp))
          .background(EmeraldPrimary.copy(alpha = 0.12f))
          .border(1.dp, EmeraldPrimary.copy(alpha = 0.3f), RoundedCornerShape(20.dp))
          .padding(horizontal = 14.dp, vertical = 6.dp)
      ) {
        Box(
          modifier = Modifier
            .size(8.dp)
            .clip(CircleShape)
            .background(EmeraldPrimaryLight.copy(alpha = pulseAlpha))
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(
          text = StringsProvider.get("smoke_free_timer", language),
          style = MaterialTheme.typography.labelMedium.copy(
            color = EmeraldPrimaryLight,
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp
          )
        )
      }

      Spacer(modifier = Modifier.height(20.dp))

      // 4 Main Time Segment Cards: Days, Hours, Minutes, Seconds
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
      ) {
        TimeUnitBox(
          value = days.toString(),
          label = StringsProvider.get("days", language),
          isHighlight = true
        )

        TimeDivider()

        TimeUnitBox(
          value = String.format("%02d", hours),
          label = StringsProvider.get("hours", language),
          isHighlight = false
        )

        TimeDivider()

        TimeUnitBox(
          value = String.format("%02d", minutes),
          label = StringsProvider.get("minutes", language),
          isHighlight = false
        )

        TimeDivider()

        TimeUnitBox(
          value = String.format("%02d", seconds),
          label = StringsProvider.get("seconds", language),
          isHighlight = false
        )
      }

      Spacer(modifier = Modifier.height(16.dp))

      // Subtitle with Islamic blessing
      Text(
        text = when (language) {
          AppLanguage.ARABIC -> "«مَا شَاءَ اللَّهُ • ثَبَّتَكَ اللَّهُ وَزَادَكَ نَقَاءً»"
          AppLanguage.ENGLISH -> "Masha'Allah • May Allah preserve your purity & health"
          AppLanguage.FRENCH -> "Macha'Allah • Qu'Allah préserve votre pureté et santé"
        },
        fontFamily = AmiriFontFamily,
        style = MaterialTheme.typography.bodyMedium.copy(
          color = GoldPrimary,
          fontSize = 15.sp,
          fontWeight = FontWeight.Medium
        )
      )
    }
  }
}

@Composable
private fun TimeUnitBox(
  value: String,
  label: String,
  isHighlight: Boolean
) {
  Column(
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Box(
      modifier = Modifier
        .size(width = 66.dp, height = 72.dp)
        .clip(RoundedCornerShape(16.dp))
        .background(
          if (isHighlight) EmeraldPrimary.copy(alpha = 0.16f)
          else ObsidianSurfaceVariant
        )
        .border(
          1.dp,
          if (isHighlight) EmeraldPrimary.copy(alpha = 0.5f)
          else ObsidianCardBorder,
          RoundedCornerShape(16.dp)
        ),
      contentAlignment = Alignment.Center
    ) {
      Text(
        text = value,
        style = MaterialTheme.typography.displaySmall.copy(
          color = if (isHighlight) EmeraldPrimaryLight else TextWhite,
          fontWeight = FontWeight.Bold,
          fontSize = 28.sp
        )
      )
    }
    Spacer(modifier = Modifier.height(6.dp))
    Text(
      text = label,
      style = MaterialTheme.typography.labelSmall.copy(
        color = TextMuted,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp
      )
    )
  }
}

@Composable
private fun TimeDivider() {
  Text(
    text = ":",
    style = MaterialTheme.typography.headlineMedium.copy(
      color = GoldPrimary.copy(alpha = 0.6f),
      fontWeight = FontWeight.Bold
    ),
    modifier = Modifier.padding(bottom = 18.dp)
  )
}
