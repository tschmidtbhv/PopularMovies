package de.naturalsoft.popularmovies.ui.detail;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import de.naturalsoft.popularmovies.ui.share.BaseAdapter;

/**
 * PopularMovies
 * Created by Thomas Schmidt on 20.06.2018.
 */
public class ReviewAdapter extends BaseAdapter<ReviewAdapter.ViewHolder> {


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
