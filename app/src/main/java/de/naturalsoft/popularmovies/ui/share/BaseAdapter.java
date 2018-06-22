package de.naturalsoft.popularmovies.ui.share;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.naturalsoft.popularmovies.utils.NetworkHelper;

/**
 * PopularMovies
 * Created by Thomas Schmidt on 20.06.2018.
 */
public abstract class BaseAdapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> {

    private final static String CLASSTAG = BaseAdapter.class.getSimpleName();

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

        Log.d(CLASSTAG, "ItemCount " + mList.size());
        return mList.size();
    }

    public List<?> getDataList() {
        return mList;
    }

    /**
     * Load image from Web
     *
     * @param imagePath           path to image
     * @param optImageWidth       image size
     * @param placeHolderDrawable placeholder
     * @param imageView           to load into
     */
    public void loadImage(String imagePath, String optImageWidth, Drawable placeHolderDrawable, ImageView imageView) {

        Picasso.get().load(NetworkHelper.getImageURI(imagePath, optImageWidth))
                .placeholder(placeHolderDrawable)
                .error(placeHolderDrawable)
                .into(imageView);
    }
}
