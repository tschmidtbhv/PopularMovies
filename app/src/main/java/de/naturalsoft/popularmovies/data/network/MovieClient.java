package de.naturalsoft.popularmovies.data.network;

import de.naturalsoft.popularmovies.data.DataObjects.Review;
import de.naturalsoft.popularmovies.data.DataObjects.TrailerResponse;
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

    //http://api.themoviedb.org/3/movie/12/videos?api_key=
    //https://img.youtube.com/vi/-aAHfOQ7Rbo/0.jpg (key)

    @GET("movie/{path}")
    Call<MovieResponse> getMovies(@Path("path") String path, @Query("api_key") String key);

    @GET("movie/{id}/videos")
    Call<TrailerResponse> getTrailerByMovieId(@Path("id") int id, @Query("api_key") String key);

    @GET("movie/{id}/reviews")
    Call<Review> getReviewsByMovieId(int id, @Query("api_key") String key);
}
