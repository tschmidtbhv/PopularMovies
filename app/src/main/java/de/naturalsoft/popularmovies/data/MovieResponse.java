package de.naturalsoft.popularmovies.data;

import java.util.List;

/**
 * PopularMovies
 * Created by Thomas Schmidt on 31.05.2018.
 *
 * MovieResponse includes Movies
 */
public class MovieResponse {

    List<Movie> results;

    private String page;
    private String total_pages;

    public MovieResponse(String page, String total_pages, List<Movie> mMovieList) {
        this.page = page;
        this.total_pages = total_pages;
    }

    public List<Movie> getmMovieList() {
        return results;
    }

    public void setmMovieList(List<Movie> mMovieList) {
        this.results = mMovieList;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(String total_pages) {
        this.total_pages = total_pages;
    }
}
