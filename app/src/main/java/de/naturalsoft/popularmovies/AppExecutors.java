package de.naturalsoft.popularmovies;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * PopularMovies
 * Created by Thomas Schmidt on 13.06.2018.
 */
public class AppExecutors {

    private static final Object LOCK = new Object();
    private static AppExecutors sINSTANCE;

    private final Executor diskIO;

    private AppExecutors(Executor diskIO) {
        this.diskIO = diskIO;
    }

    public static AppExecutors getInstance() {
        if (sINSTANCE == null) {
            synchronized (LOCK) {
                sINSTANCE = new AppExecutors(Executors.newSingleThreadExecutor());
            }
        }

        return sINSTANCE;
    }

    public Executor getDiskIO() {
        return diskIO;
    }
}
