package de.naturalsoft.popularmovies.ui.detail;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.naturalsoft.popularmovies.R;
import de.naturalsoft.popularmovies.data.database.Movie;
import de.naturalsoft.popularmovies.ui.share.MovieViewModelFactory;
import de.naturalsoft.popularmovies.utils.Constants;
import de.naturalsoft.popularmovies.utils.InjectorUtil;
import de.naturalsoft.popularmovies.utils.NetworkHelper;

public class MovieDetailActivity extends AppCompatActivity {

    MovieDetailViewModel mViewModel;

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
    @BindView(R.id.isFavorite)
    CheckBox isFavorite;


    private Movie mMovie;

    private Observer<Movie> observer = movie -> {
        if (movie != null) {
            mMovie = movie;
            loadMovieDetails(movie);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            MovieViewModelFactory factory = (MovieViewModelFactory) InjectorUtil.provideMovieViewModelFactory(this);
            mViewModel = ViewModelProviders.of(this, factory).get(MovieDetailViewModel.class);
            mViewModel.getMovieById(extras.getInt(Constants.MOVIEKEY)).observe(this, observer);
            isFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateMovie();
                }
            });
        }
    }

    /**
     * Set and load the Moviedetails into views
     *
     * @param movie the selected movie
     */
    private void loadMovieDetails(Movie movie) {
        Picasso.get()
                .load(NetworkHelper.getImageURI(movie.getPoster_path(), null))
                .placeholder(getResources().getDrawable(R.drawable.poster_not_available))
                .into(poster);

        getSupportActionBar().setTitle(movie.getTitle());

        Log.d("TAG", "Movie ID " + movie.getId() + " MOVIE IMAGEPATH " + movie.getPoster_path());
        ratingBar.setNumStars(10);
        ratingBar.setRating(Float.parseFloat(movie.getVote_average()));
        releasedateTextView.setText(movie.getRelease_date());
        String text = movie.getOverview() + movie.getOverview() + movie.getOverview() + movie.getOverview();
        plotTextView.setText(text);
    }

    private void updateMovie() {
        if (mMovie != null) {
            mMovie.setFavorite(isFavorite.isChecked());
            mViewModel.updateMovie(mMovie);
        }
    }

}
