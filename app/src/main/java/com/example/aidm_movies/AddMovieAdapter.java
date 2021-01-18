package com.example.aidm_movies;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AddMovieAdapter extends RecyclerView.Adapter<AddMovieAdapter.MyViewHolder> {
    private List<Movie> movies;
    private Context context;

    public AddMovieAdapter(List<Movie> mv) {
        movies = mv;
    }

    @Override
    public AddMovieAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_item_layout, null);
        MyViewHolder vh = new MyViewHolder(v);
        context = parent.getContext();

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull AddMovieAdapter.MyViewHolder holder, int position) {
        String title = movies.get(position).getTitle();
        String year = movies.get(position).getYear();
        final String imdbID = movies.get(position).getImdbID();
        holder.movieTitle.setText(title);
        holder.movieYear.setText(year);
        holder.button.setTag(title + ";" + year);

        holder.movieView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://www.imdb.com/title/" + imdbID);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                context.startActivity(intent);
            }
        });
        Log.d("JSON", "Added " + title);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView movieTitle;
        public TextView movieYear;
        public View movieView;
        public Button button;

        public MyViewHolder(View v){
            super(v);
            movieTitle = v.findViewById(R.id.movie_name);
            movieYear = v.findViewById(R.id.movie_year);
            button = v.findViewById(R.id.arrow_button);

            movieView = v;
        }
    }

}