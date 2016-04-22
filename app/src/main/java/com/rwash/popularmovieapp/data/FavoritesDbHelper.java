package com.rwash.popularmovieapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.rwash.popularmovieapp.data.FavoritesContract.FavoritesMoviesEntry;

/**
 * Created by bonzo on 3/31/16.
 */
public class FavoritesDbHelper extends SQLiteOpenHelper {

    public final String LOG_TAG = FavoritesDbHelper.class.getSimpleName();

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Favorites.db";


    private static final String UNIQUE_CONSTRAIN = " UNIQUE";
    private static final String TEXT_TYPE        = " TEXT";
    private static final String INT_TYPE         = " INTEGER";
    private static final String COMMA_SEP         = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FavoritesMoviesEntry.TABLE_NAME + " (" +
                    FavoritesMoviesEntry._ID + " INTEGER PRIMARY KEY," +
                    FavoritesMoviesEntry.COLUMN_MOVIE_ID + INT_TYPE + UNIQUE_CONSTRAIN + COMMA_SEP +
                    FavoritesMoviesEntry.COLUMN_MOVIE_TITLE + TEXT_TYPE + COMMA_SEP +
                    FavoritesMoviesEntry.COLUMN_MOVIE_ORIGINAL_TITLE + TEXT_TYPE + COMMA_SEP +
                    FavoritesMoviesEntry.COLUMN_MOVIE_RELEASE_DATE + TEXT_TYPE + COMMA_SEP +
                    FavoritesMoviesEntry.COLUMN_MOVIE_OVERVIEW + TEXT_TYPE + COMMA_SEP +
                    FavoritesMoviesEntry.COLUMN_MOVIE_POSTER_URL + TEXT_TYPE +
            " )";



    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FavoritesMoviesEntry.TABLE_NAME;


    public FavoritesDbHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.v(LOG_TAG, SQL_CREATE_ENTRIES);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        onUpgrade(db, oldVersion, newVersion);
    }


}
