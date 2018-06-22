package de.naturalsoft.popularmovies.data.network;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.util.Log;

import com.google.gson.GsonBuilder;

import java.util.List;

import de.naturalsoft.popularmovies.data.DataObjects.Movie;
import de.naturalsoft.popularmovies.data.DataObjects.ReviewResponse;
import de.naturalsoft.popularmovies.data.DataObjects.ReviewResponse.Review;
import de.naturalsoft.popularmovies.data.DataObjects.TrailerResponse;
import de.naturalsoft.popularmovies.data.DataObjects.TrailerResponse.Trailer;
import de.naturalsoft.popularmovies.data.database.MovieResponse;
import de.naturalsoft.popularmovies.error.NoKeyError;
import de.naturalsoft.popularmovies.utils.Constants.BuildConfig;
import de.naturalsoft.popularmovies.utils.DataHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static de.naturalsoft.popularmovies.utils.Constants.MOVIESSTDID;

/**
 * PopularMovies
 * Created by Thomas Schmidt on 30.05.2018.
 */
public class NetworkDataSource {

    private final static String CLASSTAG = NetworkDataSource.class.getSimpleName();

    private static NetworkDataSource sINSTANCE;
    private static final Object LOCK = new Object();

    private Context mContext;
    private static String lastSetting = "";


    private static MutableLiveData<List<Movie>> mDownloadedMovies;
    private static MutableLiveData<List<Trailer>> mTrailers;
    private static MutableLiveData<List<Review>> mReviews;

    private static Retrofit mRetrofit;

    private NetworkDataSource(Context context) {
        mContext = context;
        mDownloadedMovies = new MutableLiveData<List<Movie>>();
    }

    public synchronized static NetworkDataSource getInstance(Context context, Retrofit retrofit) {

        if (sINSTANCE == null) {
            synchronized (LOCK) {
                Log.d(CLASSTAG, "NewX NetworkUtil");
                sINSTANCE = new NetworkDataSource(context.getApplicationContext());
                mRetrofit = retrofit;
            }
        }

        return sINSTANCE;
    }

    /**
     * Check the given MoviesKey is not an empty
     *
     * @return MoviesKey
     * @throws NoKeyError
     */
    private static String getMovieskey() throws NoKeyError {

        if (BuildConfig.API_KEY.isEmpty()) throw new NoKeyError();

        return BuildConfig.API_KEY;
    }

    /**
     * Handle the Retrofit Callback
     *
     * @param call Retrofit Call Obj
     * @throws NullPointerException
     */
    private static <T> void doCall(Call<T> call) throws NullPointerException {

        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                Log.d(CLASSTAG, "CALL onResponse " + response.toString() + " " + new GsonBuilder().setPrettyPrinting().create().toJson(response));
                Log.d(CLASSTAG, "ClassType: " + response.body().getClass().getSimpleName());

                Class classType = response.body().getClass();
                if (classType.isAssignableFrom(MovieResponse.class)) {
                    mDownloadedMovies.postValue(((MovieResponse) response.body()).getmMovieList());
                } else if (classType.isAssignableFrom(TrailerResponse.class)) {
                    mTrailers.postValue(((TrailerResponse) response.body()).getTrailer());
                } else if (classType.isAssignableFrom(ReviewResponse.class)) {
                    mReviews.postValue(((ReviewResponse) response.body()).getReviews());
                }

            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                Log.d(CLASSTAG, "CALL onFailure " + t.getLocalizedMessage());
            }
        });
    }

    /**
     * Load movies for a Type
     *
     * @param type to load (Top rated / popular)
     */
    public void loadMoviesForType(int id, String type) {

        try {

            MovieClient client = mRetrofit.create(MovieClient.class);

            Call<?> call = null;
            if (id == MOVIESSTDID) {
                call = client.getMovies(type, getMovieskey());
            } else if (type.equals("trailer")) {
                Log.d(CLASSTAG, "MOVIES ID " + id);
                call = client.getTrailerByMovieId(id, getMovieskey());
            } else if (type.equals("reviews")) {
                call = client.getReviewsByMovieId(id, getMovieskey());
            }


            if (call != null) doCall(call);
        } catch (NullPointerException e) {
            Log.e(CLASSTAG, "Nullpointer while executing call " + e);
        } catch (NoKeyError e) {
            Log.e(CLASSTAG, "Please create and add your API Key. Currentkey value is " + e.getMessage());
        }

    }


    /**
     * Get the current Movies
     *
     * @return LiveData List for Movies
     */
    public LiveData<List<Movie>> getCurrentMovies() {
        return mDownloadedMovies;
    }

    /**
     * Trailer for Movie
     *
     * @param id Movie id
     * @return List of Trailer
     */
    public LiveData<List<Trailer>> getTrailerByMovieId(int id) {
        if (mTrailers == null) {
            mTrailers = new MutableLiveData<>();
        }

        loadMoviesForType(id, "trailer");
        return mTrailers;
    }


    public LiveData<List<Review>> getReviewsByMovieId(int id) {

        if (mReviews == null) mReviews = new MutableLiveData<>();
        loadMoviesForType(id, "reviews");
        return mReviews;
    }

    /**
     * Check the lastSetting is different
     * from the currentSetting
     */
    public void checkSettingsHasChanged() {

        String currentSetting = DataHelper.getSelectedType(mContext);

        if (!lastSetting.equals(currentSetting) || mDownloadedMovies.getValue() == null) {
            Log.d(CLASSTAG, "Settings has changed");
            loadMoviesForType(MOVIESSTDID, currentSetting);
            lastSetting = currentSetting;
        }
    }
}
