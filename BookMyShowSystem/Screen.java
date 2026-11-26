package BookMyShowSystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Screen {
    private Theater theater;
    private String screenID;
    private String screenNumber;
    List<Show> runningShows;

    public Screen(Theater theater, String screenID, String screenNumber) {
        this.theater = theater;
        this.screenID = screenID;
        this.screenNumber = screenNumber;
        this.runningShows = new ArrayList<>();
    }

    public synchronized List<Show> getShows() {
        return Collections.unmodifiableList(new ArrayList<>(runningShows));
    }

    public String getScreenID() {
        return screenID;
    }

    public synchronized void addShow(Show show) {
        runningShows.add(show);
    }

    public synchronized void removeShow(Show show) {
        runningShows.remove(show);
    }
}
