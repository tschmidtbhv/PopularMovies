package de.naturalsoft.popularmovies.ui.detail;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import de.naturalsoft.popularmovies.R;
import de.naturalsoft.popularmovies.data.DataObjects.TrailerResponse.Trailer;
import de.naturalsoft.popularmovies.ui.share.BaseAdapter;
import de.naturalsoft.popularmovies.utils.Constants;

/**
 * PopularMovies
 * Created by Thomas Schmidt on 20.06.2018.
 */
public class TrailerAdapter extends BaseAdapter<TrailerAdapter.ViewHolder> {

    private Context mContext;

    public TrailerAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Drawable drawable = mContext.getResources().getDrawable(R.drawable.poster_not_available);
        Trailer trailer = ((List<Trailer>) getDataList()).get(position);
        String imgPath = Constants.BuildConfig.YOUTUBE_TUMBNAIL_URL + trailer.getKey();
        loadImage(imgPath, "", drawable, holder.trailerPoster);

        Log.d("", "imgPath: " + imgPath);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView trailerPoster;
        public ViewHolder(View itemView) {

            super(itemView);
            trailerPoster = itemView.findViewById(R.id.trailerPoster);
        }
    }
}
