package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cravings")
data class CravingLogEntity(
  @PrimaryKey(autoGenerate = true)
  val id: Long = 0,
  val timestamp: Long = System.currentTimeMillis(),
  val intensity: Int = 3, // 1 to 5
  val trigger: String = "Stress",
  val note: String = ""
)

@Entity(tableName = "wishlist")
data class WishlistEntity(
  @PrimaryKey(autoGenerate = true)
  val id: Long = 0,
  val title: String,
  val targetCost: Double,
  val isPurchased: Boolean = false,
  val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "breathing_sessions")
data class BreathingSessionEntity(
  @PrimaryKey(autoGenerate = true)
  val id: Long = 0,
  val timestamp: Long = System.currentTimeMillis(),
  val mode: String = "4-7-8",
  val durationSeconds: Int = 60
)
