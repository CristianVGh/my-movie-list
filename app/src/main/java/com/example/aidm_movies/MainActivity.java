package com.example.aidm_movies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase moviesDB;
    EditText usernameField;
    EditText passwordField;

    Button loginButton;
    Button singupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("My Movie List");

        MoviesHelper dbHelper = new MoviesHelper(this);
        moviesDB = dbHelper.getWritableDatabase();

        usernameField = findViewById(R.id.username);
        passwordField = findViewById(R.id.password);

        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkValues())
                    login();
               usernameField.setText("");
               passwordField.setText("");
            }
        });

        singupButton = findViewById(R.id.registerButton);
        singupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkValues()) {
                    addNewUser(usernameField.getText().toString(), passwordField.getText().toString());
                    usernameField.setText("");
                    passwordField.setText("");
                }
            }
        });

        }


        private boolean checkValues(){
            boolean correct = true;

            if(usernameField.getText().length() == 0) {
                correct = false;
                Toast.makeText(this, "Username field cannot be empty", Toast.LENGTH_LONG).show();
            }

            if(usernameField.getText().length() > 15) {
                correct = false;
                Toast.makeText(this, "Username must have 15 character or less", Toast.LENGTH_LONG).show();
            }

            if(passwordField.getText().length() == 0) {
                correct = false;
                Toast.makeText(this, "Password field cannot be empty", Toast.LENGTH_LONG).show();
            }

            if(passwordField.getText().length() > 12) {
                correct = false;
                Toast.makeText(this, "Password must have 12 character or less", Toast.LENGTH_LONG).show();
            }
            return correct;
        }

    private void addNewUser(String username, String password){{
        ContentValues cv = new ContentValues();
        cv.put(MoviesContract.UsersEntry.COLUMN_USERNAME, username);
        cv.put(MoviesContract.UsersEntry.COLUMN_PASSWORD, password);
        moviesDB.insert(MoviesContract.UsersEntry.TABLE_NAME, null, cv);
        Toast.makeText(this, "Registered Successfully", Toast.LENGTH_LONG).show();
    }}

    private void login(){
        String selection = MoviesContract.UsersEntry.COLUMN_USERNAME + " = ? AND " +
                MoviesContract.UsersEntry.COLUMN_PASSWORD + " = ? ";
        String[] values = {usernameField.getText().toString(), passwordField.getText().toString()};

            Cursor cursor = moviesDB.query(
                    MoviesContract.UsersEntry.TABLE_NAME,
                    null,
                    selection,
                    values,
                    null,
                    null,
                    MoviesContract.UsersEntry.COLUMN_USERNAME
            );
            if (cursor.moveToNext()){
                long userID = cursor.getLong(cursor.getColumnIndex(MoviesContract.UsersEntry._ID));
                String username = cursor.getString(cursor.getColumnIndex(MoviesContract.UsersEntry.COLUMN_USERNAME));
                String password = cursor.getString(cursor.getColumnIndex(MoviesContract.UsersEntry.COLUMN_PASSWORD));
                Log.d("Results", userID + " " + username + " " + password );

                Intent intent = new Intent(this, MovieActivity.class);
                intent.putExtra("id", userID);
                intent.putExtra("user", username);
                startActivity(intent);
            }
            else
                Toast.makeText(this, "Username or password incorrect",Toast.LENGTH_SHORT ).show();

    }
}


