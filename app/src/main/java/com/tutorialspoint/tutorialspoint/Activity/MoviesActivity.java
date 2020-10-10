package com.tutorialspoint.tutorialspoint.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.tutorialspoint.tutorialspoint.Adapter.MoviesAdapter;
import com.tutorialspoint.tutorialspoint.Model.Movies;
import com.tutorialspoint.tutorialspoint.R;
import com.tutorialspoint.tutorialspoint.Retrofit.RetrofitClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesActivity extends AppCompatActivity {
private JSONObject jsonObject;
private JSONArray jsonArray;
private Movies movies;
    private RecyclerView moviesRecyclerView;
    private MoviesAdapter adapter;
    private ArrayList<Movies> moviesModels;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        moviesModels = new ArrayList<>();
        moviesRecyclerView =findViewById(R.id.moviesRecyclerView);
        moviesRecyclerView.setLayoutManager(new LinearLayoutManager(MoviesActivity.this, RecyclerView.VERTICAL, false));
        moviesRecyclerView.setHasFixedSize(true);
        getMovies();
    }

    private void getMovies() {
        Call<ResponseBody> responseBodyCall = RetrofitClient.getInstance().getApi().getMovieList("08f9c8323601bc289de99524bd6be202");
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {

                        try {
                            String responseString = response.body().string();
                           jsonObject=new JSONObject(responseString);
                            jsonArray =jsonObject.getJSONArray("results");
                             for (int i = 0; i < jsonArray.length(); i++) {
                                movies = new Movies();

                                jsonObject = jsonArray.getJSONObject(i);
                                char fisrt =jsonObject.getString("title").charAt(0);
                                if(fisrt=='E' || fisrt=='e' ){

                                }
                                else {


                                    movies.setId(jsonObject.getInt("id"));
                                    movies.setOriginal_title(jsonObject.getString("title"));
                                    movies.setPoster_path(jsonObject.getString("poster_path"));
                                    movies.setRelease_date(jsonObject.getString("release_date"));
                                    movies.setVote_average(jsonObject.getDouble("vote_average"));
                                    moviesModels.add(movies);
                                }
                                }
                            adapter = new MoviesAdapter(moviesModels, MoviesActivity.this);
                            moviesRecyclerView.setAdapter(adapter);
                        } catch (IOException | JSONException e ) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
System.out.println(t.getMessage());
            }
        });

    }
}