package BookMyShowSystem;

import java.time.LocalDate;
import java.util.List;

public class Movie {
    private String movieId;
    private LocalDate releaseDate;
    private List<Theater> runningTheaters;
    private int duration;
}
