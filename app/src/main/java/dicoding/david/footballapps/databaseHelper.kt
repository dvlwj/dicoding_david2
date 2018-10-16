package dicoding.david.footballapps

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

object databaseHelper {
    data class Favorite(val id: Long?, val eventID: String?) {

        companion object {
            const val TABLE_FAVORITE: String = "TABLE_FAVORITE"
            const val ID: String = "ID_"
            const val EVENT_ID: String = "EVENT_ID"
        }
    }

    class MyDatabaseOpenHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "FootBallApps.db", null, 1) {
        companion object {
            private var instance: MyDatabaseOpenHelper? = null

            @Synchronized
            fun getInstance(ctx: Context): MyDatabaseOpenHelper {
                if (instance == null) {
                    instance = MyDatabaseOpenHelper(ctx.applicationContext)
                }
                return instance as MyDatabaseOpenHelper
            }
        }

        override fun onCreate(db: SQLiteDatabase) {
            db.createTable(Favorite.TABLE_FAVORITE, true,
                    Favorite.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                    Favorite.EVENT_ID to TEXT + UNIQUE)
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            db.dropTable(Favorite.TABLE_FAVORITE, true)
        }
    }

    val Context.database: MyDatabaseOpenHelper
        get() = MyDatabaseOpenHelper.getInstance(applicationContext)
}