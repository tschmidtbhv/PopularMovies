package de.naturalsoft.popularmovies.data.network;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.util.Log;

import com.google.gson.GsonBuilder;

import java.util.List;

import de.naturalsoft.popularmovies.data.Movie;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * PopularMovies
 * Created by Thomas Schmidt on 30.05.2018.
 */
public class NetworkUtil {

    private final static String CLASSTAG = NetworkUtil.class.getSimpleName();

    private static NetworkUtil sINSTANCE;

    private Context mContext;
    private final static String BASEMOVIESURL = "http://api.themoviedb.org/3/";
    private final static String MOVIESKEY = ""; //TODO you need to set the API Key here

    private static MutableLiveData<List<Movie>> mDownloadedMovies;


    private NetworkUtil(Context context) {
        mContext = context;
        mDownloadedMovies = new MutableLiveData<List<Movie>>();
    }

    public static NetworkUtil getInstance(Context context) {

        if (sINSTANCE == null) {
            Log.d(CLASSTAG, "New NetworkUtil");
            sINSTANCE = new NetworkUtil(context.getApplicationContext());
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

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASEMOVIESURL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        MovieClient client = retrofit.create(MovieClient.class);
        Call<Movie> call = client.getPopularMovies(MOVIESKEY);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                Log.d(CLASSTAG, "CALL onResponse " + response.toString() + " " + new GsonBuilder().setPrettyPrinting().create().toJson(response));
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Log.d(CLASSTAG, "CALL onFailure " + t.getLocalizedMessage());
            }
        });

    }

    public LiveData<List<Movie>> getCurrentMovies() {
        return mDownloadedMovies;
    }
}
