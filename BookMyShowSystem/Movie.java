package BookMyShowSystem;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Movie {
    private String movieID;
    private LocalDate releaseDate;
    private List<Theater> runningTheaters;
    private int duration;

    public Movie(String movieID, LocalDate releaseDate, int duration) {
        this.movieID = movieID;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.runningTheaters = new ArrayList<>();
    }
    
    public synchronized void addTheater(Theater theater) {
        runningTheaters.add(theater);
    }

    public synchronized void removeTheater(Theater theater) {
        runningTheaters.remove(theater);
    }

    public String getMovieID() {
        return movieID;
    }

    public synchronized List<Theater> getRunningTheaters() {
        return Collections.unmodifiableList(new ArrayList<>(runningTheaters));
    }

    public int getDuration() {
        return duration;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }
}
