package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
  entities = [CravingLogEntity::class, WishlistEntity::class, BreathingSessionEntity::class],
  version = 1,
  exportSchema = false
)
abstract class NafasDatabase : RoomDatabase() {
  abstract fun cravingDao(): CravingDao
  abstract fun wishlistDao(): WishlistDao
  abstract fun breathingDao(): BreathingDao

  companion object {
    @Volatile
    private var INSTANCE: NafasDatabase? = null

    fun getDatabase(context: Context): NafasDatabase {
      return INSTANCE ?: synchronized(this) {
        val instance = Room.databaseBuilder(
          context.applicationContext,
          NafasDatabase::class.java,
          "nafas_app_database"
        )
          .fallbackToDestructiveMigration()
          .build()
        INSTANCE = instance
        instance
      }
    }
  }
}
