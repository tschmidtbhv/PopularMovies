package de.naturalsoft.popularmovies.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

/**
 * PopularMovies
 * Created by Thomas Schmidt on 10.06.2018.
 */
@Database(entities = Movie.class, version = 1, exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {

    private static final String CLASSNAME = MovieDatabase.class.getSimpleName();

    private static final String DATABASENAME = "movies.db";

    private static final Object LOCK = new Object();
    private static MovieDatabase sINSTANCE;


    public static MovieDatabase getInstance(Context context) {

        if (sINSTANCE == null) {
            synchronized (LOCK) {
                sINSTANCE = Room.databaseBuilder(context, MovieDatabase.class, DATABASENAME).build();
                Log.d(CLASSNAME, "New Database created");
            }
        }
        Log.d(CLASSNAME, "Getting the Database instance");
        return sINSTANCE;
    }

    public abstract MovieDao movieDao();
}
