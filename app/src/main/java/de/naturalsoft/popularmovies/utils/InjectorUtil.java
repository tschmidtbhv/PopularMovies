package de.naturalsoft.popularmovies.utils;

import android.content.Context;

import de.naturalsoft.popularmovies.data.MovieRepository;
import de.naturalsoft.popularmovies.data.network.NetworkUtil;
import de.naturalsoft.popularmovies.ui.list.MovieViewModelFactory;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * PopularMovies
 * Created by Thomas Schmidt on 30.05.2018.
 * <p>
 * Provides static methods for injecting
 */

public interface InjectorUtil {


    static MovieViewModelFactory provideMovieViewModelFactory(Context context) {

        MovieRepository movieRepository = provideMovieRepository(context.getApplicationContext(), provideRetrofit());
        return new MovieViewModelFactory(movieRepository);
    }


    static MovieRepository provideMovieRepository(Context context, Retrofit retrofit) {
        NetworkUtil networkUtil = NetworkUtil.getInstance(context.getApplicationContext(), retrofit);
        return MovieRepository.getInstance(networkUtil);
    }

    static Retrofit provideRetrofit() {

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(NetworkUtil.BASEMOVIESURL)
                .addConverterFactory(GsonConverterFactory.create());

        return builder.build();
    }
}
