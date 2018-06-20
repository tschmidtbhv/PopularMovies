package de.naturalsoft.popularmovies.ui.detail;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import de.naturalsoft.popularmovies.data.MovieRepository;
import de.naturalsoft.popularmovies.data.database.Movie;

/**
 * PopularMovies
 * Created by Thomas Schmidt on 19.06.2018.
 */
public class MovieDetailViewModel extends ViewModel {

    private MovieRepository movieRepository;

    public MovieDetailViewModel(MovieRepository repository) {
        movieRepository = repository;
    }

    public LiveData<Movie> getMovieById(int id) {
        return movieRepository.getMovieById(id);
    }

    public void updateMovie(Movie movie) {
        movieRepository.updateMoview(movie);
    }

    public void loadTrailerById(int id) {
        movieRepository.loadTrailerById(id);
    }
}
