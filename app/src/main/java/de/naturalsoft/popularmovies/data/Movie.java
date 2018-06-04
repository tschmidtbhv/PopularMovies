package de.naturalsoft.popularmovies.data;

/**
 * PopularMovies
 * Created by Thomas Schmidt on 30.05.2018.
 */
public class Movie {

    private String title;
    private String original_title;
    private String overview;
    private String poster_path;

    public Movie(String title, String original_title, String overview, String poster_path) {
        this.title = title;
        this.original_title = original_title;
        this.overview = overview;
        this.poster_path = poster_path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }
}
