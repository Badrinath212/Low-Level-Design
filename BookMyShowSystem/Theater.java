package BookMyShowSystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Theater {
    private String theaterID;
    private String location;
    private List<Screen> screens;
    private String theaterName;

    public List<Screen> getScreens() {
        return Collections.unmodifiableList(new ArrayList<>(screens));
    }

    public String getTheaterID() {
        return theaterID;
    }
}