package com.ok.dao.responses.booking;

import com.ok.entities.Reservation;

import java.util.ArrayList;
import java.util.List;


public class ListBookingResponse {

    private boolean success;
    private String message;
    private List<Reservation> reservations = new ArrayList<>();


    public boolean getSuccess() {
        return success;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
