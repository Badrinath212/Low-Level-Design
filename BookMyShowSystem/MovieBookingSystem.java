package BookMyShowSystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class MovieBookingSystem {
    private List<Theater> theaters;
    private List<Movie> runningMovies;

    public static void main(String[] args) {
        
    }

    public synchronized void markSeatStatusHealper(List<Seat> seats, Show show) {
        for (Seat s : seats) {
            if (s.getBookingStatus().equals(BookingStatus.AVAILABLE)) {
                s.setBookingStatus(BookingStatus.PENDING);
                show.addToLockedSeats(s);
                s.setTtl();
            }
        }
    } 

    public synchronized boolean makePayment(List<Seat> seats, Show show) {
        for (Seat seat : seats) {
            seat.setBookingStatus(BookingStatus.RESERVED);
            show.removeLockForSeat(seat);
        }
        Ticket ticket = new Ticket(UUID.randomUUID().toString(), show, seats);
        show.addBooking(ticket);
        return true;
    }

    public synchronized boolean bookMovie(String movieID, String theaterID, String screenID, List<Seat> seats, String showID) {
        Theater theater = getTheaterIfAvailable(theaterID);
        if (theater == null) return false;
        for (Screen screen : theater.getScreens()) {
            if (screen.getScreenID().equals(screenID)) {
                List<Show> shows = screen.getShows();
                for (Show show : shows) {
                    if (show.getShowID().equals(showID)) {
                        List<List<Seat>> totalSeats = show.getSeats();
                        List<Seat> goldSeats = totalSeats.get(0);
                        List<Seat> platSeats = totalSeats.get(1);
                        List<Seat> diamondSeats = totalSeats.get(2);
                        for (Seat seat : seats) {
                            SeatType seatType = seat.getSeatType();
                            if (seatType == SeatType.GOLD) {
                                markSeatStatusHealper(goldSeats, show);
                            } else if (seatType == SeatType.PLATINUM) {
                                markSeatStatusHealper(platSeats, show);
                            } else if (seatType == SeatType.DIAMOND) {
                                markSeatStatusHealper(diamondSeats, show);
                            } else {
                                throw new IllegalArgumentException("Invalid seat");
                            }
                        }
                        // we have to call the payment method
                        boolean paymentStatus = makePayment(seats, show);
                        if (paymentStatus) {

                        }
                    }
                }
            }
        }
        return true;
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
