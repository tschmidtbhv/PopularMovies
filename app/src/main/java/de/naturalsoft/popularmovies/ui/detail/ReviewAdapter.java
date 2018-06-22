package de.naturalsoft.popularmovies.ui.detail;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import de.naturalsoft.popularmovies.R;
import de.naturalsoft.popularmovies.data.DataObjects.ReviewResponse.Review;
import de.naturalsoft.popularmovies.ui.share.BaseAdapter;

/**
 * PopularMovies
 * Created by Thomas Schmidt on 20.06.2018.
 */
public class ReviewAdapter extends BaseAdapter<ReviewAdapter.ViewHolder> {


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Review review = ((List<Review>) getDataList()).get(position);
        holder.author.setText(review.getAuthor());
        holder.content.setText(review.getContent());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView author;
        TextView content;

        public ViewHolder(View itemView) {
            super(itemView);
            author = itemView.findViewById(R.id.author);
            content = itemView.findViewById(R.id.content);
        }

    }
}
