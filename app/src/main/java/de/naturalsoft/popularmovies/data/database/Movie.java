package de.naturalsoft.popularmovies.data.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * PopularMovies
 * Created by Thomas Schmidt on 30.05.2018.
 */
@Entity
public class Movie {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String original_title;
    private String overview;
    private String poster_path;
    private String release_date;
    private String vote_average;
    private boolean isFavorite;
    private String type;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Movie(int id, String title, String original_title, String overview, String poster_path, String release_date, String vote_average, String type, boolean isFavorite) {
        this.id = id;
        this.title = title;
        this.original_title = original_title;
        this.overview = overview;
        this.poster_path = poster_path;
        this.release_date = release_date;
        this.vote_average = vote_average;
        this.type = type;
        this.isFavorite = isFavorite;
    }


    /**
     * For incomming movies
     *
     * @param title
     * @param original_title
     * @param overview
     * @param poster_path
     * @param release_date
     * @param vote_average
     */
    @Ignore
    public Movie(String title, String original_title, String overview, String poster_path, String release_date, String vote_average) {
        this.id = id;
        this.title = title;
        this.original_title = original_title;
        this.overview = overview;
        this.poster_path = poster_path;
        this.release_date = release_date;
        this.vote_average = vote_average;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
