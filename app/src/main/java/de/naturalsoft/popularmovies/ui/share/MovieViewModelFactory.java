package de.naturalsoft.popularmovies.ui.share;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import de.naturalsoft.popularmovies.data.MovieRepository;
import de.naturalsoft.popularmovies.ui.bookmark.BookmarkActivityViewModel;
import de.naturalsoft.popularmovies.ui.detail.MovieDetailActivity;
import de.naturalsoft.popularmovies.ui.detail.MovieDetailViewModel;
import de.naturalsoft.popularmovies.ui.list.MovieActivityViewModel;

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

        if (modelClass.isAssignableFrom(MovieActivityViewModel.class)) {
            return (T) new MovieActivityViewModel(mMovieRepository);
        } else if (modelClass.isAssignableFrom(MovieDetailViewModel.class)) {
            return (T) new MovieDetailViewModel(mMovieRepository);
        } else if (modelClass.isAssignableFrom(BookmarkActivityViewModel.class)) {
            return (T) new BookmarkActivityViewModel(mMovieRepository);
        } else {
            throw new IllegalArgumentException("Class can`t create");
        }
    }
}
