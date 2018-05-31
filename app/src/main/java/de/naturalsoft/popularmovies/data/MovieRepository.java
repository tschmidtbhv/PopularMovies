package de.naturalsoft.popularmovies.data;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import java.util.List;

import de.naturalsoft.popularmovies.data.network.NetworkUtil;

/**
 * PopularMovies
 * Created by Thomas Schmidt on 30.05.2018.
 */
public class MovieRepository {

    private final static String CLASSTAG = MovieRepository.class.getSimpleName();

    private static  MovieRepository sINSTANCE;

    private LiveData<List<Movie>> mMovies;

    private MovieRepository(NetworkUtil networkUtil){
        mMovies = networkUtil.getCurrentMovies();
    }

    public static MovieRepository getInstance(NetworkUtil networkUtil){
        if(sINSTANCE == null){
            Log.d(CLASSTAG, "New MovieRepository");
            sINSTANCE = new MovieRepository(networkUtil);
        }

        return sINSTANCE;
    }

    public LiveData<List<Movie>>getCurrentMovies(){

        return mMovies;
    }

}
