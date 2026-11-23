package BookMyShowSystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Screen {
    private Theater theater;
    private String screenId;
    private String screenNumber;
    List<Show> runningShows;

    public List<Show> getShows() {
        return Collections.unmodifiableList(new ArrayList<>(runningShows));
    }

    public String getScreenID() {
        return screenId;
    }
}
