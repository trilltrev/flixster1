package adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Movie;
import android.os.Parcel;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixster.DetailActivity;
import com.example.flixster.R;

import org.parceler.Parcels;

import java.util.List;

import models.Movies;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    Context context;
    List<Movies> movies;

    public MovieAdapter(Context context, List<Movies> movie) {
        this.context = context;
        this.movies = movie;
    }

    //usually involves inflating a layout from XML and returning the holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter", "onCreateViewHolder");
        final View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(movieView);
    }

    // involves populating data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("MovieAdapter", "onBindViewHolder " + position);
        // Get the movie at passed position
        final Movies movie = movies.get(position);

        // bind the movie data into VH
        holder.bind(movie);
    }

    // returns total count of item into te list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        RelativeLayout container;
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            container = itemView.findViewById(R.id.container);
        }

               //tada
        public void bind(final Movies movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            String imageUrl;
            //if phone is in landscape
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                //then imageUrl = backdrop image
                imageUrl = movie.getBackdropPath();
            } else{
                //else imageUrl = poster image
                imageUrl = movie.getPosterPath();
            }

            Glide.with(context).load(imageUrl).into(ivPoster);
            //1.register click listener on the whole row
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //2.navigate to a new activity on tap
                    Intent i = new Intent(context, DetailActivity.class);
                    i.putExtra("movie", Parcels.wrap(movie));
                    context.startActivity(i);
                }
            });
        }
    }
}

