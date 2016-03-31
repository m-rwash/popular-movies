package com.rwash.popularmovieapp.data;

import android.provider.BaseColumns;

/**
 * Created by bonzo on 3/31/16.
 */
public final class FavoritesContract
{
    public FavoritesContract() {
    }

    public static abstract class FavoritesMoviesEntry implements BaseColumns
    {
        public static final String TABLE_NAME                  = "favorites";
        public static final String COLUMN_MOVIE_ID             = "movieid";
        public static final String COLUMN_MOVIE_TITLE          = "movietitle";
        public static final String COLUMN_MOVIE_ORIGINAL_TITLE = "movieoriginaltitle";
        public static final String COLUMN_MOVIE_RELEASE_DATE   = "moviereleasedate";
        public static final String COLUMN_MOVIE_OVERVIEW       = "movieoverview";
        public static final String COLUMN_MOVIE_POSTER_URL     = "movieposterurl";
    }
}
