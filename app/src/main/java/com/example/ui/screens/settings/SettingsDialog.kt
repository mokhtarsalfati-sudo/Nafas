package com.example.ui.screens.settings

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.localization.StringsProvider
import com.example.ui.components.MedicalDisclaimerCard
import com.example.ui.theme.CrimsonContainer
import com.example.ui.theme.CrimsonSOS
import com.example.ui.theme.EmeraldContainer
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.EmeraldPrimaryLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.ObsidianBorderSubtle
import com.example.ui.theme.ObsidianCardBorder
import com.example.ui.theme.ObsidianSurface
import com.example.ui.theme.ObsidianSurfaceVariant
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite
import com.example.ui.viewmodel.NafasViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsDialog(
  viewModel: NafasViewModel,
  onDismiss: () -> Unit
) {
  val userProfile by viewModel.userProfile.collectAsState()
  val language = viewModel.currentLanguage

  var cigsPerDay by remember { mutableIntStateOf(userProfile.cigarettesPerDay) }
  var packPriceText by remember { mutableStateOf(userProfile.pricePerPack.toString()) }
  var selectedCurrency by remember { mutableStateOf(userProfile.currencySymbol) }
  var showResetConfirm by remember { mutableStateOf(false) }

  val currencies = listOf("ر.س", "د.إ", "ج.م", "د.م", "د.ج", "$", "€", "£")

  BasicAlertDialog(
    onDismissRequest = onDismiss,
    modifier = Modifier.testTag("settings_dialog")
  ) {
    Box(
      modifier = Modifier
        .clip(RoundedCornerShape(24.dp))
        .background(ObsidianSurface)
        .border(1.dp, GoldPrimary.copy(alpha = 0.5f), RoundedCornerShape(24.dp))
        .padding(20.dp)
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .verticalScroll(rememberScrollState())
      ) {
        // Header
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = StringsProvider.get("settings_title", language),
            style = MaterialTheme.typography.titleLarge.copy(
              color = GoldPrimary,
              fontWeight = FontWeight.Bold
            )
          )
          IconButton(onClick = onDismiss) {
            Icon(Icons.Default.Close, contentDescription = "Close", tint = TextMuted)
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Cigs per day
        Text(
          text = StringsProvider.get("cigs_per_day", language),
          style = MaterialTheme.typography.labelMedium.copy(color = TextWhite)
        )

        Spacer(modifier = Modifier.height(6.dp))

        Row(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(ObsidianSurfaceVariant)
            .border(1.dp, ObsidianBorderSubtle, RoundedCornerShape(12.dp))
            .padding(10.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          IconButton(
            onClick = { if (cigsPerDay > 1) cigsPerDay-- },
            modifier = Modifier.size(32.dp).clip(CircleShape).background(EmeraldContainer)
          ) {
            Icon(Icons.Default.Remove, contentDescription = "Decrease", tint = EmeraldPrimaryLight)
          }

          Text(
            text = "$cigsPerDay ${StringsProvider.get("cigs_avoided", language).split(" ").first()}",
            style = MaterialTheme.typography.titleSmall.copy(color = TextWhite, fontWeight = FontWeight.Bold)
          )

          IconButton(
            onClick = { if (cigsPerDay < 100) cigsPerDay++ },
            modifier = Modifier.size(32.dp).clip(CircleShape).background(EmeraldContainer)
          ) {
            Icon(Icons.Default.Add, contentDescription = "Increase", tint = EmeraldPrimaryLight)
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Pack Price & Currency
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          OutlinedTextField(
            value = packPriceText,
            onValueChange = { packPriceText = it },
            label = { Text(StringsProvider.get("pack_price", language), fontSize = 11.sp) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.weight(1.3f),
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = EmeraldPrimary,
              unfocusedBorderColor = ObsidianCardBorder,
              focusedTextColor = TextWhite,
              unfocusedTextColor = TextWhite
            )
          )

          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = StringsProvider.get("select_currency", language),
              style = MaterialTheme.typography.labelSmall.copy(color = TextMuted)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(ObsidianSurfaceVariant)
                .border(1.dp, ObsidianCardBorder, RoundedCornerShape(12.dp))
                .clickable {
                  val nextIdx = (currencies.indexOf(selectedCurrency) + 1) % currencies.size
                  selectedCurrency = currencies[nextIdx]
                }
                .padding(vertical = 14.dp),
              horizontalArrangement = Arrangement.Center,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(
                text = selectedCurrency,
                style = MaterialTheme.typography.titleMedium.copy(color = GoldPrimary, fontWeight = FontWeight.Bold)
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Reset Counter Option
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(CrimsonContainer.copy(alpha = 0.4f))
            .border(1.dp, CrimsonSOS.copy(alpha = 0.5f), RoundedCornerShape(14.dp))
            .clickable { showResetConfirm = true }
            .padding(12.dp)
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Refresh, contentDescription = null, tint = CrimsonSOS, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(10.dp))
            Column {
              Text(
                text = StringsProvider.get("reset_all", language),
                style = MaterialTheme.typography.titleSmall.copy(color = CrimsonSOS, fontWeight = FontWeight.Bold, fontSize = 13.sp)
              )
              Text(
                text = "ابدأ بداية جديدة بنية خالصة",
                style = MaterialTheme.typography.bodySmall.copy(color = TextMuted, fontSize = 11.sp)
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Medical Disclaimer
        MedicalDisclaimerCard(language = language)

        Spacer(modifier = Modifier.height(18.dp))

        // Action Buttons
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.End
        ) {
          OutlinedButton(
            onClick = onDismiss,
            shape = RoundedCornerShape(12.dp)
          ) {
            Text(text = StringsProvider.get("cancel", language), color = TextMuted)
          }

          Spacer(modifier = Modifier.width(8.dp))

          Button(
            onClick = {
              val price = packPriceText.toDoubleOrNull() ?: userProfile.pricePerPack
              viewModel.updateProfile(
                quitTimestamp = userProfile.quitTimestampMillis,
                cigsPerDay = cigsPerDay,
                pricePack = price,
                cigsPack = userProfile.cigarettesPerPack,
                currency = selectedCurrency,
                langCode = userProfile.languageCode
              )
              onDismiss()
            },
            colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
            shape = RoundedCornerShape(12.dp)
          ) {
            Text(
              text = StringsProvider.get("save", language),
              color = ObsidianSurface,
              fontWeight = FontWeight.Bold
            )
          }
        }
      }
    }
  }

  // Confirmation Alert for Reset
  if (showResetConfirm) {
    BasicAlertDialog(
      onDismissRequest = { showResetConfirm = false }
    ) {
      Box(
        modifier = Modifier
          .clip(RoundedCornerShape(20.dp))
          .background(ObsidianSurface)
          .border(1.5.dp, CrimsonSOS, RoundedCornerShape(20.dp))
          .padding(20.dp)
      ) {
        Column {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Warning, contentDescription = null, tint = CrimsonSOS)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = StringsProvider.get("reset_all", language),
              style = MaterialTheme.typography.titleMedium.copy(color = CrimsonSOS, fontWeight = FontWeight.Bold)
            )
          }

          Spacer(modifier = Modifier.height(10.dp))

          Text(
            text = StringsProvider.get("reset_confirm", language),
            style = MaterialTheme.typography.bodySmall.copy(color = TextWhite, lineHeight = 20.sp)
          )

          Spacer(modifier = Modifier.height(18.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
          ) {
            OutlinedButton(
              onClick = { showResetConfirm = false },
              shape = RoundedCornerShape(12.dp)
            ) {
              Text(StringsProvider.get("cancel", language), color = TextMuted)
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(
              onClick = {
                viewModel.resetCounter()
                showResetConfirm = false
                onDismiss()
              },
              colors = ButtonDefaults.buttonColors(containerColor = CrimsonSOS),
              shape = RoundedCornerShape(12.dp)
            ) {
              Text(
                text = "تصفير الآن",
                color = TextWhite,
                fontWeight = FontWeight.Bold
              )
            }
          }
        }
      }
    }
  }
}
