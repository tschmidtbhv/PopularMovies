package de.naturalsoft.popularmovies.ui.list;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import de.naturalsoft.popularmovies.data.MovieRepository;

/**
 * PopularMovies
 * Created by Thomas Schmidt on 30.05.2018.
 */
public class MovieViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final MovieRepository mMovieRepository;

    public MovieViewModelFactory(MovieRepository repository) {
        mMovieRepository = repository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new MovieActivityViewModel(mMovieRepository);
    }
}
