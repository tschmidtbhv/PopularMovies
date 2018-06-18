package de.naturalsoft.popularmovies.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.naturalsoft.popularmovies.R;
import de.naturalsoft.popularmovies.data.database.Movie;
import de.naturalsoft.popularmovies.ui.detail.MovieDetailActivity;
import de.naturalsoft.popularmovies.ui.list.MovieActivity;
import de.naturalsoft.popularmovies.ui.list.MovieActivityViewModel;
import de.naturalsoft.popularmovies.ui.list.MovieViewModelFactory;
import de.naturalsoft.popularmovies.ui.list.MoviesAdapter;
import de.naturalsoft.popularmovies.utils.Constants;
import de.naturalsoft.popularmovies.utils.DataHelper;
import de.naturalsoft.popularmovies.utils.InjectorUtil;

/**
 * PopularMovies
 * Created by Thomas Schmidt on 18.06.2018.
 */
public abstract class BaseActivity extends AppCompatActivity implements MoviesAdapter.OnAdapterListener {

    private final static String CLASSNAME = MovieActivity.class.getSimpleName();
    private final static int numberOfColumns = 2;
    private static String lastSetting = "";
    @BindView(R.id.moviesRecyclerView)

    RecyclerView moviesRecyclerView;
    MoviesAdapter mMoviesAdapter;
    MovieActivityViewModel mViewModel;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    private int mPosition = RecyclerView.NO_POSITION;
    private Observer<List<Movie>> observer = movies -> {

        Log.d(CLASSNAME, "movie observe changed");
        mMoviesAdapter.swapMovies(movies);
        if (mPosition == RecyclerView.NO_POSITION) mPosition = 0;
        moviesRecyclerView.smoothScrollToPosition(mPosition);

        if (movies != null && movies.size() != 0) showMovieDataView();
        else showLoading();
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        ButterKnife.bind(this);

        MovieViewModelFactory factory = (MovieViewModelFactory) InjectorUtil.provideMovieViewModelFactory(this);
        mViewModel = ViewModelProviders.of(this, factory).get(MovieActivityViewModel.class);

        setUpRecyclerView();
        loadDataView();
    }

    /**
     * Shows loading if the given
     * LiveData Values are null
     * <p>
     * Set the observer for that LiveData
     */
    private void loadDataView() {

        lastSetting = DataHelper.getSelectedType(this);

        //mViewModel.getMoviesByType(lastSetting).observe(this,observer);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mViewModel.getFavoriteMovies().observe(this, observer);
        } else {
            mViewModel.getCurrentMovies().observe(this, observer);
        }
    }

    /**
     * Initial Setup for RecyclerView
     */
    private void setUpRecyclerView() {

        GridLayoutManager layoutManager = new GridLayoutManager(this, numberOfColumns);
        mMoviesAdapter = new MoviesAdapter(this);

        moviesRecyclerView.setLayoutManager(layoutManager);
        moviesRecyclerView.setHasFixedSize(true);
        moviesRecyclerView.setAdapter(mMoviesAdapter);
    }

    private void showLoading() {
        moviesRecyclerView.setVisibility(View.INVISIBLE);
        progressbar.setVisibility(View.VISIBLE);
    }

    private void showMovieDataView() {
        moviesRecyclerView.setVisibility(View.VISIBLE);
        progressbar.setVisibility(View.INVISIBLE);
    }

    public MovieActivityViewModel getViewModel() {
        return mViewModel;
    }

    @Override
    public void showDetailsActivity(ImageView imageView, String gsonString) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra(Constants.MOVIEKEY, gsonString);
        startActivity(intent);
    }
}
