package de.naturalsoft.popularmovies.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
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
import de.naturalsoft.popularmovies.data.DataObjects.Movie;
import de.naturalsoft.popularmovies.ui.bookmark.BookmarkActivity;
import de.naturalsoft.popularmovies.ui.bookmark.BookmarkActivityViewModel;
import de.naturalsoft.popularmovies.ui.detail.MovieDetailActivity;
import de.naturalsoft.popularmovies.ui.list.MovieActivity;
import de.naturalsoft.popularmovies.ui.list.MovieActivityViewModel;
import de.naturalsoft.popularmovies.ui.share.Listener.OnItemClickListener;
import de.naturalsoft.popularmovies.ui.share.MovieViewModelFactory;
import de.naturalsoft.popularmovies.ui.share.MoviesAdapter;
import de.naturalsoft.popularmovies.utils.Constants;
import de.naturalsoft.popularmovies.utils.DataHelper;
import de.naturalsoft.popularmovies.utils.InjectorUtil;

/**
 * PopularMovies
 * Created by Thomas Schmidt on 18.06.2018.
 */
public abstract class BaseActivity extends AppCompatActivity implements OnItemClickListener {

    private final static String CLASSNAME = MovieActivity.class.getSimpleName();
    private final static int numberOfColumns = 2;
    private static String lastSetting = "";
    @BindView(R.id.moviesRecyclerView)

    RecyclerView moviesRecyclerView;
    MoviesAdapter mMoviesAdapter;
    ViewModel mViewModel;
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
        if (getClass().isAssignableFrom(MovieActivity.class)) {
            mViewModel = ViewModelProviders.of(this, factory).get(MovieActivityViewModel.class);
        } else if (getClass().isAssignableFrom(BookmarkActivity.class)) {
            mViewModel = ViewModelProviders.of(this, factory).get(BookmarkActivityViewModel.class);
        }

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

        GridLayoutManager layoutManager = new GridLayoutManager(this, numberOfColumns);
        mMoviesAdapter = new MoviesAdapter(this);

        moviesRecyclerView.setLayoutManager(layoutManager);
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

    public <T extends ViewModel> T getViewModel() {
        return (T) mViewModel;
    }

    @Override
    public void onItemClickedWithImage(ImageView imageView, int movieId) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra(Constants.MOVIEKEY, movieId);
        startActivity(intent);
    }

    @Override
    public void onItemClicked(String key) {
        //Unused here (We just need the Clicked with Image to animate it
    }
}
