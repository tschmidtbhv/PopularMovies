package de.naturalsoft.popularmovies.data.network;

import de.naturalsoft.popularmovies.data.MovieResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * PopularMovies
 * Created by Thomas Schmidt on 31.05.2018.
 */
public interface MovieClient {

    @GET("movie/popular")
    Call<MovieResponse> getPopularMovies(@Query("api_key") String key);

    @GET("/movie/top_rated")
    Call<MovieResponse> getTopRatedMovies(@Query("api_key") String key);
}
