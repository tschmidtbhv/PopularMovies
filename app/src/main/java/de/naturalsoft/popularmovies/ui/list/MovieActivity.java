package de.naturalsoft.popularmovies.ui.list;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.naturalsoft.popularmovies.R;
import de.naturalsoft.popularmovies.data.database.Movie;
import de.naturalsoft.popularmovies.ui.bookmark.BookmarkActivity;
import de.naturalsoft.popularmovies.ui.detail.MovieDetailActivity;
import de.naturalsoft.popularmovies.ui.setting.SettingsActivity;
import de.naturalsoft.popularmovies.utils.Config;
import de.naturalsoft.popularmovies.utils.DataHelper;
import de.naturalsoft.popularmovies.utils.InjectorUtil;
import de.naturalsoft.popularmovies.utils.NetworkHelper;

public class MovieActivity extends AppCompatActivity implements MoviesAdapter.OnAdapterListener {

    private final static String CLASSNAME = MovieActivity.class.getSimpleName();
    private static String lastSetting = "";

    @BindView(R.id.moviesRecyclerView)

    RecyclerView moviesRecyclerView;
    MoviesAdapter mMoviesAdapter;
    MovieActivityViewModel mViewModel;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;

    private int mPosition = RecyclerView.NO_POSITION;
    private final static int numberOfColumns = 2;


    private Observer<List<Movie>> observer = movies -> {

        Log.d(CLASSNAME, "movie observe changed");
        mMoviesAdapter.swapMovies(movies);
        if (mPosition == RecyclerView.NO_POSITION) mPosition = 0;
        moviesRecyclerView.smoothScrollToPosition(mPosition);

        if (movies != null && movies.size() != 0) showMovieDataView();
        else showLoading();
    };

    private final BroadcastReceiver mNetworkStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (NetworkHelper.checkConnectivityState(MovieActivity.this) && mViewModel != null) {
                mViewModel.checkSettingsHasChanged();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        ButterKnife.bind(this);

        MovieViewModelFactory factory = InjectorUtil.provideMovieViewModelFactory(this);
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
        mViewModel.getCurrentMovies().observe(this, observer);
    }

    @Override
    protected void onResume() {
        super.onResume();

        IntentFilter networkState = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mNetworkStateReceiver, networkState);

        //mViewModel.checkSettingsHasChanged();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mNetworkStateReceiver);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent = null;
        switch (item.getItemId()) {

            case R.id.settings:
                intent = new Intent(MovieActivity.this, SettingsActivity.class);
                break;

            case R.id.bookmark:
                intent = new Intent(MovieActivity.this, BookmarkActivity.class);
                break;
        }

        if (intent != null) startActivity(intent);

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.settings_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void showLoading() {
        moviesRecyclerView.setVisibility(View.INVISIBLE);
        progressbar.setVisibility(View.VISIBLE);
    }

    private void showMovieDataView() {
        moviesRecyclerView.setVisibility(View.VISIBLE);
        progressbar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showDetailsActivity(ImageView imageView, String gsonString) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra(Config.MOVIEKEY, gsonString);
        startActivity(intent);
    }

}
