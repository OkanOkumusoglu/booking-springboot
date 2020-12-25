package com.ok.entities;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.util.HashMap;

@Entity
public class Booking {

    @EmbeddedId
    @org.springframework.data.annotation.Id
    private BookingId bookingId;
    private HashMap<String, Long> reservations = new HashMap<>();

    public BookingId getBookingId() {
        return bookingId;
    }

    public void setBookingId(BookingId bookingId) {
        this.bookingId = bookingId;
    }

    public HashMap<String, Long> getReservations() {
        return reservations;
    }

    public void setReservations(HashMap<String, Long> reservations) {
        this.reservations = reservations;
    }
}
