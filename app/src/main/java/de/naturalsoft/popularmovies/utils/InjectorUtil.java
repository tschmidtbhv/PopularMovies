package de.naturalsoft.popularmovies.utils;

import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;

import de.naturalsoft.popularmovies.data.MovieRepository;
import de.naturalsoft.popularmovies.data.database.MovieDatabase;
import de.naturalsoft.popularmovies.data.network.NetworkDataSource;
import de.naturalsoft.popularmovies.ui.share.MovieViewModelFactory;
import de.naturalsoft.popularmovies.utils.Constants.BuildConfig;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * PopularMovies
 * Created by Thomas Schmidt on 30.05.2018.
 * <p>
 * Provides static methods for injecting
 */

public interface InjectorUtil {


    static <T> ViewModelProvider.NewInstanceFactory provideMovieViewModelFactory(Context context) {

        MovieRepository movieRepository = provideMovieRepository(context.getApplicationContext(), provideRetrofit());
        return new MovieViewModelFactory(movieRepository);
    }


    static MovieRepository provideMovieRepository(Context context, Retrofit retrofit) {

        MovieDatabase database = MovieDatabase.getInstance(context.getApplicationContext());
        NetworkDataSource networkUtil = NetworkDataSource.getInstance(context.getApplicationContext(), retrofit);
        return MovieRepository.getInstance(database.movieDao(), networkUtil);
    }

    static Retrofit provideRetrofit() {

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());

        return builder.build();
    }
}
