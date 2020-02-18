package com.example.travelcartest.data.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(obj: T) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(objs: List<T>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertIgnoringForeignKeyErrors(obj: T) {
        try {
            insert(obj)
        } catch (e: Exception) {
            // android.database.sqlite.SQLiteConstraintException: FOREIGN KEY constraint failed (code 787)
            e.printStackTrace()
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertIgnoringForeignKeyErrors(obj: List<T>) {
        try {
            insert(obj)
        } catch (e: Exception) {
            // android.database.sqlite.SQLiteConstraintException: FOREIGN KEY constraint failed (code 787)
            e.printStackTrace()
        }
    }

    @Update
    fun update(vararg obj: T)

    @Update
    fun updateList(objs: List<T>)

    @Delete
    fun delete(vararg obj: T): Int
}