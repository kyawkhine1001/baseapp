package com.kkk.baseapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kkk.baseapp.data.db.dao.MovieDao
import com.kkk.baseapp.data.db.entity.MovieEntity
import com.kkk.baseapp.data.db.typeconverter.IntListTypeConverter

@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
@TypeConverters(IntListTypeConverter::class)
abstract class MyDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao

    companion object {
        private var instance: MyDatabase? = null

        fun getInstance(context: Context): MyDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(context, MyDatabase::class.java, "MyDBName")
                    .allowMainThreadQueries()
                    .build()
            }
            return instance as MyDatabase
        }
    }
}