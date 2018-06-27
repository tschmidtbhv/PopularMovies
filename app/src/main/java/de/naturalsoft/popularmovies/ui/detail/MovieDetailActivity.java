package de.naturalsoft.popularmovies.ui.detail;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.naturalsoft.popularmovies.R;
import de.naturalsoft.popularmovies.data.DataObjects.Movie;
import de.naturalsoft.popularmovies.data.DataObjects.ReviewResponse.Review;
import de.naturalsoft.popularmovies.data.DataObjects.TrailerResponse.Trailer;
import de.naturalsoft.popularmovies.ui.share.Listener.OnItemClickListener;
import de.naturalsoft.popularmovies.ui.share.MovieViewModelFactory;
import de.naturalsoft.popularmovies.utils.Constants;
import de.naturalsoft.popularmovies.utils.InjectorUtil;
import de.naturalsoft.popularmovies.utils.helper.NetworkHelper;

import static de.naturalsoft.popularmovies.utils.Constants.BuildConfig.SHARETYPE;

public class MovieDetailActivity extends AppCompatActivity implements OnItemClickListener {

    MovieDetailViewModel mViewModel;

    @BindView(R.id.poster)
    ImageView poster;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.releasedateTextView)
    TextView releasedateTextView;
    @BindView(R.id.plotTextView)
    TextView plotTextView;
    @BindView(R.id.isFavorite)
    CheckBox isFavorite;
    @BindView(R.id.trailerRecycler)
    RecyclerView trailerRecycler;
    @BindView(R.id.reviewRecycler)
    RecyclerView reviewRecycler;
    @BindView(R.id.reviewTV)
    TextView reviewTV;
    @BindView(R.id.vote_average)
    TextView voteAverage;

    private TrailerAdapter trailerAdapter;
    private ReviewAdapter reviewAdapter;

    private Movie mMovie;

    private Observer<Movie> movieObserver = movie -> {
        if (movie != null) {
            mMovie = movie;
            loadMovieDetails(movie);
        }
    };

    private Observer<List<Trailer>> trailerObserver = trailer -> {
        if (trailer != null) {
            if (trailer.size() > 0) {
                trailerRecycler.setVisibility(View.VISIBLE);
            }
            trailerAdapter.swapMovies(trailer);
        }
    };

    private Observer<List<Review>> reviewObserver = reviews -> {
        if (reviews != null) {

            if (reviews.size() > 0) {
                reviewTV.setVisibility(View.VISIBLE);
                reviewRecycler.setVisibility(View.VISIBLE);
            }

            reviewAdapter.swapMovies(reviews);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.share);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mMovie != null) {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.default_share_text, mMovie.getTitle(), mMovie.getVote_average()));
                    shareIntent.setType(SHARETYPE);
                    if (shareIntent.resolveActivity(getPackageManager()) != null) {
                        startActivity(shareIntent);
                    } else {
                        Toast.makeText(MovieDetailActivity.this, getString(R.string.app_not_found), Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            setTitle(extras.getString(Constants.MOVIESTITLE));

            MovieViewModelFactory factory = (MovieViewModelFactory) InjectorUtil.provideMovieViewModelFactory(this);
            mViewModel = ViewModelProviders.of(this, factory).get(MovieDetailViewModel.class);
            mViewModel.getMovieById(extras.getInt(Constants.MOVIEKEY)).observe(this, movieObserver);
            isFavorite.setOnClickListener(view -> {
                updateMovie();
            });
        }
        setUpRecyclerViews();
    }

    /**
     * Initial Setup for Trailer / Review
     * RecyclerViews
     */
    private void setUpRecyclerViews() {
        trailerAdapter = new TrailerAdapter(this);
        trailerRecycler.setAdapter(trailerAdapter);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        trailerRecycler.setLayoutManager(layoutManager);

        reviewAdapter = new ReviewAdapter();
        reviewRecycler.setLayoutManager(new LinearLayoutManager(this));
        reviewRecycler.setAdapter(reviewAdapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
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

        Log.d("TAG", " MOVIE IMAGEPATH " + movie.getId() + " MOVIE IMAGEPATH " + movie.getPoster_path());

        voteAverage.setText(getString(R.string.vote_average, movie.getVote_average()));
        releasedateTextView.setText(movie.getRelease_date());
        String text = movie.getOverview() + movie.getOverview() + movie.getOverview() + movie.getOverview();
        plotTextView.setText(text);
        isFavorite.setChecked(movie.isFavorite());

        mViewModel.getTrailerByMovieId(movie.getId()).observe(this, trailerObserver);
        mViewModel.getReviewsByMovieId(movie.getId()).observe(this, reviewObserver);
    }

    /**
     * Update the Movie prop
     * when favorite checkbox was checked/unchecked
     */
    private void updateMovie() {
        if (mMovie != null) {
            mMovie.setFavorite(isFavorite.isChecked());
            mViewModel.updateMovie(mMovie);

            createAndShowSnackBar();
        }
    }

    /**
     * Create and show up the Snackbar
     */
    private void createAndShowSnackBar() {
        int message;
        if (isFavorite.isChecked()) {
            message = R.string.added_to_favorites;
        } else {
            message = R.string.removed_from_favorites;
        }
        Snackbar.make(findViewById(R.id.coordinatLayout), message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClickedWithImage(ImageView imageView, Movie movie) {
        //We don`t need it here (Because we won*t animate anything)
    }

    /**
     * Open up Youtube with the given Trailer key
     * Youtube App / Browser is needed
     * If not an Toast will shown
     *
     * @param key trailerKey
     */
    @Override
    public void onItemClicked(String key) {

        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.youtube_uri_key) + key));

        if (appIntent.resolveActivity(getPackageManager()) != null)
            startActivity(appIntent);
        else {
            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(Constants.BuildConfig.YOUTUBE_URL + key));
            if (webIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(webIntent);
            } else {
                Toast.makeText(this, getString(R.string.app_not_found), Toast.LENGTH_SHORT).show();
            }
        }

    }
}
