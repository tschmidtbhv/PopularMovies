package de.naturalsoft.popularmovies.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.util.List;

/**
 * PopularMovies
 * Created by Thomas Schmidt on 30.05.2018.
 */
public class MovieRepository {

    public LiveData<List<Movie>>getCurrentMovies(){
        //TODO fill logic

        LiveData<List<Movie>> movies = new MutableLiveData<>();
        return movies;
    }
}
