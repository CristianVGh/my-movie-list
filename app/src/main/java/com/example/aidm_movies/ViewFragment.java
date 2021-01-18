package com.example.aidm_movies;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ViewFragment extends Fragment {
    String username;
    private SQLiteDatabase moviesDB;
    RecyclerView recyclerView;
    ViewMovieAdapter myAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_view, container, false);
        username = getArguments().getString("user");
        recyclerView = rootView.findViewById(R.id.recycler_view_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        MoviesHelper dbHelper = new MoviesHelper(getActivity());
        moviesDB = dbHelper.getWritableDatabase();

        Cursor cursor = getAllMovies();
        myAdapter = new ViewMovieAdapter(getActivity(), cursor);
        recyclerView.setAdapter(myAdapter);


        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private Cursor getAllMovies() {
    String[] values = {username};
        Cursor cursor = moviesDB.query(
                MoviesContract.MoviesEntry.TABLE_NAME + " , " + MoviesContract.UsersEntry.TABLE_NAME,
                null,
                MoviesContract.MoviesEntry.COLUMN_USER_ID + " = " +
                        MoviesContract.UsersEntry.TABLE_NAME + "." + MoviesContract.UsersEntry._ID +
                        " AND " + MoviesContract.UsersEntry.COLUMN_USERNAME + " = ?",
                values,
                null,
                null,
                MoviesContract.MoviesEntry.COLUMN_TITLE
        );
        while (cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndex(MoviesContract.MoviesEntry._ID));
            String username = cursor.getString(cursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_TITLE));
            String password = cursor.getString(cursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_YEAR));
            String name = cursor.getString(cursor.getColumnIndex(MoviesContract.UsersEntry.COLUMN_USERNAME));
            long us = cursor.getLong(cursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_USER_ID));
            Log.d("Results3", id + " " + username + " " + password + " " + us + " " + name);

        }
        return cursor;
    }
}