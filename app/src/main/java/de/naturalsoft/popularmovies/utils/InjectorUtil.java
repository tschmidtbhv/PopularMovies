package de.naturalsoft.popularmovies.utils;

import android.content.Context;

import de.naturalsoft.popularmovies.data.MovieRepository;
import de.naturalsoft.popularmovies.ui.list.MovieViewModelFactory;

/**
 * PopularMovies
 * Created by Thomas Schmidt on 30.05.2018.
 *
 * Provides static methods for injecting
 */

public class InjectorUtil {


    public static MovieViewModelFactory provideMovieViewModelFactory(Context applicationContext) {

        //TODO REPO
        return new MovieViewModelFactory(new MovieRepository());
    }
}
