package de.naturalsoft.popularmovies.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import de.naturalsoft.popularmovies.R;

/**
 * PopularMovies
 * Created by Thomas Schmidt on 14.06.2018.
 */
public interface DataHelper {


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
