package de.naturalsoft.popularmovies.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.naturalsoft.popularmovies.R;
import de.naturalsoft.popularmovies.data.DataObjects.Movie;
import de.naturalsoft.popularmovies.ui.bookmark.BookmarkActivity;
import de.naturalsoft.popularmovies.ui.bookmark.BookmarkActivityViewModel;
import de.naturalsoft.popularmovies.ui.detail.MovieDetailActivity;
import de.naturalsoft.popularmovies.ui.list.MovieActivity;
import de.naturalsoft.popularmovies.ui.list.MovieActivityViewModel;
import de.naturalsoft.popularmovies.ui.share.Listener.OnItemClickListener;
import de.naturalsoft.popularmovies.ui.share.MoviesAdapter;
import de.naturalsoft.popularmovies.utils.Constants;
import de.naturalsoft.popularmovies.utils.helper.ViewHelper;

import static de.naturalsoft.popularmovies.utils.Constants.ISTATE_RECYCLERVIEW_POSITION;

/**
 * PopularMovies
 * Created by Thomas Schmidt on 18.06.2018.
 */
public abstract class BaseActivity extends AppCompatActivity implements OnItemClickListener {

    private final static String CLASSNAME = MovieActivity.class.getSimpleName();
    private GridLayoutManager mLayoutManager;

    @BindView(R.id.moviesRecyclerView)

    RecyclerView moviesRecyclerView;
    MoviesAdapter mMoviesAdapter;
    ViewModel mViewModel;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;

    private Observer<List<Movie>> observer = movies -> {

        Log.d(CLASSNAME, "movie observe changed");
        mMoviesAdapter.swapMovies(movies);

        if (movies != null && movies.size() != 0) {
            showMovieDataView();
        } else {
            showLoading();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        ButterKnife.bind(this);

        mViewModel = getViewModel();

        setTitle(getActionBarTitle());
        setUpRecyclerView();
        loadDataView();
    }

    public abstract String getActionBarTitle();

    public abstract ViewModel getViewModel();

    /**
     * Shows loading if the given
     * LiveData Values are null
     * <p>
     * Set the observer for that LiveData
     */
    private void loadDataView() {

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            ((BookmarkActivityViewModel) mViewModel).getFavoriteMovies().observe(this, observer);
        } else {
            ((MovieActivityViewModel) mViewModel).getCurrentMovies().observe(this, observer);
        }
    }

    /**
     * Initial Setup for RecyclerView
     */
    private void setUpRecyclerView() {

        mLayoutManager = new GridLayoutManager(this, ViewHelper.calculateNoOfColumns(this));
        mMoviesAdapter = new MoviesAdapter(this);

        LayoutAnimationController animationController = AnimationUtils.loadLayoutAnimation(this, R.anim.layoutanimation_fall_down);

        moviesRecyclerView.setLayoutAnimation(animationController);
        moviesRecyclerView.setLayoutManager(mLayoutManager);
        moviesRecyclerView.setHasFixedSize(true);
        moviesRecyclerView.setAdapter(mMoviesAdapter);
    }

    private void showLoading() {

        if (getClass().isAssignableFrom(BookmarkActivity.class)) return;

        moviesRecyclerView.setVisibility(View.INVISIBLE);
        progressbar.setVisibility(View.VISIBLE);
    }

    private void showMovieDataView() {
        moviesRecyclerView.setVisibility(View.VISIBLE);
        progressbar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onItemClickedWithImage(ImageView imageView, Movie movie) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra(Constants.MOVIEKEY, movie.getId());
        intent.putExtra(Constants.MOVIESTITLE, movie.getTitle());
        startActivity(intent);
    }

    @Override
    public void onItemClicked(String key) {
        //Unused here (We just need the Clicked with Image to animate it
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(ISTATE_RECYCLERVIEW_POSITION, mLayoutManager.onSaveInstanceState());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            mLayoutManager.onRestoreInstanceState(savedInstanceState.getParcelable(ISTATE_RECYCLERVIEW_POSITION));
        }
    }
}
