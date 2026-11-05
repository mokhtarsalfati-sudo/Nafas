package com.example.ui.screens.onboarding

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.localization.AppLanguage
import com.example.data.localization.StringsProvider
import com.example.ui.theme.EmeraldContainer
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.EmeraldPrimaryLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.ObsidianBackground
import com.example.ui.theme.ObsidianBorderSubtle
import com.example.ui.theme.ObsidianCardBorder
import com.example.ui.theme.ObsidianSurfaceVariant
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite
import com.example.ui.viewmodel.NafasViewModel

@Composable
fun OnboardingScreen(
  viewModel: NafasViewModel,
  onComplete: () -> Unit,
  modifier: Modifier = Modifier
) {
  var selectedLanguage by remember { mutableStateOf(AppLanguage.ARABIC) }
  var isQuitNow by remember { mutableStateOf(true) }
  var daysAgo by remember { mutableIntStateOf(0) }
  var cigsPerDay by remember { mutableIntStateOf(20) }
  var packPriceText by remember { mutableStateOf("25") }
  var selectedCurrency by remember { mutableStateOf("ر.س") }

  val currencies = listOf("ر.س", "د.إ", "ج.م", "د.م", "د.ج", "$", "€", "£")

  val scrollState = rememberScrollState()

  Box(
    modifier = modifier
      .fillMaxSize()
      .background(ObsidianBackground)
      .padding(horizontal = 20.dp)
      .testTag("onboarding_screen")
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .verticalScroll(scrollState)
        .padding(vertical = 32.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      // Logo & Welcome Header
      Box(
        modifier = Modifier
          .size(72.dp)
          .clip(CircleShape)
          .background(
            Brush.radialGradient(
              listOf(EmeraldPrimary.copy(alpha = 0.4f), EmeraldContainer)
            )
          )
          .border(2.dp, GoldPrimary, CircleShape),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = Icons.Default.Spa,
          contentDescription = "NAFAS Logo",
          tint = EmeraldPrimaryLight,
          modifier = Modifier.size(38.dp)
        )
      }

      Spacer(modifier = Modifier.height(14.dp))

      Text(
        text = StringsProvider.get("onboarding_welcome", selectedLanguage),
        style = MaterialTheme.typography.headlineMedium.copy(
          color = GoldPrimary,
          fontWeight = FontWeight.Bold,
          fontSize = 24.sp
        )
      )

      Spacer(modifier = Modifier.height(4.dp))

      Text(
        text = StringsProvider.get("onboarding_subtitle", selectedLanguage),
        style = MaterialTheme.typography.bodySmall.copy(
          color = TextMuted,
          textAlign = TextAlign.Center,
          lineHeight = 18.sp
        ),
        modifier = Modifier.padding(horizontal = 16.dp)
      )

      Spacer(modifier = Modifier.height(24.dp))

      // Language selection
      Text(
        text = StringsProvider.get("select_language", selectedLanguage),
        style = MaterialTheme.typography.titleSmall.copy(
          color = TextWhite,
          fontWeight = FontWeight.SemiBold
        ),
        modifier = Modifier.align(Alignment.Start)
      )

      Spacer(modifier = Modifier.height(8.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        AppLanguage.entries.forEach { lang ->
          val isSelected = selectedLanguage == lang
          Box(
            modifier = Modifier
              .weight(1f)
              .clip(RoundedCornerShape(12.dp))
              .background(if (isSelected) EmeraldPrimary.copy(alpha = 0.2f) else ObsidianSurfaceVariant)
              .border(
                1.dp,
                if (isSelected) EmeraldPrimary else ObsidianBorderSubtle,
                RoundedCornerShape(12.dp)
              )
              .clickable { selectedLanguage = lang }
              .padding(vertical = 10.dp),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = lang.displayName,
              style = MaterialTheme.typography.labelMedium.copy(
                color = if (isSelected) EmeraldPrimaryLight else TextWhite,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
              )
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(20.dp))

      // Quit Date selection
      Text(
        text = StringsProvider.get("quit_date_label", selectedLanguage),
        style = MaterialTheme.typography.titleSmall.copy(
          color = TextWhite,
          fontWeight = FontWeight.SemiBold
        ),
        modifier = Modifier.align(Alignment.Start)
      )

      Spacer(modifier = Modifier.height(8.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        Box(
          modifier = Modifier
            .weight(1f)
            .clip(RoundedCornerShape(14.dp))
            .background(if (isQuitNow) EmeraldPrimary.copy(alpha = 0.2f) else ObsidianSurfaceVariant)
            .border(
              1.dp,
              if (isQuitNow) EmeraldPrimary else ObsidianBorderSubtle,
              RoundedCornerShape(14.dp)
            )
            .clickable { isQuitNow = true }
            .padding(14.dp),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = StringsProvider.get("quit_now", selectedLanguage),
            style = MaterialTheme.typography.bodySmall.copy(
              color = if (isQuitNow) EmeraldPrimaryLight else TextWhite,
              fontWeight = if (isQuitNow) FontWeight.Bold else FontWeight.Normal,
              textAlign = TextAlign.Center
            )
          )
        }

        Box(
          modifier = Modifier
            .weight(1f)
            .clip(RoundedCornerShape(14.dp))
            .background(if (!isQuitNow) EmeraldPrimary.copy(alpha = 0.2f) else ObsidianSurfaceVariant)
            .border(
              1.dp,
              if (!isQuitNow) EmeraldPrimary else ObsidianBorderSubtle,
              RoundedCornerShape(14.dp)
            )
            .clickable { isQuitNow = false }
            .padding(14.dp),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = StringsProvider.get("quit_custom", selectedLanguage),
            style = MaterialTheme.typography.bodySmall.copy(
              color = if (!isQuitNow) EmeraldPrimaryLight else TextWhite,
              fontWeight = if (!isQuitNow) FontWeight.Bold else FontWeight.Normal,
              textAlign = TextAlign.Center
            )
          )
        }
      }

      if (!isQuitNow) {
        Spacer(modifier = Modifier.height(10.dp))
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(ObsidianSurfaceVariant)
            .border(1.dp, ObsidianBorderSubtle, RoundedCornerShape(14.dp))
            .padding(12.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.CalendarMonth, contentDescription = null, tint = GoldPrimary)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "توقفت منذ $daysAgo أيام",
              style = MaterialTheme.typography.bodySmall.copy(color = TextWhite)
            )
          }

          Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { if (daysAgo > 0) daysAgo-- }) {
              Icon(Icons.Default.Remove, contentDescription = "Decrease", tint = TextWhite)
            }
            Text(
              text = "$daysAgo",
              style = MaterialTheme.typography.titleMedium.copy(color = GoldPrimary, fontWeight = FontWeight.Bold),
              modifier = Modifier.padding(horizontal = 4.dp)
            )
            IconButton(onClick = { if (daysAgo < 365) daysAgo++ }) {
              Icon(Icons.Default.Add, contentDescription = "Increase", tint = TextWhite)
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(20.dp))

      // Cigarettes per day counter
      Text(
        text = StringsProvider.get("cigs_per_day", selectedLanguage),
        style = MaterialTheme.typography.titleSmall.copy(
          color = TextWhite,
          fontWeight = FontWeight.SemiBold
        ),
        modifier = Modifier.align(Alignment.Start)
      )

      Spacer(modifier = Modifier.height(8.dp))

      Row(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(14.dp))
          .background(ObsidianSurfaceVariant)
          .border(1.dp, ObsidianBorderSubtle, RoundedCornerShape(14.dp))
          .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        IconButton(
          onClick = { if (cigsPerDay > 1) cigsPerDay-- },
          modifier = Modifier
            .size(36.dp)
            .clip(CircleShape)
            .background(EmeraldContainer)
        ) {
          Icon(Icons.Default.Remove, contentDescription = "Decrease", tint = EmeraldPrimaryLight)
        }

        Text(
          text = "$cigsPerDay سيجارة / يوم",
          style = MaterialTheme.typography.titleMedium.copy(
            color = TextWhite,
            fontWeight = FontWeight.Bold,
            fontSize = 17.sp
          )
        )

        IconButton(
          onClick = { if (cigsPerDay < 100) cigsPerDay++ },
          modifier = Modifier
            .size(36.dp)
            .clip(CircleShape)
            .background(EmeraldContainer)
        ) {
          Icon(Icons.Default.Add, contentDescription = "Increase", tint = EmeraldPrimaryLight)
        }
      }

      Spacer(modifier = Modifier.height(20.dp))

      // Pack Price & Currency
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        OutlinedTextField(
          value = packPriceText,
          onValueChange = { packPriceText = it },
          label = { Text(StringsProvider.get("pack_price", selectedLanguage), fontSize = 12.sp) },
          keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
          modifier = Modifier.weight(1.3f).testTag("input_pack_price"),
          colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = EmeraldPrimary,
            unfocusedBorderColor = ObsidianCardBorder,
            focusedTextColor = TextWhite,
            unfocusedTextColor = TextWhite
          )
        )

        Column(modifier = Modifier.weight(1f)) {
          Text(
            text = StringsProvider.get("select_currency", selectedLanguage),
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
              .padding(horizontal = 12.dp, vertical = 14.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = selectedCurrency,
              style = MaterialTheme.typography.titleMedium.copy(
                color = GoldPrimary,
                fontWeight = FontWeight.Bold
              )
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(32.dp))

      // Start Button
      Button(
        onClick = {
          val price = packPriceText.toDoubleOrNull() ?: 25.0
          val quitMillis = if (isQuitNow) {
            System.currentTimeMillis()
          } else {
            System.currentTimeMillis() - (daysAgo * 86400000L)
          }
          viewModel.completeOnboarding(
            quitTimestamp = quitMillis,
            cigsPerDay = cigsPerDay,
            pricePack = price,
            cigsPack = 20,
            currency = selectedCurrency,
            langCode = selectedLanguage.code
          )
          onComplete()
        },
        colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
        shape = RoundedCornerShape(18.dp),
        modifier = Modifier
          .fillMaxWidth()
          .height(56.dp)
          .testTag("onboarding_start_button")
      ) {
        Text(
          text = StringsProvider.get("save_and_start", selectedLanguage),
          style = MaterialTheme.typography.titleMedium.copy(
            color = ObsidianBackground,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
          )
        )
      }
    }
  }
}
