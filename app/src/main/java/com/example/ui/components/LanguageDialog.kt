package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.localization.AppLanguage
import com.example.data.localization.StringsProvider
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.ObsidianCardBorder
import com.example.ui.theme.ObsidianSurface
import com.example.ui.theme.ObsidianSurfaceVariant
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageDialog(
  currentLanguage: AppLanguage,
  onLanguageSelected: (AppLanguage) -> Unit,
  onDismiss: () -> Unit
) {
  BasicAlertDialog(
    onDismissRequest = onDismiss,
    modifier = Modifier.testTag("language_dialog")
  ) {
    Box(
      modifier = Modifier
        .clip(RoundedCornerShape(24.dp))
        .background(ObsidianSurface)
        .border(1.dp, ObsidianCardBorder, RoundedCornerShape(24.dp))
        .padding(24.dp)
    ) {
      Column(
        modifier = Modifier.fillMaxWidth()
      ) {
        Text(
          text = StringsProvider.get("select_language", currentLanguage),
          style = MaterialTheme.typography.titleLarge.copy(
            color = GoldPrimary,
            fontWeight = FontWeight.Bold
          )
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppLanguage.entries.forEach { lang ->
          val isSelected = lang == currentLanguage
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(12.dp))
              .background(if (isSelected) EmeraldPrimary.copy(alpha = 0.15f) else ObsidianSurfaceVariant)
              .border(
                1.dp,
                if (isSelected) EmeraldPrimary else ObsidianCardBorder,
                RoundedCornerShape(12.dp)
              )
              .clickable {
                onLanguageSelected(lang)
                onDismiss()
              }
              .padding(16.dp)
              .testTag("lang_option_${lang.code}"),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Column {
              Text(
                text = lang.displayName,
                style = MaterialTheme.typography.bodyLarge.copy(
                  color = if (isSelected) EmeraldPrimary else TextWhite,
                  fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                )
              )
              Text(
                text = when (lang) {
                  AppLanguage.ARABIC -> "اللغة الافتراضية (واجهة عربية من اليمين لليسار)"
                  AppLanguage.ENGLISH -> "English interface (LTR)"
                  AppLanguage.FRENCH -> "Interface en français (LTR)"
                },
                style = MaterialTheme.typography.bodySmall.copy(
                  color = TextMuted,
                  fontSize = 11.sp
                )
              )
            }

            if (isSelected) {
              Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Selected",
                tint = EmeraldPrimary
              )
            }
          }
          Spacer(modifier = Modifier.height(8.dp))
        }

        Spacer(modifier = Modifier.height(8.dp))

        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable { onDismiss() }
            .padding(12.dp),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = StringsProvider.get("close", currentLanguage),
            style = MaterialTheme.typography.labelLarge.copy(color = TextMuted)
          )
        }
      }
    }
  }
}
