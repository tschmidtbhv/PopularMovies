package de.naturalsoft.popularmovies.data;

/**
 * PopularMovies
 * Created by Thomas Schmidt on 30.05.2018.
 */
public class Movie {

    private String page;

    public Movie(String page, String total_pages) {
        this.page = page;
        this.total_pages = total_pages;
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

    private String total_pages;


}
