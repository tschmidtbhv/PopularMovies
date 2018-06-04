package de.naturalsoft.popularmovies.ui.list;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.naturalsoft.popularmovies.R;
import de.naturalsoft.popularmovies.ui.setting.SettingsActivity;
import de.naturalsoft.popularmovies.utils.InjectorUtil;

public class MovieActivity extends AppCompatActivity {

    private final static String CLASSNAME = MovieActivity.class.getSimpleName();

    @BindView(R.id.moviesRecyclerView)

    RecyclerView moviesRecyclerView;
    MoviesAdapter mMoviesAdapter;
    MovieActivityViewModel mViewModel;

    private int mPosition = RecyclerView.NO_POSITION;
    private final static int numberOfColumns = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        ButterKnife.bind(this);

        MovieViewModelFactory factory = InjectorUtil.provideMovieViewModelFactory(this);
        mViewModel = ViewModelProviders.of(this, factory).get(MovieActivityViewModel.class);

        setUpRecyclerView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mViewModel.checkSettingsHasChanged();
    }

    private void setUpRecyclerView() {

        GridLayoutManager layoutManager = new GridLayoutManager(this, numberOfColumns);
        mMoviesAdapter = new MoviesAdapter(this);

        moviesRecyclerView.setLayoutManager(layoutManager);
        moviesRecyclerView.setHasFixedSize(true);
        moviesRecyclerView.setAdapter(mMoviesAdapter);

        mViewModel.getmMovies().observe(this, movies -> {
            Log.d(CLASSNAME, "movie observe changed");
            mMoviesAdapter.swapMovies(movies);
            if (mPosition == RecyclerView.NO_POSITION) mPosition = 0;
            moviesRecyclerView.smoothScrollToPosition(mPosition);

            if (movies != null && movies.size() != 0) showMovieDataView();
            else showLoading();
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.settings) {
            Intent intent = new Intent(MovieActivity.this, SettingsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.settings_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void showLoading() {
    }

    private void showMovieDataView() {
    }
}
