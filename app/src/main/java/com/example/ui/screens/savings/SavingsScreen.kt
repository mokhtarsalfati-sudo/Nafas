package com.example.ui.screens.savings

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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Savings
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.WishlistEntity
import com.example.data.localization.AppLanguage
import com.example.data.localization.StringsProvider
import com.example.ui.theme.EmeraldContainer
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.EmeraldPrimaryLight
import com.example.ui.theme.GoldContainer
import com.example.ui.theme.GoldDark
import com.example.ui.theme.GoldLight
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
fun SavingsScreen(
  viewModel: NafasViewModel,
  modifier: Modifier = Modifier
) {
  val userProfile by viewModel.userProfile.collectAsState()
  val currentTime by viewModel.currentTime.collectAsState()
  val wishlistItems by viewModel.wishlist.collectAsState()
  val language = viewModel.currentLanguage

  val elapsedMillis = (currentTime - userProfile.quitTimestampMillis).coerceAtLeast(0L)
  val elapsedSeconds = elapsedMillis / 1000L

  val cigsAvoided = (elapsedSeconds.toDouble() / 86400.0) * userProfile.cigarettesPerDay
  val totalSaved = cigsAvoided * userProfile.costPerCigarette
  val dailyCost = userProfile.dailyCost
  val weeklyCost = dailyCost * 7.0
  val monthlyCost = dailyCost * 30.0
  val yearlyCost = dailyCost * 365.0
  val fiveYearsCost = yearlyCost * 5.0
  val tenYearsCost = yearlyCost * 10.0
  val packsAvoided = if (userProfile.cigarettesPerPack > 0) cigsAvoided / userProfile.cigarettesPerPack else 0.0

  var showAddGoalDialog by remember { mutableStateOf(false) }
  var newGoalName by remember { mutableStateOf("") }
  var newGoalCost by remember { mutableStateOf("") }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    // Grand Total Saved Banner
    item {
      Spacer(modifier = Modifier.height(4.dp))
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(26.dp))
          .background(
            Brush.verticalGradient(
              listOf(GoldContainer, ObsidianSurfaceVariant)
            )
          )
          .border(1.5.dp, GoldPrimary, RoundedCornerShape(26.dp))
          .padding(22.dp)
          .testTag("savings_total_card")
      ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
          Box(
            modifier = Modifier
              .size(44.dp)
              .clip(CircleShape)
              .background(GoldPrimary.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.Savings,
              contentDescription = "Savings",
              tint = GoldLight,
              modifier = Modifier.size(24.dp)
            )
          }

          Spacer(modifier = Modifier.height(10.dp))

          Text(
            text = StringsProvider.get("total_savings", language),
            style = MaterialTheme.typography.labelMedium.copy(
              color = GoldLight,
              fontWeight = FontWeight.SemiBold
            )
          )

          Spacer(modifier = Modifier.height(6.dp))

          Text(
            text = String.format("%.2f %s", totalSaved, userProfile.currencySymbol),
            style = MaterialTheme.typography.displayMedium.copy(
              color = TextWhite,
              fontWeight = FontWeight.Bold,
              fontSize = 34.sp
            )
          )

          Spacer(modifier = Modifier.height(8.dp))

          Text(
            text = String.format(
              "≈ %.1f %s",
              packsAvoided,
              StringsProvider.get("unsmoked_packs", language)
            ),
            style = MaterialTheme.typography.bodySmall.copy(
              color = TextMuted,
              fontSize = 12.sp
            )
          )
        }
      }
    }

    // Rate Projections Grid
    item {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(20.dp))
          .background(ObsidianSurfaceVariant)
          .border(1.dp, ObsidianCardBorder, RoundedCornerShape(20.dp))
          .padding(18.dp)
      ) {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
          Text(
            text = StringsProvider.get("savings_subtitle", language),
            style = MaterialTheme.typography.titleSmall.copy(
              color = GoldPrimary,
              fontWeight = FontWeight.Bold
            )
          )

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            RateRow(
              label = StringsProvider.get("daily_rate", language),
              value = String.format("%.2f %s", dailyCost, userProfile.currencySymbol),
              modifier = Modifier.weight(1f)
            )
            RateRow(
              label = StringsProvider.get("weekly_rate", language),
              value = String.format("%.2f %s", weeklyCost, userProfile.currencySymbol),
              modifier = Modifier.weight(1f)
            )
          }

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            RateRow(
              label = StringsProvider.get("monthly_rate", language),
              value = String.format("%.2f %s", monthlyCost, userProfile.currencySymbol),
              modifier = Modifier.weight(1f)
            )
            RateRow(
              label = StringsProvider.get("yearly_rate", language),
              value = String.format("%.2f %s", yearlyCost, userProfile.currencySymbol),
              modifier = Modifier.weight(1f)
            )
          }

          Spacer(modifier = Modifier.height(4.dp))

          // 5-Year and 10-Year Long Term Forecast
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(12.dp))
              .background(EmeraldContainer.copy(alpha = 0.4f))
              .border(1.dp, EmeraldPrimary.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
              .padding(12.dp)
          ) {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Text(
                  text = StringsProvider.get("projected_5years", language),
                  style = MaterialTheme.typography.bodySmall.copy(color = TextWhite)
                )
                Text(
                  text = String.format("%.0f %s", fiveYearsCost, userProfile.currencySymbol),
                  style = MaterialTheme.typography.bodySmall.copy(
                    color = EmeraldPrimaryLight,
                    fontWeight = FontWeight.Bold
                  )
                )
              }
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Text(
                  text = StringsProvider.get("projected_10years", language),
                  style = MaterialTheme.typography.bodySmall.copy(color = TextWhite)
                )
                Text(
                  text = String.format("%.0f %s", tenYearsCost, userProfile.currencySymbol),
                  style = MaterialTheme.typography.bodySmall.copy(
                    color = GoldPrimary,
                    fontWeight = FontWeight.Bold
                  )
                )
              }
            }
          }
        }
      }
    }

    // Wishlist / Goals Header & Button
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(
            text = StringsProvider.get("wishlist_title", language),
            style = MaterialTheme.typography.titleMedium.copy(
              color = GoldPrimary,
              fontWeight = FontWeight.Bold
            )
          )
          Text(
            text = StringsProvider.get("wishlist_desc", language),
            style = MaterialTheme.typography.bodySmall.copy(color = TextMuted, fontSize = 11.sp)
          )
        }

        IconButton(
          onClick = { showAddGoalDialog = true },
          modifier = Modifier
            .size(38.dp)
            .clip(CircleShape)
            .background(EmeraldPrimary)
            .testTag("add_goal_button")
        ) {
          Icon(Icons.Default.Add, contentDescription = "Add Goal", tint = ObsidianSurface)
        }
      }
    }

    // Wishlist Items List
    items(wishlistItems) { goal ->
      GoalItemCard(
        goal = goal,
        totalSaved = totalSaved,
        dailyCost = dailyCost,
        currencySymbol = userProfile.currencySymbol,
        language = language,
        onToggle = { viewModel.toggleWishlistItem(goal) },
        onDelete = { viewModel.deleteWishlistItem(goal) }
      )
    }

    item {
      Spacer(modifier = Modifier.height(16.dp))
    }
  }

  // Add Goal Dialog
  if (showAddGoalDialog) {
    BasicAlertDialog(
      onDismissRequest = { showAddGoalDialog = false },
      modifier = Modifier.testTag("add_goal_dialog")
    ) {
      Box(
        modifier = Modifier
          .clip(RoundedCornerShape(24.dp))
          .background(ObsidianSurface)
          .border(1.dp, GoldPrimary, RoundedCornerShape(24.dp))
          .padding(24.dp)
      ) {
        Column {
          Text(
            text = StringsProvider.get("add_goal", language),
            style = MaterialTheme.typography.titleLarge.copy(
              color = GoldPrimary,
              fontWeight = FontWeight.Bold
            )
          )

          Spacer(modifier = Modifier.height(16.dp))

          OutlinedTextField(
            value = newGoalName,
            onValueChange = { newGoalName = it },
            label = { Text(StringsProvider.get("goal_name", language), fontSize = 12.sp) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth().testTag("input_goal_name"),
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = EmeraldPrimary,
              unfocusedBorderColor = ObsidianCardBorder,
              focusedTextColor = TextWhite,
              unfocusedTextColor = TextWhite
            )
          )

          Spacer(modifier = Modifier.height(12.dp))

          OutlinedTextField(
            value = newGoalCost,
            onValueChange = { newGoalCost = it },
            label = { Text(StringsProvider.get("goal_cost", language), fontSize = 12.sp) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth().testTag("input_goal_cost"),
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = EmeraldPrimary,
              unfocusedBorderColor = ObsidianCardBorder,
              focusedTextColor = TextWhite,
              unfocusedTextColor = TextWhite
            )
          )

          Spacer(modifier = Modifier.height(18.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
          ) {
            OutlinedButton(
              onClick = { showAddGoalDialog = false },
              shape = RoundedCornerShape(12.dp)
            ) {
              Text(text = StringsProvider.get("cancel", language), color = TextMuted)
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(
              onClick = {
                val cost = newGoalCost.toDoubleOrNull() ?: 0.0
                if (newGoalName.isNotBlank() && cost > 0) {
                  viewModel.addWishlistItem(newGoalName, cost)
                  newGoalName = ""
                  newGoalCost = ""
                  showAddGoalDialog = false
                }
              },
              colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
              shape = RoundedCornerShape(12.dp),
              modifier = Modifier.testTag("confirm_add_goal")
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
  }
}

@Composable
private fun RateRow(
  label: String,
  value: String,
  modifier: Modifier = Modifier
) {
  Column(modifier = modifier) {
    Text(
      text = label,
      style = MaterialTheme.typography.labelSmall.copy(color = TextMuted, fontSize = 11.sp)
    )
    Text(
      text = value,
      style = MaterialTheme.typography.bodyMedium.copy(
        color = TextWhite,
        fontWeight = FontWeight.SemiBold
      )
    )
  }
}

@Composable
private fun GoalItemCard(
  goal: WishlistEntity,
  totalSaved: Double,
  dailyCost: Double,
  currencySymbol: String,
  language: AppLanguage,
  onToggle: () -> Unit,
  onDelete: () -> Unit
) {
  val progress = if (goal.targetCost > 0) (totalSaved / goal.targetCost).toFloat().coerceIn(0f, 1f) else 1f
  val isCompleted = goal.isPurchased || totalSaved >= goal.targetCost
  val remainingCost = (goal.targetCost - totalSaved).coerceAtLeast(0.0)
  val daysRemaining = if (dailyCost > 0) (remainingCost / dailyCost).toInt() else 0

  Box(
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(18.dp))
      .background(
        if (goal.isPurchased) EmeraldContainer.copy(alpha = 0.3f)
        else ObsidianSurfaceVariant
      )
      .border(
        1.dp,
        if (goal.isPurchased) EmeraldPrimary.copy(alpha = 0.5f)
        else ObsidianCardBorder,
        RoundedCornerShape(18.dp)
      )
      .padding(16.dp)
      .testTag("goal_card_${goal.id}")
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
              .size(36.dp)
              .clip(CircleShape)
              .background(
                if (goal.isPurchased) EmeraldPrimary.copy(alpha = 0.2f)
                else GoldPrimary.copy(alpha = 0.15f)
              ),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = if (goal.isPurchased) Icons.Default.CheckCircle else Icons.Default.CardGiftcard,
              contentDescription = null,
              tint = if (goal.isPurchased) EmeraldPrimaryLight else GoldPrimary,
              modifier = Modifier.size(20.dp)
            )
          }

          Spacer(modifier = Modifier.width(12.dp))

          Column {
            Text(
              text = goal.title,
              style = MaterialTheme.typography.titleSmall.copy(
                color = TextWhite,
                fontWeight = FontWeight.SemiBold
              )
            )
            Text(
              text = String.format("%.0f %s", goal.targetCost, currencySymbol),
              style = MaterialTheme.typography.bodySmall.copy(
                color = GoldPrimary,
                fontWeight = FontWeight.Medium
              )
            )
          }
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
          IconButton(onClick = onToggle) {
            Icon(
              imageVector = Icons.Default.Check,
              contentDescription = "Toggle Complete",
              tint = if (goal.isPurchased) EmeraldPrimaryLight else TextMuted
            )
          }

          IconButton(onClick = onDelete) {
            Icon(
              imageVector = Icons.Default.DeleteOutline,
              contentDescription = "Delete",
              tint = TextMuted
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      LinearProgressIndicator(
        progress = { progress },
        modifier = Modifier
          .fillMaxWidth()
          .height(6.dp)
          .clip(RoundedCornerShape(3.dp)),
        color = if (goal.isPurchased) EmeraldPrimaryLight else GoldPrimary,
        trackColor = ObsidianCardBorder
      )

      Spacer(modifier = Modifier.height(6.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Text(
          text = String.format("%.0f%%", progress * 100),
          style = MaterialTheme.typography.labelSmall.copy(
            color = if (goal.isPurchased) EmeraldPrimaryLight else GoldPrimary,
            fontWeight = FontWeight.Bold
          )
        )

        Text(
          text = if (isCompleted) StringsProvider.get("goal_achieved", language)
          else "${StringsProvider.get("goal_days_left", language)} $daysRemaining ${StringsProvider.get("days", language)}",
          style = MaterialTheme.typography.labelSmall.copy(
            color = if (isCompleted) EmeraldPrimaryLight else TextMuted,
            fontSize = 11.sp
          )
        )
      }
    }
  }
}
