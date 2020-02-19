package com.example.travelcartest.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.travelcartest.data.converters.DateConverter
import com.example.travelcartest.data.converters.EquipmentsConverter
import com.example.travelcartest.data.dao.AccountDao
import com.example.travelcartest.data.dao.VehicleDao
import com.example.travelcartest.data.entity.Account
import com.example.travelcartest.data.entity.Vehicle

import java.util.concurrent.Executors

@Database(
    entities = [Vehicle::class, Account::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    EquipmentsConverter::class,
    DateConverter::class
)
abstract class TravelCarDb : RoomDatabase() {
    abstract fun vehicleDao(): VehicleDao
    abstract fun accountDao(): AccountDao


    companion object {
        // For Singleton instantiation
        @Volatile
        private var instance: TravelCarDb? = null

        fun getInstance(context: Context): TravelCarDb {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }
        }

        // Create and pre-populate the database. See this article for more details:
        // https://medium.com/google-developers/7-pro-tips-for-room-fbadea4bfbd1#4785
        private fun buildDatabase(context: Context): TravelCarDb {
            return Room.databaseBuilder(context, TravelCarDb::class.java, "TravelCar.db")
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        Executors.newSingleThreadScheduledExecutor().execute {
                        //configure db

                        }
                    }
                })
                .build()
        }


    }
}