package com.ok.dao.responses.booking;

import java.util.HashMap;

public class ListBookingDayResponse {

    private boolean success;
    private String message;
    private HashMap<String,Long> reservations;

    public HashMap<String, Long> getReservations() {
        return reservations;
    }

    public void setReservations(HashMap<String, Long> reservations) {
        this.reservations = reservations;
    }

    public boolean isSuccess() {
        return success;
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
