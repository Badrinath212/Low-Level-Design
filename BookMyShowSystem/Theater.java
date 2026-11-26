package BookMyShowSystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Theater {
    private String theaterID;
    private String location;
    private List<Screen> screens;
    private String theaterName;

    public Theater(String theaterID, String location, List<Screen> screens, String theaterName) {
        this.theaterID = theaterID;
        this.location = location;
        this.screens = screens;
        this.theaterName = theaterName;
    }

    public synchronized List<Screen> getScreens() {
        return Collections.unmodifiableList(new ArrayList<>(screens));
    }

    public String getTheaterID() {
        return theaterID;
    }

    public synchronized void addScreen(Screen screen) {
        screens.add(screen);
    }

    public synchronized void removeScreen(Screen screen) {
        screens.remove(screen);
    }
}