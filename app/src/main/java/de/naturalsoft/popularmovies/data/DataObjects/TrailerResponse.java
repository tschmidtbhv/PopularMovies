package de.naturalsoft.popularmovies.data.DataObjects;

import java.util.List;

/**
 * PopularMovies
 * Created by Thomas Schmidt on 20.06.2018.
 */
public class TrailerResponse {

    private List<Trailer> results;

    public void setTrailerList(List<Trailer> trailerList) {
        this.results = trailerList;
    }

    public List<Trailer> getTrailer() {
        return results;
    }

    public class Trailer {

        String id;
        String key;
        String name;
        String site;
        String type;

        public Trailer(String id, String key, String name, String site, String type) {
            this.id = id;
            this.key = key;
            this.name = name;
            this.site = site;
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSite() {
            return site;
        }

        public void setSite(String site) {
            this.site = site;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
