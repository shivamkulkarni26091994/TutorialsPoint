package com.tutorialspoint.tutorialspoint.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.google.android.material.textview.MaterialTextView;
import com.squareup.picasso.Picasso;
import com.tutorialspoint.tutorialspoint.Adapter.MoviesAdapter;
import com.tutorialspoint.tutorialspoint.Model.Movies;
import com.tutorialspoint.tutorialspoint.R;
import com.tutorialspoint.tutorialspoint.Retrofit.RetrofitClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailsActivity extends AppCompatActivity {
private int id;
private ImageView movieImageViewDetails;
private MaterialTextView movieNameDetails,movieRelaseDateDetails,movieLang,overview;
private JSONObject jsonObject;
private JSONArray jsonArray;
    String image_path="https://image.tmdb.org/t/p/w500";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        movieImageViewDetails=findViewById(R.id.movieImageViewDetails);
        movieNameDetails=findViewById(R.id.movieNameDetails);
        movieRelaseDateDetails=findViewById(R.id.movieRelaseDateDetails);
        movieLang=findViewById(R.id.movieLang);
        overview=findViewById(R.id.overview);

    id=getIntent().getIntExtra("id",0);
    System.out.println(id);
    getMovieDetails("08f9c8323601bc289de99524bd6be202",id);
    }

    private void getMovieDetails(String s, int id) {
        Call<ResponseBody> responseBodyCall = RetrofitClient.getInstance().getApi().getMovieDetails(id,s);
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {

                        try {
                            String responseString = response.body().string();
                            jsonObject=new JSONObject(responseString);
                            if (jsonObject.getString("poster_path").equals("")) {
                                movieImageViewDetails.setImageResource(R.drawable.image);
                            } else {
                                Picasso.get().load(image_path+jsonObject.getString("poster_path")).into(movieImageViewDetails);
                            }
                            movieNameDetails.setText(jsonObject.getString("title"));
                            movieRelaseDateDetails.setText(jsonObject.getString("release_date"));
                            overview.setText(jsonObject.getString("overview"));
                            jsonArray=jsonObject.getJSONArray("spoken_languages");
                            for (int i = 0; i < jsonArray.length(); i++) {

                                jsonObject = jsonArray.getJSONObject(i);
                                movieLang.setText(jsonObject.getString("name"));
                            }
                        } catch (IOException | JSONException e) {
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