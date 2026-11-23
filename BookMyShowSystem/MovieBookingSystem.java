package BookMyShowSystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MovieBookingSystem {
    private List<Theater> theaters;
    private List<Movie> runningMovies;

    public boolean bookMovie(String movieId, String theaterID, String screenID, List<Seat> seats) {
        Theater theater = getTheaterIfAvailable(theaterID);
        if (theater == null) return false;
        for (Screen screen : theater.getScreens()) {
            if (screen.getScreenID().equals(screenID)) {
                List<Show> shows = screen.getShows();
                for (Show show : shows) {
                    
                }
            }
        }
    }

    public synchronized List<List<List<Seat>>> getSeatsForTheaterByMovieID(String theaterID, String movieID) {
        List<List<List<Seat>>> availableShows = new ArrayList<>();
        Theater theater = getTheaterIfAvailable(theaterID);
        if (theater == null) return null;
        for (Screen screen : theater.getScreens()) {
            List<Show> shows = screen.getShows();
            if (shows == null) continue;
            for (Show show : shows) {
                if (show.getMovie().getMovieID().equals(theaterID)) {
                    availableShows.add(show.getSeats());
                }
            }
        }
        return availableShows;
    }

    public Theater getTheaterIfAvailable(String theaterID) {
        for (Theater theater : theaters) {
            if (theater.getTheaterID().equals(theaterID)) return theater;
        }
        return null;
    }

    public List<Movie> getRunningMovies() {
        return Collections.unmodifiableList(new ArrayList<>(runningMovies));
    }

    public List<Show> checkAvailabilityByMovie(String movieID) {
        List<Show> availableShows = new ArrayList<>();
        Movie movie = getMovieIfRunning(movieID);
        if (movie == null) return null;
        List<Theater> runningTheaters = movie.getRunningTheaters();
        if (runningTheaters == null) return null;
        for (Theater theater : runningTheaters) {
            List<Screen> screens = theater.getScreens();
            if (screens == null) continue;
            for (Screen screen : screens) {
                List<Show> shows = screen.getShows();
                if (shows == null) continue;
                for (Show show : shows) {
                    if (show.getMovie().getMovieID().equals(movieID)) {
                        availableShows.add(show);
                    }
                }
            }
        }
        return availableShows;
    }
    public Movie getMovieIfRunning(String movieId) {
        for (Movie movie : runningMovies) {
            if (movie.getMovieID().equals(movieId)) return movie;
        }
        return null;
    }

}
