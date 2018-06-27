package de.naturalsoft.popularmovies.utils.helper;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * PopularMovies
 * Created by Thomas Schmidt on 27.06.2018.
 */
public interface ViewHelper {

    /**
     * Calculate the possible Columns
     * at runtime for the device
     *
     * @param context Act. Context
     * @return number Of Columns
     */
    static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int scalingFactor = 200;
        int noOfColumns = (int) (dpWidth / scalingFactor);
        if (noOfColumns < 2)
            noOfColumns = 2;
        return noOfColumns;
    }
}
