package de.naturalsoft.popularmovies.ui.share;

import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * PopularMovies
 * Created by Thomas Schmidt on 20.06.2018.
 */
public abstract class BaseAdapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> {

    List<?> mList;


    /**
     * Swap the given Movies
     *
     * @param list
     */
    public void swapMovies(final List<?> list) {

        if (list != null) {
            mList = list;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        if (mList == null) return 0;

        return mList.size();
    }

    public List<?> getDataList() {
        return mList;
    }
}
