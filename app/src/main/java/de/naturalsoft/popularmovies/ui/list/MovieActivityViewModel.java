package de.naturalsoft.popularmovies.ui.list;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import de.naturalsoft.popularmovies.data.DataObjects.Movie;
import de.naturalsoft.popularmovies.data.MovieRepository;

/**
 * PopularMovies
 * Created by Thomas Schmidt on 30.05.2018.
 */
public class MovieActivityViewModel extends ViewModel {

    private final MovieRepository mMovieRepository;
    private final LiveData<List<Movie>> mMovies;

    public MovieActivityViewModel(MovieRepository repository) {
        mMovieRepository = repository;
        mMovies = repository.getCurrentMovies();
    }

    public LiveData<List<Movie>> getCurrentMovies() {
        return mMovies;
    }

    public void checkSettingsHasChanged() {
        mMovieRepository.checkSettingsHasChanged();
    }
}
