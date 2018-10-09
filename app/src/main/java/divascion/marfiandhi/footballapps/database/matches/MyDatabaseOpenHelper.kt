package divascion.marfiandhi.footballapps.database.matches

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import divascion.marfiandhi.footballapps.model.matches.Favorite
import org.jetbrains.anko.db.*

/**
 * Created by Marfiandhi on 10/7/2018.
 */
class MyDatabaseOpenHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "FavoriteEvent.db", null, 1) {
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
                Favorite.EVENT_ID to TEXT + UNIQUE,
                Favorite.HOME_ID to TEXT,
                Favorite.HOME_NAME to TEXT,
                Favorite.HOME_SCORE to TEXT,
                Favorite.AWAY_ID to TEXT,
                Favorite.AWAY_NAME to TEXT,
                Favorite.AWAY_SCORE to TEXT,
                Favorite.DATE to TEXT,
                Favorite.HOME_GOAL_DETAIL to TEXT,
                Favorite.AWAY_GOAL_DETAIL to TEXT,
                Favorite.HOME_SHOT to TEXT,
                Favorite.AWAY_SHOT to TEXT,
                Favorite.HOME_GOAL_KEEPER to TEXT,
                Favorite.HOME_DEFENSE to TEXT,
                Favorite.HOME_MIDFIELD to TEXT,
                Favorite.HOME_FORWARD to TEXT,
                Favorite.HOME_SUBSTITUTES to TEXT,
                Favorite.AWAY_GOAL_KEEPER to TEXT,
                Favorite.AWAY_DEFENSE to TEXT,
                Favorite.AWAY_MIDFIELD to TEXT,
                Favorite.AWAY_FORWARD to TEXT,
                Favorite.AWAY_SUBSTITUTES to TEXT,
                Favorite.TIME to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable("TABLE_FAVORITE", true)
    }
}

val Context.database: MyDatabaseOpenHelper
    get() = MyDatabaseOpenHelper.getInstance(applicationContext)