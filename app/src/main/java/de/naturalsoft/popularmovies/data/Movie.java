package de.naturalsoft.popularmovies.data;

/**
 * PopularMovies
 * Created by Thomas Schmidt on 30.05.2018.
 */
public class Movie {

    private String title;
    private String original_title;
    private String overview;

    public Movie(String title, String original_title, String overview) {
        this.title = title;
        this.original_title = original_title;
        this.overview = overview;
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
}
