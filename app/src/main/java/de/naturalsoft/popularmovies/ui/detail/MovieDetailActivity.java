package de.naturalsoft.popularmovies.ui.detail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.naturalsoft.popularmovies.R;
import de.naturalsoft.popularmovies.data.Movie;
import de.naturalsoft.popularmovies.utils.Config;
import de.naturalsoft.popularmovies.utils.NetworkHelper;

public class MovieDetailActivity extends AppCompatActivity {

    @BindView(R.id.poster)
    ImageView poster;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ratingBar)
    RatingBar ratingBar;
    @BindView(R.id.releasedateTextView)
    TextView releasedateTextView;
    @BindView(R.id.plotTextView)
    TextView plotTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Movie movie = getMovie(extras);
            loadMovieDetails(movie);
        }
    }

    private void loadMovieDetails(Movie movie) {
        Picasso.get()
                .load(NetworkHelper.getImageURI(movie.getPoster_path()))
                .placeholder(getResources().getDrawable(R.drawable.poster_not_available))
                .into(poster);

        getSupportActionBar().setTitle(movie.getTitle());

        Log.d("TAG", "Movie ID " + movie.getId() + " MOVIE IMAGEPATH " + movie.getPoster_path());
        ratingBar.setNumStars(10);
        ratingBar.setRating(Float.parseFloat(movie.getVote_average()));
        releasedateTextView.setText(movie.getRelease_date());
        String text = movie.getOverview() + movie.getOverview() + movie.getOverview()+ movie.getOverview();
        plotTextView.setText(text);
    }

    private Movie getMovie(Bundle extras) {
        Gson gson = new Gson();
        String jsonString = extras.getString(Config.MOVIEKEY);
        return gson.fromJson(jsonString, Movie.class);
    }

}
