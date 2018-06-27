package de.naturalsoft.popularmovies.data;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import java.util.List;

import de.naturalsoft.popularmovies.data.DataObjects.Movie;
import de.naturalsoft.popularmovies.data.DataObjects.ReviewResponse;
import de.naturalsoft.popularmovies.data.DataObjects.TrailerResponse.Trailer;
import de.naturalsoft.popularmovies.data.database.MovieDao;
import de.naturalsoft.popularmovies.data.network.NetworkDataSource;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

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


    private MovieRepository(MovieDao movieDao, NetworkDataSource networkUtil) {
        mNetworkDataSource = networkUtil;
        mMovieDao = movieDao;
        settingObserver();
    }

    public synchronized static MovieRepository getInstance(MovieDao movieDao, NetworkDataSource networkUtil) {
        if (sINSTANCE == null) {
            synchronized (LOCK) {
                Log.d(CLASSTAG, "NewX MovieRepository");
                sINSTANCE = new MovieRepository(movieDao, networkUtil);
            }
        }

        return sINSTANCE;
    }

    private void settingObserver() {
        LiveData<List<Movie>> movies = mNetworkDataSource.getCurrentMovies();
        movies.observeForever(moviesFromNetwork -> {
            RxJavaPlugins.onSingleScheduler(Schedulers.single()).scheduleDirect(() -> {
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
        RxJavaPlugins.onSingleScheduler(Schedulers.single()).scheduleDirect(() -> {
            mMovieDao.updateMovie(movie);
        });
    }

    public LiveData<List<Trailer>> getTrailerByMovieId(int id) {
        return mNetworkDataSource.getTrailerByMovieId(id);
    }

    public LiveData<List<ReviewResponse.Review>> getReviewsByMovieId(int id) {
        return mNetworkDataSource.getReviewsByMovieId(id);
    }
}
