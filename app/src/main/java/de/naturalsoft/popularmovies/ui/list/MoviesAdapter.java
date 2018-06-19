package de.naturalsoft.popularmovies.ui.list;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.naturalsoft.popularmovies.R;
import de.naturalsoft.popularmovies.data.database.Movie;
import de.naturalsoft.popularmovies.utils.NetworkHelper;

/**
 * PopularMovies
 * Created by Thomas Schmidt on 30.05.2018.
 */
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    private Context mContext;
    private List<Movie> mMovieList;
    private OnAdapterListener listener;

    public MoviesAdapter(Context context) {
        mContext = context;
        listener = (OnAdapterListener) context;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Drawable drawable = mContext.getResources().getDrawable(R.drawable.poster_not_available);

        Movie movie = mMovieList.get(position);
        Picasso.get().load(NetworkHelper.getImageURI(movie.getPoster_path(), mContext.getString(R.string.default_poster_size)))
                .placeholder(drawable)
                .error(drawable)
                .into(holder.poster);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.movie_list_item, parent, false);
        return new ViewHolder(view);
    }

    public interface OnAdapterListener {
        void showDetailsActivity(ImageView imageView, int movieId);
    }

    @Override
    public int getItemCount() {
        if (mMovieList == null) return 0;

        return mMovieList.size();
    }

    /**
     * Swap the given Movies
     *
     * @param movieList loaded movies
     */
    public void swapMovies(final List<Movie> movieList) {

        if (movieList != null) {
            mMovieList = movieList;
            notifyDataSetChanged();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        Context context;
        ImageView poster;

        public ViewHolder(View itemView) {
            super(itemView);
            poster = itemView.findViewById(R.id.poster);
            context = itemView.getContext();

            itemView.setOnClickListener(view ->
                    listener.showDetailsActivity(poster, ((Movie) mMovieList.get(getAdapterPosition())).getId())
            );
        }
    }
}
