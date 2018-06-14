package de.naturalsoft.popularmovies.data.network;

import de.naturalsoft.popularmovies.data.database.MovieResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * PopularMovies
 * Created by Thomas Schmidt on 31.05.2018.
 */
public interface MovieClient {

    @GET("movie/{path}")
    Call<MovieResponse> getMovies(@Path("path") String path ,@Query("api_key") String key);
}
