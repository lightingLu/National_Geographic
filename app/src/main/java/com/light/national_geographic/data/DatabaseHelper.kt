package com.light.national_geographic.data

import android.content.Context
import android.database.DatabaseErrorHandler
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * 创建日期：2017/12/21 on 22:46
 * @author ludaguang
 * @version 1.0
 * 类说明：图片缓存数据库
 */
class DatabaseHelper:SQLiteOpenHelper {

    constructor(context: Context?, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int) : super(context, name, factory, version)
    constructor(context: Context?, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int, errorHandler: DatabaseErrorHandler?) : super(context, name, factory, version, errorHandler)

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_COLLECTS)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        db?.execSQL("drop table if exist collects")
        onCreate(db)
    }

    companion object {
        val CREATE_COLLECTS = "create table Collects (" +
                "uid integer primary key autoincrement, " +
                "id text," +
                "albumid text," +
                "title text," +
                "content text," +
                "url text," +
                "size text," +
                "addtime text," +
                "author text," +
                "thumb text," +
                "encoded text," +
                "weburl text," +
                "type text," +
                "yourshotlink text," +
                "copyright text," +
                "pmd5 text," +
                "sort text)"

    }

}