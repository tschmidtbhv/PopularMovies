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
 *
 * Provides static methods for injecting
 */

public class InjectorUtil {


    public static MovieViewModelFactory provideMovieViewModelFactory(Context context) {

        MovieRepository movieRepository = provideMovieRepository(context.getApplicationContext(), provideRetrofit());
        return new MovieViewModelFactory(movieRepository);
    }


    public static MovieRepository provideMovieRepository(Context context, Retrofit retrofit){
        NetworkUtil networkUtil = NetworkUtil.getInstance(context.getApplicationContext(), retrofit);
        return MovieRepository.getInstance(networkUtil);
    }

    public static Retrofit provideRetrofit(){

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(NetworkUtil.BASEMOVIESURL)
                .addConverterFactory(GsonConverterFactory.create());

        return builder.build();
    }
}
