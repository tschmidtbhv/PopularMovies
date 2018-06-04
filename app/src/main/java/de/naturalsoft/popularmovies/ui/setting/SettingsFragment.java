package de.naturalsoft.popularmovies.ui.setting;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

import de.naturalsoft.popularmovies.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);

        Preference sectionPref = findPreference(getString(R.string.sectionKey));
        bindPreference(sectionPref);
    }

    private void bindPreference(Preference preference) {
        preference.setOnPreferenceChangeListener(this);
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(preference.getContext());
        String preferenceAsString = sharedPreferences
                .getString(preference.getKey(), getString(R.string.popular));
        onPreferenceChange(preference,preferenceAsString);

    }

    /**
     * Called when Preferences changed
     *
     * @param preference to change
     * @param newValue   New input value
     * @return boolean
     */
    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {

        String stringValue = newValue.toString();

        if (preference instanceof ListPreference) {

            ListPreference listPreference = (ListPreference) preference;
            int index = listPreference.findIndexOfValue(stringValue);

            if (index >= 0) {
                CharSequence[] labels = listPreference.getEntries();
                preference.setSummary(labels[index]);
            }

        } else {
            preference.setSummary(stringValue);
        }

        Log.d("TAG", "onPreferenceChange");
        return true;
    }
}
