package de.naturalsoft.popularmovies.ui.list;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import de.naturalsoft.popularmovies.R;
import de.naturalsoft.popularmovies.data.Movie;

/**
 * PopularMovies
 * Created by Thomas Schmidt on 30.05.2018.
 */
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder>{

    private Context mContext;
    private List<Movie> mMovieList;

    public MoviesAdapter(Context context){
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.movie_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(String.valueOf(position));
        //bind(holder, mMovieList.get(position));
    }

    private void bind(ViewHolder holder, Movie currentMovie) {
        //TODO bind related fields
       // holder.title.setText(currentMovie.);
    }

    @Override
    public int getItemCount() {
        if(mMovieList == null)return 0;

        return mMovieList.size();
    }

    /**
     * Swap the given Movies
     * @param movieList loaded movies
     */
    public void swapMovies(final List<Movie> movieList){

        if(movieList != null){
            mMovieList = movieList;
            notifyDataSetChanged();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView title;

        public ViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
        }
    }
}
