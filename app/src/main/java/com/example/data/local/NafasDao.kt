package com.example.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CravingDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insert(craving: CravingLogEntity): Long

  @Query("SELECT * FROM cravings ORDER BY timestamp DESC")
  fun getAllCravings(): Flow<List<CravingLogEntity>>

  @Query("SELECT COUNT(*) FROM cravings")
  fun getCravingCount(): Flow<Int>

  @Query("DELETE FROM cravings")
  suspend fun clearAll()
}

@Dao
interface WishlistDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insert(item: WishlistEntity): Long

  @Update
  suspend fun update(item: WishlistEntity)

  @Delete
  suspend fun delete(item: WishlistEntity)

  @Query("SELECT * FROM wishlist ORDER BY targetCost ASC")
  fun getAllWishlist(): Flow<List<WishlistEntity>>

  @Query("DELETE FROM wishlist")
  suspend fun clearAll()
}

@Dao
interface BreathingDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insert(session: BreathingSessionEntity): Long

  @Query("SELECT COUNT(*) FROM breathing_sessions")
  fun getSessionCount(): Flow<Int>

  @Query("DELETE FROM breathing_sessions")
  suspend fun clearAll()
}
