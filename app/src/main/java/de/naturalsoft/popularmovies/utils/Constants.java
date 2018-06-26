package de.naturalsoft.popularmovies.utils;

/**
 * PopularMovies
 * Created by Thomas Schmidt on 01.06.2018.
 */
public interface Constants {

    int MOVIESSTDID = -1;
    String MOVIEKEY = "MOVIEKEY";
    String MOVIESTITLE = "MOVIESTITLE";
    String ISBOOKMARK = "ISBOOKMARK";
    String TRAILERTYPE = "TRAILER";
    String REVIEWTYPE = "REVIEW";

    interface BuildConfig {

        String API_KEY = ""; //TODO you need to set the API Key here
        String BASE_URL = "https://api.themoviedb.org/3/";
        String BASEIMAGEURL = "https://image.tmdb.org/t/p/";
        String YOUTUBE_TUMBNAIL_URL = "http://img.youtube.com/vi/";
        String YOUTUBE_URL = "http://www.youtube.com/watch?v=";
        String SHARETYPE = "text/plain";
    }

}
