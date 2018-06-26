package de.naturalsoft.popularmovies.data.DataObjects;

import java.util.List;

/**
 * PopularMovies
 * Created by Thomas Schmidt on 20.06.2018.
 */
public class ReviewResponse {

    List<Review> results;

    public List<Review> getReviews() {
        return results;
    }

    public class Review {
        private String id;
        private String author;
        private String content;
        private String url;

        public Review(String id, String author, String content, String url) {
            this.id = id;
            this.author = author;
            this.content = content;
            this.url = url;
        }

        public String getId() {
            return id;
        }

        public String getAuthor() {
            return author;
        }

        public String getContent() {
            return content;
        }

        public String getUrl() {
            return url;
        }
    }
}
