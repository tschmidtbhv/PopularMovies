package de.naturalsoft.popularmovies.data.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * PopularMovies
 * Created by Thomas Schmidt on 10.06.2018.
 */
@Dao
public interface MovieDao {

    @Query("SELECT * FROM movie")
    LiveData<List<Movie>> getAllMovies();

    @Query("SELECT * FROM movie WHERE isFavorite = 1")
    LiveData<List<Movie>> getFavoriteMovies();

    @Query("SELECT * FROM movie WHERE id = :id")
    LiveData<Movie> getMovieById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovies(List<Movie> movies);

    @Query("DELETE FROM movie")
    void deleteMovies();

    @Query("DELETE FROM movie WHERE isFavorite = 0")
    void deleteAllNoFavsMovies();

}
