package com.rbmstroy.rbmbonus.data.sqlite

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.google.gson.Gson
import com.rbmstroy.rbmbonus.model.Notifications

class DatabaseHandler(context: Context, factory: SQLiteDatabase.CursorFactory?) : SQLiteOpenHelper(context, DB_NAME, factory, DB_VERSION) {

    companion object {
        private val DB_NAME = "stroyBack.db"
        private val DB_VERSION = 1
        private val gson = Gson()
    }

    override fun onCreate(db: SQLiteDatabase?) {
        println("DatabaseHandler: onCreate table")
        val CREATE_NOTIFICATIONS_TABLE = "CREATE TABLE NOTIFICATIONS(ID INTEGER PRIMARY KEY, TYPE TEXT, TITLE TEXT, MESSAGE TEXT, DATE TEXT, ISREAD INTEGER)"
        db?.execSQL(CREATE_NOTIFICATIONS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        println("DatabaseHandler: onUpgrade table")
        db?.execSQL("DROP TABLE IF EXISTS NOTIFICATIONS")
        onCreate(db)
    }

    fun addNotifications(notification: Notifications): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("ID", notification.id)
        values.put("TYPE", notification.type)
        values.put("TITLE", notification.title)
        values.put("MESSAGE", notification.message)
        values.put("DATE", notification.datetime)
        values.put("ISREAD", notification.isRead)
        val _success = db.insert("NOTIFICATIONS", null, values)
        db.close()
        return (Integer.parseInt("$_success") != -1)
    }

    fun deleteNotifications(id: Int): Boolean {
        val db = this.writableDatabase
        val _success = db.delete("NOTIFICATIONS", "ID=$id", null)
        db.close()
        return (Integer.parseInt("$_success") != -1)
    }

    fun updateNotifications(id: Int): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("ISREAD", 1)
        val _success = db.update("NOTIFICATIONS", values, "ID=$id", null)
        db.close()
        return (Integer.parseInt("$_success") != -1)
    }

    @SuppressLint("Range")
    fun getNotifications(): ArrayList<Notifications> {
        val notifications: ArrayList<Notifications> = ArrayList()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM NOTIFICATIONS ORDER BY DATE DESC", null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    val id = cursor.getInt(cursor.getColumnIndex("ID"))
                    val type = cursor.getString(cursor.getColumnIndex("TYPE"))
                    val title = cursor.getString(cursor.getColumnIndex("TITLE"))
                    val message = cursor.getString(cursor.getColumnIndex("MESSAGE"))
                    val date = cursor.getString(cursor.getColumnIndex("DATE"))
                    val isRead = cursor.getInt(cursor.getColumnIndex("ISREAD"))
                    println("DatabaseHandler: Notifications - id=${id}, type=${type}, title=${title}, message=${message}, date=${date}, isRead=${isRead}")
                    notifications.add(Notifications(id, type, title, message, date, isRead))
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        db.close()
        return notifications
    }

    fun deleteTable(table: String): Boolean {
        val db = this.writableDatabase
        val _success = db.delete("$table", null, null)
        db.close()
        return (Integer.parseInt("$_success") != -1)
    }

}