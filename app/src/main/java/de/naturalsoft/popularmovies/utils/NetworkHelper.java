package de.naturalsoft.popularmovies.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;

import de.naturalsoft.popularmovies.utils.Constants.BuildConfig;
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


        Uri.Builder imageUri = Uri.parse(BuildConfig.BASEIMAGEURL).buildUpon();

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


}
