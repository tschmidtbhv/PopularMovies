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
    private static String mType;
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

    private static void insertMovieWithType(List<Movie> movies, String type) {

        for (Movie movie : movies) {
            movie.setType(type);
        }

        mMovieDao.insertMovies(movies);
    }

    private void settingObserver() {
        LiveData<List<Movie>> movies = mNetworkDataSource.getCurrentMovies();
        movies.observeForever(moviesFromNetwork -> {
            mExecutors.getDiskIO().execute(() -> {
                deleteOldData(movies);
                insertMovieWithType(moviesFromNetwork, mType);
            });
        });
    }

    private void deleteOldData(LiveData<List<Movie>> movies) {
        mMovieDao.deleteMovies();
    }

    public LiveData<List<Movie>> getMoviesByType(String type) {
        mType = type;
        return mMovieDao.getMoviesByType(mType);
    }

    public LiveData<List<Movie>> getCurrentMovies() {
        return mMovieDao.getAllMovies();
    }

    public void loadMoviesByType(String type) {
        mNetworkDataSource.loadMoviesForType(type);
    }

    public void checkSettingsHasChanged() {
        mNetworkDataSource.checkSettingsHasChanged();
    }
}
