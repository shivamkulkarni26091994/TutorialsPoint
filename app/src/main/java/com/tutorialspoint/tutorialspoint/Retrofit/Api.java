package com.tutorialspoint.tutorialspoint.Retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {

    @GET("3/movie/popular")
    Call<ResponseBody> getMovieList(@Query("api_key") String api_key);

    @GET("3/movie/{movie_id}")
    Call<ResponseBody> getMovieDetails(@Path("movie_id") int movie_id,
                                                @Query("api_key") String api_key);

}
