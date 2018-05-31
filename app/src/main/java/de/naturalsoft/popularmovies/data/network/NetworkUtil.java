package de.naturalsoft.popularmovies.data.network;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.util.Log;

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
    private static MutableLiveData<List<Movie>> mDownloadedMovies;


    private NetworkUtil(Context context){
        mContext = context;
        mDownloadedMovies = new MutableLiveData<List<Movie>>();
    }

    public static NetworkUtil getInstance(Context context){

        if(sINSTANCE == null){
            Log.d(CLASSTAG, "New NetworkUtil");
            sINSTANCE = new NetworkUtil(context.getApplicationContext());
        }

        loadMoviesForType(0);
        return sINSTANCE;
    }

    /**
     * Load movies for a Type
     * TODO Type and separation
     * @param type
     */
    public static void loadMoviesForType(int type){

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASEMOVIESURL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        MovieClient client = retrofit.create(MovieClient.class);
        Call<List<Movie>> call =  client.getPopularMovies();
        call.enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                Log.d(CLASSTAG, "CALL onResponse " + response.toString() + " " + response.raw());
                mDownloadedMovies.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {
                Log.d(CLASSTAG, "CALL onFailure");
            }
        });

    }

    public LiveData<List<Movie>> getCurrentMovies(){
        return mDownloadedMovies;
    }
}
