package com.example.ui.screens.noor

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.IslamicContentData
import com.example.data.localization.AppLanguage
import com.example.data.localization.StringsProvider
import com.example.data.model.IslamicContentType
import com.example.data.model.IslamicInspiration
import com.example.ui.theme.AmiriFontFamily
import com.example.ui.theme.EmeraldContainer
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.EmeraldPrimaryLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.ObsidianBorderSubtle
import com.example.ui.theme.ObsidianCardBorder
import com.example.ui.theme.ObsidianSurfaceVariant
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite
import com.example.ui.viewmodel.NafasViewModel

@Composable
fun NoorGuidanceScreen(
  viewModel: NafasViewModel,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  val language = viewModel.currentLanguage
  var selectedFilter by remember { mutableStateOf<IslamicContentType?>(null) }

  val filteredItems = remember(selectedFilter) {
    if (selectedFilter == null) IslamicContentData.items
    else IslamicContentData.items.filter { it.type == selectedFilter }
  }

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
          .border(1.dp, GoldPrimary.copy(alpha = 0.5f), RoundedCornerShape(24.dp))
          .padding(20.dp)
      ) {
        Column {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(38.dp)
                .clip(CircleShape)
                .background(GoldPrimary.copy(alpha = 0.2f)),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.AutoAwesome,
                contentDescription = "Noor",
                tint = GoldPrimary,
                modifier = Modifier.size(22.dp)
              )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Text(
              text = StringsProvider.get("noor_title", language),
              style = MaterialTheme.typography.titleLarge.copy(
                color = GoldPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 19.sp
              )
            )
          }

          Spacer(modifier = Modifier.height(8.dp))

          Text(
            text = StringsProvider.get("noor_subtitle", language),
            style = MaterialTheme.typography.bodySmall.copy(
              color = TextMuted,
              lineHeight = 18.sp
            )
          )
        }
      }
    }

    // Filter Chips
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        FilterChip(
          selected = selectedFilter == null,
          onClick = { selectedFilter = null },
          label = { Text(StringsProvider.get("filter_all", language), fontSize = 12.sp) },
          colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = EmeraldPrimary,
            selectedLabelColor = MaterialTheme.colorScheme.surface,
            containerColor = ObsidianSurfaceVariant,
            labelColor = TextWhite
          ),
          border = FilterChipDefaults.filterChipBorder(
            enabled = true,
            selected = selectedFilter == null,
            borderColor = ObsidianBorderSubtle,
            selectedBorderColor = EmeraldPrimary
          )
        )

        FilterChip(
          selected = selectedFilter == IslamicContentType.QURAN,
          onClick = { selectedFilter = IslamicContentType.QURAN },
          label = { Text(StringsProvider.get("filter_quran", language), fontSize = 12.sp) },
          colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = EmeraldPrimary,
            selectedLabelColor = MaterialTheme.colorScheme.surface,
            containerColor = ObsidianSurfaceVariant,
            labelColor = TextWhite
          ),
          border = FilterChipDefaults.filterChipBorder(
            enabled = true,
            selected = selectedFilter == IslamicContentType.QURAN,
            borderColor = ObsidianBorderSubtle,
            selectedBorderColor = EmeraldPrimary
          )
        )

        FilterChip(
          selected = selectedFilter == IslamicContentType.HADITH,
          onClick = { selectedFilter = IslamicContentType.HADITH },
          label = { Text(StringsProvider.get("filter_hadith", language), fontSize = 12.sp) },
          colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = EmeraldPrimary,
            selectedLabelColor = MaterialTheme.colorScheme.surface,
            containerColor = ObsidianSurfaceVariant,
            labelColor = TextWhite
          ),
          border = FilterChipDefaults.filterChipBorder(
            enabled = true,
            selected = selectedFilter == IslamicContentType.HADITH,
            borderColor = ObsidianBorderSubtle,
            selectedBorderColor = EmeraldPrimary
          )
        )

        FilterChip(
          selected = selectedFilter == IslamicContentType.DUA,
          onClick = { selectedFilter = IslamicContentType.DUA },
          label = { Text(StringsProvider.get("filter_duas", language), fontSize = 12.sp) },
          colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = EmeraldPrimary,
            selectedLabelColor = MaterialTheme.colorScheme.surface,
            containerColor = ObsidianSurfaceVariant,
            labelColor = TextWhite
          ),
          border = FilterChipDefaults.filterChipBorder(
            enabled = true,
            selected = selectedFilter == IslamicContentType.DUA,
            borderColor = ObsidianBorderSubtle,
            selectedBorderColor = EmeraldPrimary
          )
        )
      }
    }

    // Inspiration Cards
    items(filteredItems) { item ->
      InspirationCard(
        item = item,
        language = language,
        onCopy = {
          val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
          val textToCopy = "«${item.arabicText}»\n[${item.referenceAr}]"
          clipboard.setPrimaryClip(ClipData.newPlainText("NAFAS Noor", textToCopy))
          Toast.makeText(context, StringsProvider.get("copied_toast", language), Toast.LENGTH_SHORT).show()
        },
        onShare = {
          val shareText = "«${item.arabicText}»\n\n${item.reference(language)}\n\n(عبر تطبيق نَفَس – NAFAS للإقلاع عن التدخين)"
          val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, shareText)
            type = "text/plain"
          }
          context.startActivity(Intent.createChooser(sendIntent, "مشاركة النص"))
        }
      )
    }

    item {
      Spacer(modifier = Modifier.height(16.dp))
    }
  }
}

@Composable
private fun InspirationCard(
  item: IslamicInspiration,
  language: AppLanguage,
  onCopy: () -> Unit,
  onShare: () -> Unit
) {
  val typeLabel = when (item.type) {
    IslamicContentType.QURAN -> "القرآن الكريم"
    IslamicContentType.HADITH -> "حديث نبوي شريف"
    IslamicContentType.DUA -> "دعاء واستعانة"
  }

  val typeColor = when (item.type) {
    IslamicContentType.QURAN -> GoldPrimary
    IslamicContentType.HADITH -> EmeraldPrimaryLight
    IslamicContentType.DUA -> EmeraldPrimary
  }

  Box(
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(20.dp))
      .background(ObsidianSurfaceVariant)
      .border(1.dp, ObsidianCardBorder, RoundedCornerShape(20.dp))
      .padding(18.dp)
      .testTag("inspiration_card_${item.id}")
  ) {
    Column {
      // Header tag & action buttons
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(typeColor.copy(alpha = 0.15f))
            .border(1.dp, typeColor.copy(alpha = 0.3f), RoundedCornerShape(10.dp))
            .padding(horizontal = 10.dp, vertical = 4.dp)
        ) {
          Text(
            text = typeLabel,
            style = MaterialTheme.typography.labelSmall.copy(
              color = typeColor,
              fontWeight = FontWeight.Bold,
              fontSize = 11.sp
            )
          )
        }

        Row {
          IconButton(onClick = onCopy, modifier = Modifier.size(34.dp)) {
            Icon(
              imageVector = Icons.Default.ContentCopy,
              contentDescription = "Copy",
              tint = TextMuted,
              modifier = Modifier.size(18.dp)
            )
          }
          IconButton(onClick = onShare, modifier = Modifier.size(34.dp)) {
            Icon(
              imageVector = Icons.Default.Share,
              contentDescription = "Share",
              tint = TextMuted,
              modifier = Modifier.size(18.dp)
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      // Arabic text in beautiful Amiri script
      Text(
        text = "«" + item.arabicText + "»",
        fontFamily = AmiriFontFamily,
        style = MaterialTheme.typography.headlineSmall.copy(
          color = TextWhite,
          fontSize = 19.sp,
          lineHeight = 30.sp,
          fontWeight = FontWeight.Normal
        )
      )

      if (language != AppLanguage.ARABIC) {
        Spacer(modifier = Modifier.height(8.dp))
        Text(
          text = item.translation(language),
          style = MaterialTheme.typography.bodySmall.copy(
            color = TextMuted,
            fontSize = 12.sp,
            lineHeight = 18.sp
          )
        )
      }

      Spacer(modifier = Modifier.height(12.dp))

      // Reference source
      Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
          imageVector = Icons.Default.MenuBook,
          contentDescription = null,
          tint = GoldPrimary,
          modifier = Modifier.size(14.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
          text = item.reference(language),
          style = MaterialTheme.typography.labelSmall.copy(
            color = GoldPrimary,
            fontWeight = FontWeight.SemiBold,
            fontSize = 11.sp
          )
        )
      }

      Spacer(modifier = Modifier.height(8.dp))

      // Benefit / reflection box
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(12.dp))
          .background(EmeraldContainer.copy(alpha = 0.35f))
          .padding(10.dp)
      ) {
        Text(
          text = "💡 ${item.benefit(language)}",
          style = MaterialTheme.typography.bodySmall.copy(
            color = EmeraldPrimaryLight,
            fontSize = 12.sp,
            lineHeight = 18.sp
          )
        )
      }
    }
  }
}
