package de.naturalsoft.popularmovies.ui.list;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.view.Menu;
import android.view.MenuItem;

import de.naturalsoft.popularmovies.R;
import de.naturalsoft.popularmovies.ui.BaseActivity;
import de.naturalsoft.popularmovies.ui.bookmark.BookmarkActivity;
import de.naturalsoft.popularmovies.ui.setting.SettingsActivity;
import de.naturalsoft.popularmovies.utils.Constants;
import de.naturalsoft.popularmovies.utils.NetworkHelper;

public class MovieActivity extends BaseActivity {

    private final BroadcastReceiver mNetworkStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (NetworkHelper.checkConnectivityState(MovieActivity.this) && getViewModel() != null) {
                getViewModel().checkSettingsHasChanged();
            }
        }
    };


    @Override
    protected void onResume() {
        super.onResume();

        IntentFilter networkState = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mNetworkStateReceiver, networkState);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mNetworkStateReceiver);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent = null;
        switch (item.getItemId()) {

            case R.id.settings:
                intent = new Intent(MovieActivity.this, SettingsActivity.class);
                break;

            case R.id.bookmark:
                intent = new Intent(MovieActivity.this, BookmarkActivity.class);
                intent.putExtra(Constants.ISBOOKMARK, true);
                break;
        }

        if (intent != null) startActivity(intent);

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.settings_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


}
