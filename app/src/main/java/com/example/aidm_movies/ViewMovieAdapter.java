package com.example.aidm_movies;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewMovieAdapter extends RecyclerView.Adapter<ViewMovieAdapter.MyViewHolder>{

    private Cursor cursor;
    private Context context;

    public ViewMovieAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
    }

    @Override
    public ViewMovieAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item_layout, null);
        ViewMovieAdapter.MyViewHolder vh = new ViewMovieAdapter.MyViewHolder(v);
        context = parent.getContext();

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewMovieAdapter.MyViewHolder holder, int position) {
        if(!cursor.moveToPosition(position))
            return;

            String title = cursor.getString(cursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_TITLE));
            String year = cursor.getString(cursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_YEAR));

            holder.movieTitle.setText(title);
            holder.movieYear.setText(year);
        }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView movieTitle;
        public TextView movieYear;

        public MyViewHolder(View v){
            super(v);
            movieTitle = v.findViewById(R.id.movie_title);
            movieYear = v.findViewById(R.id.movie_date);

        }
    }
}
