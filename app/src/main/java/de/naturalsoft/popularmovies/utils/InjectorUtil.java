package de.naturalsoft.popularmovies.utils;

import android.content.Context;

import de.naturalsoft.popularmovies.data.MovieRepository;
import de.naturalsoft.popularmovies.data.network.NetworkUtil;
import de.naturalsoft.popularmovies.ui.list.MovieViewModelFactory;

/**
 * PopularMovies
 * Created by Thomas Schmidt on 30.05.2018.
 *
 * Provides static methods for injecting
 */

public class InjectorUtil {


    public static MovieViewModelFactory provideMovieViewModelFactory(Context context) {

        //TODO REPO
        MovieRepository movieRepository = provideMovieRepository(context.getApplicationContext());
        return new MovieViewModelFactory(movieRepository);
    }


    public static MovieRepository provideMovieRepository(Context context){
        NetworkUtil networkUtil = NetworkUtil.getInstance(context.getApplicationContext());
        return MovieRepository.getInstance(networkUtil);
    }
}
