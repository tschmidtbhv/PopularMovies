package de.naturalsoft.popularmovies.data.network;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.util.Log;

import com.google.gson.GsonBuilder;

import java.util.List;

import de.naturalsoft.popularmovies.data.Movie;
import de.naturalsoft.popularmovies.data.MovieResponse;
import de.naturalsoft.popularmovies.utils.NetworkHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * PopularMovies
 * Created by Thomas Schmidt on 30.05.2018.
 */
public class NetworkUtil {

    private final static String CLASSTAG = NetworkUtil.class.getSimpleName();

    private static NetworkUtil sINSTANCE;
    private static final Object LOCK = new Object();

    private Context mContext;
    private static String lastSetting = "";

    public final static String BASEMOVIESURL = "http://api.themoviedb.org/3/";
    private final static String MOVIESKEY = ""; //TODO you need to set the API Key here

    private static MutableLiveData<List<Movie>> mDownloadedMovies;
    private static Retrofit mRetrofit;

    private NetworkUtil(Context context) {
        mContext = context;
        mDownloadedMovies = new MutableLiveData<List<Movie>>();
    }

    public synchronized static NetworkUtil getInstance(Context context, Retrofit retrofit) {

        if (sINSTANCE == null) {
            synchronized (LOCK) {
                Log.d(CLASSTAG, "NewX NetworkUtil");
                sINSTANCE = new NetworkUtil(context.getApplicationContext());
                mRetrofit = retrofit;
            }
        }

        return sINSTANCE;
    }

    /**
     * Load movies for a Type
     *
     * @param type to load (Top rated / popular)
     */
    public static void loadMoviesForType(String type) {

        MovieClient client = mRetrofit.create(MovieClient.class);
        Call<MovieResponse> call = client.getMovies(type, MOVIESKEY);

        try {
            if (call != null) doCall(call);
        } catch (NullPointerException e) {
            Log.d(CLASSTAG, "Nullpointer while executing call " + e);
        }

    }

    /**
     * Handle the Retrofit Callback
     *
     * @param call Retrofit Call Obj
     * @throws NullPointerException
     */
    private static void doCall(Call<MovieResponse> call) throws NullPointerException {

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                Log.d(CLASSTAG, "CALL onResponse " + response.toString() + " " + new GsonBuilder().setPrettyPrinting().create().toJson(response));
                mDownloadedMovies.postValue(response.body().getmMovieList());
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.d(CLASSTAG, "CALL onFailure " + t.getLocalizedMessage());
            }
        });
    }

    public LiveData<List<Movie>> getCurrentMovies() {
        return mDownloadedMovies;
    }

    /**
     * Check the lastSetting is different
     * from the currentSetting
     */
    public void checkSettingsHasChanged() {

        String currentSetting = NetworkHelper.getSelectedType(mContext);

        if (!lastSetting.equals(currentSetting)) {
            Log.d(CLASSTAG, "Settings has changed");
            loadMoviesForType(currentSetting);
            lastSetting = currentSetting;
        }
    }
}
