package com.example.ui.screens.sos

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.widget.Toast
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.localization.StringsProvider
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
import com.example.ui.viewmodel.BreathingPhase
import com.example.ui.viewmodel.NafasViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SosScreen(
  viewModel: NafasViewModel,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  val language = viewModel.currentLanguage
  val isBreathingActive by viewModel.isBreathingActive.collectAsState()
  val breathingPhase by viewModel.breathingPhase.collectAsState()
  val phaseSecondsLeft by viewModel.phaseSecondsLeft.collectAsState()
  val phaseTotalSeconds by viewModel.phaseTotalSeconds.collectAsState()
  val breathingCycle by viewModel.breathingCycle.collectAsState()
  val breathingMode by viewModel.breathingMode.collectAsState()

  var showLogDialog by remember { mutableStateOf(false) }
  var selectedTrigger by remember { mutableStateOf("Stress") }

  // Haptic feedback on phase changes
  LaunchedEffect(breathingPhase) {
    if (isBreathingActive) {
      try {
        val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
          val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
          vibratorManager?.defaultVibrator
        } else {
          @Suppress("DEPRECATION")
          context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
          vibrator?.vibrate(VibrationEffect.createOneShot(70, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
          @Suppress("DEPRECATION")
          vibrator?.vibrate(70)
        }
      } catch (_: Exception) {}
    }
  }

  // Animation scale for breathing circle
  val targetScale = when {
    !isBreathingActive -> 1.0f
    breathingPhase == BreathingPhase.INHALE -> 1.35f
    breathingPhase == BreathingPhase.HOLD -> 1.35f
    breathingPhase == BreathingPhase.EXHALE -> 0.95f
    breathingPhase == BreathingPhase.HOLD_POST -> 0.95f
    else -> 1.0f
  }

  val animatedScale by animateFloatAsState(
    targetValue = targetScale,
    animationSpec = tween(
      durationMillis = if (isBreathingActive) phaseTotalSeconds * 1000 else 600,
      easing = FastOutSlowInEasing
    ),
    label = "breathingScale"
  )

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    // Header
    item {
      Spacer(modifier = Modifier.height(4.dp))
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(24.dp))
          .background(ObsidianSurfaceVariant)
          .border(1.dp, CrimsonSOS.copy(alpha = 0.5f), RoundedCornerShape(24.dp))
          .padding(20.dp)
      ) {
        Column {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(CrimsonSOS.copy(alpha = 0.2f)),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.SelfImprovement,
                contentDescription = "SOS Urge",
                tint = CrimsonSOS,
                modifier = Modifier.size(22.dp)
              )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Text(
              text = StringsProvider.get("sos_title", language),
              style = MaterialTheme.typography.titleLarge.copy(
                color = GoldPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 19.sp
              )
            )
          }

          Spacer(modifier = Modifier.height(8.dp))

          Text(
            text = StringsProvider.get("sos_subtitle", language),
            style = MaterialTheme.typography.bodySmall.copy(
              color = TextMuted,
              lineHeight = 18.sp
            )
          )
        }
      }
    }

    // Breathing Mode Selector
    item {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(16.dp))
          .background(ObsidianSurfaceVariant)
          .border(1.dp, ObsidianBorderSubtle, RoundedCornerShape(16.dp))
          .padding(4.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
      ) {
        ModeTab(
          title = StringsProvider.get("breath_mode_478", language),
          isSelected = breathingMode == "4-7-8",
          onClick = {
            if (!isBreathingActive) viewModel.startBreathing("4-7-8")
            else {
              viewModel.stopBreathing()
              viewModel.startBreathing("4-7-8")
            }
          },
          modifier = Modifier.weight(1f)
        )

        ModeTab(
          title = StringsProvider.get("breath_mode_box", language),
          isSelected = breathingMode == "box",
          onClick = {
            if (!isBreathingActive) viewModel.startBreathing("box")
            else {
              viewModel.stopBreathing()
              viewModel.startBreathing("box")
            }
          },
          modifier = Modifier.weight(1f)
        )
      }
    }

    // Interactive Animated Breathing Circle
    item {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(300.dp)
          .clip(RoundedCornerShape(28.dp))
          .background(
            Brush.radialGradient(
              listOf(
                EmeraldContainer.copy(alpha = 0.5f),
                ObsidianSurfaceVariant
              )
            )
          )
          .border(1.dp, EmeraldPrimary.copy(alpha = 0.4f), RoundedCornerShape(28.dp))
          .testTag("breathing_arena"),
        contentAlignment = Alignment.Center
      ) {
        // Outer pulsing ring
        Box(
          modifier = Modifier
            .size(230.dp)
            .scale(animatedScale)
            .clip(CircleShape)
            .background(
              Brush.radialGradient(
                listOf(
                  EmeraldPrimary.copy(alpha = 0.25f),
                  GoldPrimary.copy(alpha = 0.05f)
                )
              )
            )
            .border(2.dp, EmeraldPrimary.copy(alpha = 0.6f), CircleShape)
        )

        // Inner solid core
        Box(
          modifier = Modifier
            .size(150.dp)
            .clip(CircleShape)
            .background(ObsidianSurface)
            .border(2.dp, GoldPrimary, CircleShape),
          contentAlignment = Alignment.Center
        ) {
          Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(12.dp)
          ) {
            Text(
              text = if (isBreathingActive) "$phaseSecondsLeft" else "NAFAS",
              style = MaterialTheme.typography.displayMedium.copy(
                color = GoldPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = if (isBreathingActive) 44.sp else 24.sp
              )
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
              text = if (isBreathingActive) {
                when (breathingPhase) {
                  BreathingPhase.INHALE -> StringsProvider.get("inhale", language)
                  BreathingPhase.HOLD, BreathingPhase.HOLD_POST -> StringsProvider.get("hold", language)
                  BreathingPhase.EXHALE -> StringsProvider.get("exhale", language)
                }
              } else {
                StringsProvider.get("start_breathing", language)
              },
              style = MaterialTheme.typography.labelSmall.copy(
                color = TextWhite,
                fontSize = 11.sp,
                textAlign = TextAlign.Center
              ),
              maxLines = 2
            )

            if (isBreathingActive) {
              Spacer(modifier = Modifier.height(2.dp))
              Text(
                text = "${StringsProvider.get("cycle", language)} $breathingCycle",
                style = MaterialTheme.typography.labelSmall.copy(
                  color = EmeraldPrimaryLight,
                  fontSize = 10.sp
                )
              )
            }
          }
        }
      }
    }

    // Breathing Start / Stop Button
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
      ) {
        if (!isBreathingActive) {
          Button(
            onClick = { viewModel.startBreathing(breathingMode) },
            colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
              .fillMaxWidth(0.85f)
              .height(52.dp)
              .testTag("start_breathing_button")
          ) {
            Icon(Icons.Default.PlayArrow, contentDescription = null, tint = ObsidianSurface)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = StringsProvider.get("start_breathing", language),
              style = MaterialTheme.typography.titleMedium.copy(
                color = ObsidianSurface,
                fontWeight = FontWeight.Bold
              )
            )
          }
        } else {
          Button(
            onClick = { viewModel.stopBreathing() },
            colors = ButtonDefaults.buttonColors(containerColor = CrimsonSOS),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
              .fillMaxWidth(0.85f)
              .height(52.dp)
              .testTag("stop_breathing_button")
          ) {
            Icon(Icons.Default.Stop, contentDescription = null, tint = TextWhite)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = StringsProvider.get("stop_breathing", language),
              style = MaterialTheme.typography.titleMedium.copy(
                color = TextWhite,
                fontWeight = FontWeight.Bold
              )
            )
          }
        }
      }
    }

    // "I Conquered This Craving" Victory Button
    item {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(20.dp))
          .background(EmeraldContainer)
          .border(1.5.dp, GoldPrimary, RoundedCornerShape(20.dp))
          .clickable { showLogDialog = true }
          .padding(18.dp)
          .testTag("craving_conquered_button"),
        contentAlignment = Alignment.Center
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.Center
        ) {
          Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = "Victory",
            tint = GoldPrimary,
            modifier = Modifier.size(26.dp)
          )
          Spacer(modifier = Modifier.width(10.dp))
          Text(
            text = StringsProvider.get("i_defeated_craving", language),
            style = MaterialTheme.typography.titleMedium.copy(
              color = TextWhite,
              fontWeight = FontWeight.Bold,
              fontSize = 16.sp
            )
          )
        }
      }
    }

    // Emergency Grounding Tips
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
            text = StringsProvider.get("emergency_tips_title", language),
            style = MaterialTheme.typography.titleSmall.copy(
              color = GoldPrimary,
              fontWeight = FontWeight.Bold
            )
          )

          Text(
            text = StringsProvider.get("tip_1", language),
            style = MaterialTheme.typography.bodySmall.copy(color = TextWhite, lineHeight = 20.sp)
          )
          Text(
            text = StringsProvider.get("tip_2", language),
            style = MaterialTheme.typography.bodySmall.copy(color = TextWhite, lineHeight = 20.sp)
          )
          Text(
            text = StringsProvider.get("tip_3", language),
            style = MaterialTheme.typography.bodySmall.copy(color = TextWhite, lineHeight = 20.sp)
          )
          Text(
            text = StringsProvider.get("tip_4", language),
            style = MaterialTheme.typography.bodySmall.copy(color = TextWhite, lineHeight = 20.sp)
          )
          Text(
            text = StringsProvider.get("tip_5", language),
            style = MaterialTheme.typography.bodySmall.copy(color = TextWhite, lineHeight = 20.sp)
          )
        }
      }
    }

    item {
      Spacer(modifier = Modifier.height(16.dp))
    }
  }

  // Trigger Logger Dialog
  if (showLogDialog) {
    BasicAlertDialog(
      onDismissRequest = { showLogDialog = false },
      modifier = Modifier.testTag("craving_log_dialog")
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
            text = StringsProvider.get("i_defeated_craving", language),
            style = MaterialTheme.typography.titleLarge.copy(
              color = GoldPrimary,
              fontWeight = FontWeight.Bold
            )
          )

          Spacer(modifier = Modifier.height(8.dp))

          Text(
            text = StringsProvider.get("select_trigger", language),
            style = MaterialTheme.typography.bodySmall.copy(color = TextMuted)
          )

          Spacer(modifier = Modifier.height(14.dp))

          val triggers = listOf(
            Pair("Stress", StringsProvider.get("trigger_stress", language)),
            Pair("Habit", StringsProvider.get("trigger_habit", language)),
            Pair("Social", StringsProvider.get("trigger_social", language)),
            Pair("Boredom", StringsProvider.get("trigger_boredom", language))
          )

          triggers.forEach { (key, label) ->
            val isSelected = selectedTrigger == key
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(if (isSelected) EmeraldPrimary.copy(alpha = 0.2f) else ObsidianSurfaceVariant)
                .border(
                  1.dp,
                  if (isSelected) EmeraldPrimary else ObsidianBorderSubtle,
                  RoundedCornerShape(12.dp)
                )
                .clickable { selectedTrigger = key }
                .padding(14.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium.copy(
                  color = if (isSelected) EmeraldPrimaryLight else TextWhite,
                  fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                )
              )
            }
            Spacer(modifier = Modifier.height(8.dp))
          }

          Spacer(modifier = Modifier.height(12.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
          ) {
            OutlinedButton(
              onClick = { showLogDialog = false },
              shape = RoundedCornerShape(12.dp)
            ) {
              Text(
                text = StringsProvider.get("cancel", language),
                color = TextMuted
              )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(
              onClick = {
                viewModel.logCraving(intensity = 4, trigger = selectedTrigger)
                showLogDialog = false
                Toast.makeText(
                  context,
                  StringsProvider.get("craving_logged_success", language),
                  Toast.LENGTH_LONG
                ).show()
              },
              colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
              shape = RoundedCornerShape(12.dp),
              modifier = Modifier.testTag("confirm_log_craving")
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
private fun ModeTab(
  title: String,
  isSelected: Boolean,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
      .clip(RoundedCornerShape(12.dp))
      .background(if (isSelected) EmeraldPrimary.copy(alpha = 0.2f) else ObsidianSurfaceVariant)
      .border(
        1.dp,
        if (isSelected) EmeraldPrimary else ObsidianBorderSubtle,
        RoundedCornerShape(12.dp)
      )
      .clickable { onClick() }
      .padding(vertical = 10.dp, horizontal = 12.dp),
    contentAlignment = Alignment.Center
  ) {
    Text(
      text = title,
      style = MaterialTheme.typography.labelMedium.copy(
        color = if (isSelected) EmeraldPrimaryLight else TextMuted,
        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
        fontSize = 12.sp
      ),
      textAlign = TextAlign.Center
    )
  }
}
