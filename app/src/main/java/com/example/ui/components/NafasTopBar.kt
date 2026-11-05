package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.localization.AppLanguage
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.EmeraldPrimaryLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.ObsidianBackground
import com.example.ui.theme.ObsidianBorderSubtle
import com.example.ui.theme.ObsidianSurfaceVariant
import com.example.ui.theme.TextWhite

@Composable
fun NafasTopBar(
  currentLanguage: AppLanguage,
  onOpenLanguage: () -> Unit,
  onOpenSettings: () -> Unit,
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
      .fillMaxWidth()
      .background(ObsidianBackground)
      .statusBarsPadding()
      .padding(horizontal = 20.dp, vertical = 12.dp)
      .testTag("nafas_top_bar")
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      // Brand Logo & Title
      Row(
        verticalAlignment = Alignment.CenterVertically
      ) {
        Box(
          modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(
              Brush.radialGradient(
                listOf(EmeraldPrimary.copy(alpha = 0.4f), EmeraldPrimary.copy(alpha = 0.05f))
              )
            )
            .border(1.5.dp, GoldPrimary.copy(alpha = 0.6f), CircleShape),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.Spa,
            contentDescription = "NAFAS Logo",
            tint = EmeraldPrimaryLight,
            modifier = Modifier.size(22.dp)
          )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
          Text(
            text = "نَفَس",
            style = MaterialTheme.typography.titleLarge.copy(
              color = GoldPrimary,
              fontWeight = FontWeight.Bold,
              fontSize = 22.sp
            )
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = "NAFAS",
            style = MaterialTheme.typography.titleSmall.copy(
              color = EmeraldPrimary,
              fontWeight = FontWeight.SemiBold,
              letterSpacing = 1.sp,
              fontSize = 13.sp
            )
          )
        }
      }

      // Actions: Language badge & Settings
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        // Language Pill
        Row(
          modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(ObsidianSurfaceVariant)
            .border(1.dp, ObsidianBorderSubtle, RoundedCornerShape(20.dp))
            .clickable { onOpenLanguage() }
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .testTag("language_button"),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Icon(
            imageVector = Icons.Default.Language,
            contentDescription = "Change Language",
            tint = GoldPrimary,
            modifier = Modifier.size(16.dp)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = currentLanguage.displayName,
            style = MaterialTheme.typography.labelMedium.copy(
              color = TextWhite,
              fontWeight = FontWeight.Medium
            )
          )
        }

        // Settings Button
        IconButton(
          onClick = onOpenSettings,
          modifier = Modifier
            .size(38.dp)
            .clip(CircleShape)
            .background(ObsidianSurfaceVariant)
            .border(1.dp, ObsidianBorderSubtle, CircleShape)
            .testTag("settings_button")
        ) {
          Icon(
            imageVector = Icons.Default.Settings,
            contentDescription = "Settings",
            tint = TextWhite,
            modifier = Modifier.size(18.dp)
          )
        }
      }
    }
  }
}
