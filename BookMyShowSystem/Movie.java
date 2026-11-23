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

    public String getMovieID() {
        return movieID;
    }

    public List<Theater> getRunningTheaters() {
        return Collections.unmodifiableList(new ArrayList<>(runningTheaters));
    }
}
