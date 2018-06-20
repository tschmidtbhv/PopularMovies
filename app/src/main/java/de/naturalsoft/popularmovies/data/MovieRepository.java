package de.naturalsoft.popularmovies.data;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import java.util.List;

import de.naturalsoft.popularmovies.AppExecutors;
import de.naturalsoft.popularmovies.data.database.Movie;
import de.naturalsoft.popularmovies.data.database.MovieDao;
import de.naturalsoft.popularmovies.data.network.NetworkDataSource;

/**
 * PopularMovies
 * Created by Thomas Schmidt on 30.05.2018.
 */
public class MovieRepository {

    private final static String CLASSTAG = MovieRepository.class.getSimpleName();

    private static final Object LOCK = new Object();
    private static MovieRepository sINSTANCE;
    private static NetworkDataSource mNetworkDataSource;
    private static MovieDao mMovieDao;
    private final AppExecutors mExecutors;


    private MovieRepository(MovieDao movieDao, NetworkDataSource networkUtil, AppExecutors executors) {
        mNetworkDataSource = networkUtil;
        mMovieDao = movieDao;
        mExecutors = executors;
        settingObserver();
    }

    public synchronized static MovieRepository getInstance(MovieDao movieDao, NetworkDataSource networkUtil, AppExecutors executors) {
        if (sINSTANCE == null) {
            synchronized (LOCK) {
                Log.d(CLASSTAG, "NewX MovieRepository");
                sINSTANCE = new MovieRepository(movieDao, networkUtil, executors);
            }
        }

        return sINSTANCE;
    }

    private void settingObserver() {
        LiveData<List<Movie>> movies = mNetworkDataSource.getCurrentMovies();
        movies.observeForever(moviesFromNetwork -> {
            mExecutors.getDiskIO().execute(() -> {
                deleteOldData();
                mMovieDao.insertMovies(moviesFromNetwork);
            });
        });
    }

    private void deleteOldData() {
        mMovieDao.deleteAllNoFavsMovies();
    }


    public LiveData<List<Movie>> getCurrentMovies() {
        return mMovieDao.getAllMovies();
    }

    public LiveData<Movie> getMovieById(int id) {
        return mMovieDao.getMovieById(id);
    }

    public void checkSettingsHasChanged() {
        mNetworkDataSource.checkSettingsHasChanged();
    }

    public LiveData<List<Movie>> getCurrentFavorites() {
        return mMovieDao.getFavoriteMovies();
    }

    public void updateMoview(Movie movie) {
        mExecutors.getDiskIO().execute(() -> {
            mMovieDao.updateMovie(movie);
        });
    }

    public void loadTrailerById(int id) {
        mNetworkDataSource.loadMoviesForType(id, "trailer");
    }
}
