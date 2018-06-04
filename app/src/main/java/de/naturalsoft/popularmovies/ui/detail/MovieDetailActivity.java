package de.naturalsoft.popularmovies.ui.detail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.naturalsoft.popularmovies.R;
import de.naturalsoft.popularmovies.data.Movie;
import de.naturalsoft.popularmovies.utils.Config;

public class MovieDetailActivity extends AppCompatActivity {

    @BindView(R.id.poster)
    ImageView poster;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Movie movie = getMovie(extras);
            loadMovieDetails(movie);
        }
    }

    private void loadMovieDetails(Movie movie) {
        Picasso.get()
                .load("https://image.tmdb.org/t/p/w185_and_h278_bestv2" + movie.getPoster_path())
                .into(poster);

        getSupportActionBar().setTitle(movie.getTitle());
    }

    private Movie getMovie(Bundle extras) {
        Gson gson = new Gson();
        String jsonString = extras.getString(Config.MOVIEKEY);
        return gson.fromJson(jsonString, Movie.class);
    }

}
