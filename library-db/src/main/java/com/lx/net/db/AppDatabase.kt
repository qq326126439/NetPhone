package com.lx.net.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

/***********************************************************************
 * <p>@description: 数据库操作类
 * <p>@author: pengl
 * <p>@created on: 2022/8/5 0005 14:08
 * <p>@version: 1.0.0
 * <p>@modify time:2022/8/5 0005 14:08
 **********************************************************************/
//@Database(
//    entities = [],
//    version = 1
//)
abstract class AppDatabase : RoomDatabase() {

//    abstract fun getSearchHistoryDao(): SearchHistoryDao

    companion object {

        private const val DATABASE_NAME = "kbt.db"

        lateinit var database: AppDatabase

        fun initDataBase(context: Context) {

            database = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                DATABASE_NAME
            )
                // .fallbackToDestructiveMigration()
                .addMigrations(

                )
                .allowMainThreadQueries()
                .build()
        }

        // 如果数据库有改动，则需要更新数据库版本来同步更新数据库，以下为示例方法：
        private val MIGRATION = object : Migration(3, 4) {
            override fun migrate(database: SupportSQLiteDatabase) {
//                database.execSQL("ALTER TABLE ${DBName.WORK_MENU} ADD COLUMN `code` TEXT")
//                database.execSQL("ALTER TABLE ${DBName.USER_INFO} ADD COLUMN `position_name` TEXT")
//                database.execSQL("ALTER TABLE ${DBName.USER_INFO} ADD COLUMN `sex` INTEGER")
//                database.execSQL("ALTER TABLE user_info ADD COLUMN `sign_path` STRING")
            }
        }
    }

}