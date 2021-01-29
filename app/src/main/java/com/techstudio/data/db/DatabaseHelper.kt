package com.techstudio.data.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.techstudio.model.Article
import com.techstudio.model.Media
import timber.log.Timber
import java.util.*

private const val DATABASE_VERSION = 1
private const val DATABASE_NAME = "tech_studio.db"

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION), ArticleStorage {

    override fun onCreate(db: SQLiteDatabase) {
        try {
            create(db)
        } catch (e: SQLException) {
            Timber.e(e)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        try {
            drop(db)
            onCreate(db)
        } catch (e: SQLException) {
            Timber.e(e)
        }
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    override fun create(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE " + ArticleStorage.TABLE_NAME +
                    "(" + ArticleStorage.COLUMN_ID + " INTEGER PRIMARY KEY, " +
                    ArticleStorage.COLUMN_TITLE + " TEXT, " +
                    ArticleStorage.COLUMN_INFO + " TEXT, " +
                    ArticleStorage.COLUMN_MEDIA + " TEXT, " +
                    ArticleStorage.COLUMN_SOURCE + " TEXT, " +
                    ArticleStorage.COLUMN_UPDATE_AT + " LONG)"

        )
    }

    override fun drop(db: SQLiteDatabase) {
        db.execSQL("DROP TABLE IF EXISTS ${ArticleStorage.TABLE_NAME}")
    }

    override fun getArticle(id: Long): Article? {
        val db = this.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM ${ArticleStorage.TABLE_NAME} WHERE ${ArticleStorage.COLUMN_ID}=${id}",
            null
        )
        val response = if (cursor.moveToFirst()) {
            getArticleFromCursor(cursor).apply {
                isFavourite = true
            }
        } else {
            null
        }
        cursor.close()
        return response
    }

    override fun insertArticle(article: Article): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(ArticleStorage.COLUMN_ID, article.id)
        contentValues.put(ArticleStorage.COLUMN_TITLE, article.title)
        contentValues.put(ArticleStorage.COLUMN_INFO, article.info)
        contentValues.put(ArticleStorage.COLUMN_SOURCE, article.source)
        contentValues.put(ArticleStorage.COLUMN_MEDIA, Gson().toJson(article.media))
        contentValues.put(ArticleStorage.COLUMN_UPDATE_AT, article.updated.timeInMillis)
        db.insert(ArticleStorage.TABLE_NAME, null, contentValues)
        return true
    }

    override fun deleteArticle(id: Long): Int {
        val db = this.writableDatabase
        return db.delete(
            ArticleStorage.TABLE_NAME,
            "${ArticleStorage.COLUMN_ID} = ? ",
            arrayOf((id).toString())
        )
    }

    override fun getAllArticles(): List<Article> {
        val articles = mutableListOf<Article>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM ${ArticleStorage.TABLE_NAME}", null)
        while (cursor.moveToNext()) {
            articles.add(getArticleFromCursor(cursor))
        }
        cursor.close()
        return articles
    }

    private fun getArticleFromCursor(cursor: Cursor): Article {
        val idColumnIndex = cursor.getColumnIndex(ArticleStorage.COLUMN_ID)
        val titleColumnIndex = cursor.getColumnIndex(ArticleStorage.COLUMN_TITLE)
        val infoColumnIndex = cursor.getColumnIndex(ArticleStorage.COLUMN_INFO)
        val mediaColumnIndex = cursor.getColumnIndex(ArticleStorage.COLUMN_MEDIA)
        val sourceColumnIndex = cursor.getColumnIndex(ArticleStorage.COLUMN_SOURCE)
        val updatedAtColumnIndex = cursor.getColumnIndex(ArticleStorage.COLUMN_UPDATE_AT)

        val id = cursor.getLong(idColumnIndex)
        val title = cursor.getString(titleColumnIndex)
        val info = cursor.getString(infoColumnIndex)
        val mediaJson = cursor.getString(mediaColumnIndex)
        val source = cursor.getString(sourceColumnIndex)
        val updatedAtMillis = cursor.getLong(updatedAtColumnIndex)

        return Article(
            id = id,
            title = title,
            info = info,
            source = source,
            updated = Calendar.getInstance().apply {
                timeInMillis = updatedAtMillis
            },
            media = Gson().fromJson(mediaJson, object : TypeToken<List<Media>>() {}.type)
        )
    }
}

interface ArticleStorage {
    companion object {
        const val TABLE_NAME = "article"
        const val COLUMN_ID = "id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_INFO = "info"
        const val COLUMN_SOURCE = "source"
        const val COLUMN_MEDIA = "media"
        const val COLUMN_UPDATE_AT = "updated_at"
    }

    fun create(db: SQLiteDatabase)
    fun drop(db: SQLiteDatabase)

    fun getArticle(id: Long): Article?
    fun insertArticle(article: Article): Boolean
    fun deleteArticle(id: Long): Int
    fun getAllArticles(): List<Article>
}