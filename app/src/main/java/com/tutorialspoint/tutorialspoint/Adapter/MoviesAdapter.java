package com.tutorialspoint.tutorialspoint.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;
import com.squareup.picasso.Picasso;
import com.tutorialspoint.tutorialspoint.Activity.MovieDetailsActivity;
import com.tutorialspoint.tutorialspoint.Model.Movies;
import com.tutorialspoint.tutorialspoint.R;

import java.util.ArrayList;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<Movies> movies;
    String image_path="https://image.tmdb.org/t/p/w500";


    public MoviesAdapter(ArrayList<Movies> movies, Context context) {
        this.movies = movies;
        this.context = context;
    }


    @Override
    public void onBindViewHolder(@NonNull MoviesAdapter.MyViewHolder myViewHolder, final int i) {
        myViewHolder.movieName.setText(movies.get(i).getOriginal_title());

        if (movies.get(i).getPoster_path().equals("")) {
            myViewHolder.movieImageView.setImageResource(R.drawable.image);
        } else {
            Picasso.get().load(image_path+movies.get(i).getPoster_path()).into(myViewHolder.movieImageView);
        }
        myViewHolder.voteAverage.setText(String.valueOf(movies.get(i).getVote_average()));
        myViewHolder.releasedate.setText(movies.get(i).getRelease_date());
        myViewHolder.movieCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, MovieDetailsActivity.class);
                intent.putExtra("id",movies.get(i).getId());
                context.startActivity(intent);
            }
        });
    }


    @NonNull
    @Override
    public MoviesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.single_movie_layout, viewGroup, false);
        return new MyViewHolder(itemView);

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView movieImageView;
        private MaterialTextView movieName, voteAverage, releasedate;
        private CardView movieCard;

        MyViewHolder(View itemView) {
            super(itemView);
            movieImageView = itemView.findViewById(R.id.movieImageView);
            movieName = itemView.findViewById(R.id.movieName);
            voteAverage = itemView.findViewById(R.id.voteAverage);
            releasedate = itemView.findViewById(R.id.releasedate);
            movieCard = itemView.findViewById(R.id.movieCard);
        }

    }
}


