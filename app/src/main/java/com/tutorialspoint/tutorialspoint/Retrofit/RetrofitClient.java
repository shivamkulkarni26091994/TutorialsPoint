package com.tutorialspoint.tutorialspoint.Retrofit;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitClient {
    private static RetrofitClient mInstane;
    private Retrofit retrofit;
   // String key1= AES.encrypt(My.getContext().getString(R.string.Base_Url),"kannan");

    Gson gson = new GsonBuilder()
            .setLenient()
            .create();
    OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .build();

    private RetrofitClient()
    {
        retrofit=new Retrofit.Builder().baseUrl("https://api.themoviedb.org/").client(okHttpClient).addConverterFactory(GsonConverterFactory.create(gson)).build();
        //retrofit=new Retrofit.Builder().baseUrl(Base_Url).client(okHttpClient).addConverterFactory(GsonConverterFactory.create()).build();
    }

    public static  synchronized RetrofitClient getInstance()
    {
        if(mInstane==null)
        {
            mInstane= new RetrofitClient();
        }
        return mInstane;
    }

    public Api getApi()
    {

        return retrofit.create(Api.class);
    }


}
