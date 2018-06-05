package de.naturalsoft.popularmovies.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;

import de.naturalsoft.popularmovies.R;
import de.naturalsoft.popularmovies.data.network.NetworkDataSource;

/**
 * PopularMovies
 * Created by Thomas Schmidt on 04.06.2018.
 */
public interface NetworkHelper {

    String imageWidth = "w500";

    /**
     * Build the ImagePath URI
     *
     * @param imagePath poster path
     * @return Poster Uri
     */
    static Uri getImageURI(String imagePath, String optImageWidth) {


        Uri.Builder imageUri = Uri.parse(NetworkDataSource.BASEIMAGEURL).buildUpon();

        if (optImageWidth == null) {
            imageUri.appendPath(imageWidth);
        } else {
            imageUri.appendPath(optImageWidth);
        }

        imageUri.appendEncodedPath(imagePath)
                .build();

        Log.d("TAG", "PATH: " + imageUri.toString());
        return imageUri.build();
    }

    /**
     * Check the connectionstate
     *
     * @param context
     * @return
     */
    static boolean checkConnectivityState(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    /**
     * @param context
     * @return
     */
    static String getSelectedType(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        String key = context.getString(R.string.sectionKey);
        String defValue = context.getString(R.string.default_popular_setting);
        return sharedPreferences.getString(key, defValue);
    }
}
