package com.example.aidm_movies;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MovieActivity extends AppCompatActivity {
    private SQLiteDatabase moviesDB;
    Button addButton;
    Button viewButton;

    long userID;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        MoviesHelper dbHelper = new MoviesHelper(this);
        moviesDB = dbHelper.getWritableDatabase();

        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                changeFragment(v);
            }
        });
        viewButton = findViewById(R.id.viewButton);
        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(v);
            }
        });

        Intent intent = getIntent();
        userID = intent.getLongExtra(("id"), -1);
        username = intent.getStringExtra("user");
        setTitle("My Movie List - " + username );

    }

    private void changeFragment(View view){
        Fragment fragment;

        if(view == findViewById(R.id.addButton)){
            fragment = new AddFragment();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.fragmentPlace, fragment);
            ft.commit();
        }

        if(view == findViewById(R.id.viewButton)){
            fragment = new ViewFragment();
            Bundle bundle = new Bundle();
            bundle.putString("user", username);
            fragment.setArguments(bundle);

            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.fragmentPlace, fragment);
            ft.commit();
        }
    }

    public void addToDB(View v){
        final String[] movieDetails = v.getTag().toString().split(";");

        new AlertDialog.Builder(this)
                .setTitle("Attention")
                .setMessage("Do you want to add this movie to your list?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        ContentValues cv = new ContentValues();
                        cv.put(MoviesContract.MoviesEntry.COLUMN_TITLE, movieDetails[0]);
                        cv.put(MoviesContract.MoviesEntry.COLUMN_YEAR, movieDetails[1]);
                        cv.put(MoviesContract.MoviesEntry.COLUMN_USER_ID, userID);
                        moviesDB.insert(MoviesContract.MoviesEntry.TABLE_NAME, null, cv);

                        Toast.makeText(MovieActivity.this, "Movie added successfully", Toast.LENGTH_SHORT).show();
                    }})
                .setNegativeButton(android.R.string.no, null).show();
    }

}
