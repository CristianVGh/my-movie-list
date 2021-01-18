package com.example.aidm_movies;

import android.provider.BaseColumns;

public class MoviesContract {
    public static final class UsersEntry implements BaseColumns{
        public static final String TABLE_NAME = "users";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_PASSWORD = "password";
    }

    public static final class MoviesEntry implements BaseColumns{
        public static final String TABLE_NAME = "movies";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_YEAR = "year";
        public static final String COLUMN_USER_ID = "userID";
    }

}
