package de.naturalsoft.popularmovies.ui.list;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.naturalsoft.popularmovies.R;
import de.naturalsoft.popularmovies.utils.InjectorUtil;

public class MovieActivity extends AppCompatActivity {

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
        mViewModel = ViewModelProviders.of(this,factory).get(MovieActivityViewModel.class);

        setUpRecyclerView();
    }

    private void setUpRecyclerView() {

        GridLayoutManager layoutManager = new GridLayoutManager(this, numberOfColumns);
        mMoviesAdapter = new MoviesAdapter(this);

        moviesRecyclerView.setLayoutManager(layoutManager);
        moviesRecyclerView.setHasFixedSize(true);
        moviesRecyclerView.setAdapter(mMoviesAdapter);

        mViewModel.getmMovies().observe(this, movies -> {

            mMoviesAdapter.swapMovies(movies);
            if (mPosition == RecyclerView.NO_POSITION) mPosition = 0;
            moviesRecyclerView.smoothScrollToPosition(mPosition);
            
            if (movies != null && movies.size() != 0) showMovieDataView();
            else showLoading();
        });
    }

    private void showLoading() {
    }

    private void showMovieDataView() {
    }
}
