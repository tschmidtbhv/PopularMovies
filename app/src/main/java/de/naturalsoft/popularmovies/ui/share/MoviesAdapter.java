package de.naturalsoft.popularmovies.ui.share;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import de.naturalsoft.popularmovies.R;
import de.naturalsoft.popularmovies.data.DataObjects.Movie;

/**
 * PopularMovies
 * Created by Thomas Schmidt on 30.05.2018.
 */
public class MoviesAdapter extends BaseAdapter<MoviesAdapter.ViewHolder> {

    private Context mContext;
    private OnAdapterListener listener;

    public MoviesAdapter(Context context) {
        mContext = context;
        listener = (OnAdapterListener) context;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Drawable drawable = mContext.getResources().getDrawable(R.drawable.poster_not_available);

        Movie movie = ((List<Movie>) getDataList()).get(position);
        loadImage(movie.getPoster_path(), mContext.getString(R.string.default_poster_size), drawable, holder.poster);
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

    public class ViewHolder extends RecyclerView.ViewHolder {

        Context context;
        ImageView poster;

        public ViewHolder(View itemView) {
            super(itemView);
            poster = itemView.findViewById(R.id.poster);
            context = itemView.getContext();

            itemView.setOnClickListener(view ->
                    listener.showDetailsActivity(poster, ((Movie) ((List<Movie>) getDataList()).get(getAdapterPosition())).getId())
            );
        }
    }
}
