package de.naturalsoft.popularmovies.ui.detail;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import de.naturalsoft.popularmovies.data.DataObjects.Movie;
import de.naturalsoft.popularmovies.data.DataObjects.ReviewResponse;
import de.naturalsoft.popularmovies.data.DataObjects.TrailerResponse.Trailer;
import de.naturalsoft.popularmovies.data.MovieRepository;

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

    public LiveData<List<Trailer>> getTrailerByMovieId(int id) {
        return movieRepository.getTrailerByMovieId(id);
    }

    public LiveData<List<ReviewResponse.Review>> getReviewsByMovieId(int id) {
        return movieRepository.getReviewsByMovieId(id);
    }
}
