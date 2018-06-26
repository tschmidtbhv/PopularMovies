package de.naturalsoft.popularmovies.ui.share.Listener;

import android.widget.ImageView;

/**
 * PopularMovies
 * Created by Thomas Schmidt on 26.06.2018.
 */
public interface OnItemClickListener {
    void onItemClickedWithImage(ImageView imageView, int movieId);

    void onItemClicked(String key);
}
