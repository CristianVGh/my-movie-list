package com.example.aidm_movies;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AddFragment extends Fragment {
    RecyclerView recyclerView;
    List<Movie> movies = new ArrayList<>();
    private JSONObject json;
    AddMovieAdapter myAdapter;

    EditText searchInput;
    Button searchButton;

    public AddFragment(){
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add, container, false);
        recyclerView = rootView.findViewById(R.id.recycler_view_add);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myAdapter = new AddMovieAdapter(movies);
        recyclerView.setAdapter(myAdapter);

        searchInput = rootView.findViewById(R.id.search_input);
        searchButton = rootView.findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(searchInput.getText().length()>0) {
                    new JSONTask().execute(searchInput.getText().toString());
                    Log.d("JSON", "clicked");
                }
            }
        });

        return rootView;
    }

    @Override
    public void onPause(){
        super.onPause();
    }


    private JSONObject getData(String title){
        URL url;
        HttpURLConnection urlConnection = null;
        BufferedReader reader;
        JSONObject results = new JSONObject();

        try {
            String apikey = "a64e6d74";
            url = new URL("https://www.omdbapi.com/?apikey=" + apikey + "&s=" + title.replace(" ", "+"));
            urlConnection = (HttpURLConnection) url.openConnection();

            InputStream in = urlConnection.getInputStream();
            reader = new BufferedReader((new InputStreamReader(in)));
            StringBuffer buffer = new StringBuffer();
            String line = "";

            while((line = reader.readLine()) != null){
                buffer.append(line);
            }

            results = new JSONObject((buffer.toString()));
        } catch(MalformedURLException e) {e.printStackTrace();}
        catch (IOException e) {e.printStackTrace();}
        catch (JSONException e) {e.printStackTrace();}
        finally {
            urlConnection.disconnect();
        }
        return results;
    }

    private class JSONTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            String title = params[0];
            json = getData(title);

            return json.toString();
        }

        @Override
        protected void onPostExecute(String result){
            if(json != null){

                try {
                    JSONArray jarr = json.getJSONArray("Search");

                    for(int i=0; i< jarr.length(); i++){
                        JSONObject json_movie = jarr.getJSONObject(i);
                        String title = json_movie.getString("Title");
                        String year = json_movie.getString("Year");
                        String imdbID = json_movie.getString("imdbID");
                        Movie movie = new Movie(title, year, imdbID);

                        movies.add(movie);
                    }

                    myAdapter = new AddMovieAdapter(movies);
                    recyclerView.setAdapter(myAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

    }


}
