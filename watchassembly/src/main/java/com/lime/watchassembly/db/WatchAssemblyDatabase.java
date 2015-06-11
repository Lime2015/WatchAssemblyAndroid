package com.lime.watchassembly.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.kakao.helper.Logger;

/**
 * Created by Administrator on 2015-06-11.
 */
public class WatchAssemblyDatabase {
    /**
     * TAG for debugging
     */
    public static final String TAG = "WatchAssemblyDatabase";

    /**
     * Singleton instance
     */
    private static WatchAssemblyDatabase database;

    /**
     * database name
     */
    public static String DATABASE_NAME = "wa.db";

    /**
     * version
     */
    public static int DATABASE_VERSION = 1;

    /**
     * Helper class defined
     */
    private DatabaseHelper dbHelper;

    /**
     * Database object
     */
    private SQLiteDatabase db;

    private Context context;

    /**
     * Singleton Constructor
     *
     * @param context
     */
    private WatchAssemblyDatabase(Context context) {
        this.context = context;
    }

    public static WatchAssemblyDatabase getInstance(Context context) {
        if (database == null) {
            database = new WatchAssemblyDatabase(context);
        }

        return database;
    }

    /**
     * open database
     *
     * @return
     */
    public boolean open() {
        Logger.getInstance().d("opening database [" + DATABASE_NAME + "].");

        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();

        return true;
    }

    /**
     * close database
     */
    public void close() {
        Logger.getInstance().d("closing database [" + DATABASE_NAME + "].");

        db.close();
        database = null;
    }

    /**
     * execute raw query using the input SQL
     * close the cursor after fetching any result;
     *
     * @param SQL
     * @return
     */
    public Cursor rawQuery(String SQL) {
        Logger.getInstance().d("nexecuteQuery called.");

        Cursor c1 = null;
        try {
            c1 = db.rawQuery(SQL, null);
            Logger.getInstance().d("cursor count : " + c1.getCount());
        } catch (Exception ex) {
            Logger.getInstance().d(TAG + ": Exception in rawQuery" + ex);
        }

        return c1;
    }

    /**
     * @param SQL
     * @return
     */
    public boolean execSQL(String SQL) {
        Logger.getInstance().d("nexecuteQuery called.");

        try {
            Logger.getInstance().d(TAG + " : SQL : " + SQL);
            db.execSQL(SQL);
        } catch (Exception ex) {
            Logger.getInstance().d(TAG + " : Exception in execSQL " + ex);
            return false;
        }

        return true;
    }


    private class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

        }

        @Override
        public void onOpen(SQLiteDatabase db) {
            super.onOpen(db);
            Logger.getInstance().d("opened database [" + DATABASE_NAME + "].");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
