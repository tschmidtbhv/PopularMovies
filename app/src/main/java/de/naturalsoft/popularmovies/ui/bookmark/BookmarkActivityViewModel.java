package de.naturalsoft.popularmovies.ui.bookmark;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import de.naturalsoft.popularmovies.data.DataObjects.Movie;
import de.naturalsoft.popularmovies.data.MovieRepository;

/**
 * PopularMovies
 * Created by Thomas Schmidt on 19.06.2018.
 */
public class BookmarkActivityViewModel extends ViewModel {

    private final MovieRepository mMovieRepository;
    private LiveData<List<Movie>> mFavoriteMovies;

    public BookmarkActivityViewModel(MovieRepository repository) {
        mMovieRepository = repository;
        mFavoriteMovies = mMovieRepository.getCurrentFavorites();
    }

    public LiveData<List<Movie>> getFavoriteMovies() {
        return mFavoriteMovies;
    }
}
