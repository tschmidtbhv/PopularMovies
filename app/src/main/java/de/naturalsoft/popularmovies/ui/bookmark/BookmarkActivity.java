package de.naturalsoft.popularmovies.ui.bookmark;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;

import de.naturalsoft.popularmovies.R;
import de.naturalsoft.popularmovies.ui.BaseActivity;
import de.naturalsoft.popularmovies.ui.share.MovieViewModelFactory;
import de.naturalsoft.popularmovies.utils.InjectorUtil;

public class BookmarkActivity extends BaseActivity {

    @Override
    public String getActionBarTitle() {
        return getString(R.string.favorites);

    }

    @Override
    public ViewModel getViewModel() {
        MovieViewModelFactory factory = (MovieViewModelFactory) InjectorUtil.provideMovieViewModelFactory(this);
        return ViewModelProviders.of(this, factory).get(BookmarkActivityViewModel.class);
    }
}
