package de.naturalsoft.popularmovies.ui.share.Listener;

import android.widget.ImageView;

import de.naturalsoft.popularmovies.data.DataObjects.Movie;

/**
 * PopularMovies
 * Created by Thomas Schmidt on 26.06.2018.
 */
public interface OnItemClickListener {
    void onItemClickedWithImage(ImageView imageView, Movie movie);

    void onItemClicked(String key);
}
