package de.naturalsoft.popularmovies.data.network;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.util.Log;

import com.google.gson.GsonBuilder;

import java.util.List;

import de.naturalsoft.popularmovies.data.Movie;
import de.naturalsoft.popularmovies.data.MovieResponse;
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

    private Context mContext;
    public final static String BASEMOVIESURL = "http://api.themoviedb.org/3/";
    private final static String MOVIESKEY = ""; //TODO you need to set the API Key here

    private static MutableLiveData<List<Movie>> mDownloadedMovies;
    private static Retrofit mRetrofit;


    public final static int POPULARMOVIES = 0;
    public final static int TOPRATEDMOVIES = 1;

    private NetworkUtil(Context context) {
        mContext = context;
        mDownloadedMovies = new MutableLiveData<List<Movie>>();
    }

    public static NetworkUtil getInstance(Context context, Retrofit retrofit) {

        if (sINSTANCE == null) {
            Log.d(CLASSTAG, "New NetworkUtil");
            sINSTANCE = new NetworkUtil(context.getApplicationContext());
            mRetrofit = retrofit;
        }

        loadMoviesForType(0);
        return sINSTANCE;
    }

    /**
     * Load movies for a Type
     * TODO Type and separation
     *
     * @param type
     */
    public static void loadMoviesForType(int type) {

        MovieClient client = mRetrofit.create(MovieClient.class);
        Call<MovieResponse> call = null;

        switch (type) {

            case POPULARMOVIES:
                call = client.getPopularMovies(MOVIESKEY);
                break;

            case TOPRATEDMOVIES:
                call = client.getTopRatedMovies(MOVIESKEY);
                break;
        }

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
}
