package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.ObsidianCardBorder
import com.example.ui.theme.ObsidianSurfaceVariant
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite

@Composable
fun StatCard(
  title: String,
  value: String,
  subtitle: String? = null,
  icon: ImageVector,
  accentColor: Color,
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
      .clip(RoundedCornerShape(20.dp))
      .background(ObsidianSurfaceVariant)
      .border(1.dp, ObsidianCardBorder, RoundedCornerShape(20.dp))
      .padding(14.dp)
      .testTag("stat_card_${title.replace(" ", "_")}")
  ) {
    Column {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
      ) {
        Box(
          modifier = Modifier
            .size(34.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(accentColor.copy(alpha = 0.15f)),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = icon,
            contentDescription = title,
            tint = accentColor,
            modifier = Modifier.size(18.dp)
          )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Text(
          text = title,
          style = MaterialTheme.typography.labelSmall.copy(
            color = TextMuted,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium
          ),
          maxLines = 1
        )
      }

      Spacer(modifier = Modifier.height(10.dp))

      Text(
        text = value,
        style = MaterialTheme.typography.titleLarge.copy(
          color = TextWhite,
          fontWeight = FontWeight.Bold,
          fontSize = 20.sp
        )
      )

      if (subtitle != null) {
        Spacer(modifier = Modifier.height(2.dp))
        Text(
          text = subtitle,
          style = MaterialTheme.typography.bodySmall.copy(
            color = accentColor,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium
          )
        )
      }
    }
  }
}
