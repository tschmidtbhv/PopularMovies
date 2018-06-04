package de.naturalsoft.popularmovies.data;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import java.util.List;

import de.naturalsoft.popularmovies.data.network.NetworkUtil;

/**
 * PopularMovies
 * Created by Thomas Schmidt on 30.05.2018.
 */
public class MovieRepository {

    private final static String CLASSTAG = MovieRepository.class.getSimpleName();

    private static final Object LOCK = new Object();
    private static  MovieRepository sINSTANCE;
    private static NetworkUtil mNetworkUtil;

    private LiveData<List<Movie>> mMovies;

    private MovieRepository(NetworkUtil networkUtil){
        mNetworkUtil = networkUtil;
        mMovies = mNetworkUtil.getCurrentMovies();
    }

    public synchronized static MovieRepository getInstance(NetworkUtil networkUtil){
        if(sINSTANCE == null){
            synchronized (LOCK) {
                Log.d(CLASSTAG, "NewX MovieRepository");
                sINSTANCE = new MovieRepository(networkUtil);
            }
        }

        return sINSTANCE;
    }

    public LiveData<List<Movie>>getCurrentMovies(){
        return mMovies;
    }

    public void checkSettingsHasChanged(){
        mNetworkUtil.checkSettingsHasChanged();
    }

}
