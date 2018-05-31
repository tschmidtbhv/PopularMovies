package de.naturalsoft.popularmovies.data;

import android.arch.lifecycle.LiveData;

import java.util.List;

import de.naturalsoft.popularmovies.data.network.NetworkUtil;

/**
 * PopularMovies
 * Created by Thomas Schmidt on 30.05.2018.
 */
public class MovieRepository {

    private LiveData<List<Movie>> mMovies;

    public LiveData<List<Movie>>getCurrentMovies(){

        return mMovies;
    }

    private void fetchMovieService(){
        NetworkUtil.loadMoviesForType(0);
    }
}
