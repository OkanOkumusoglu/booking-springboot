package com.ok.dao.controllers;

import com.ok.FindBookingCompanyNameAndDay;
import com.ok.ValidationChecks;
import com.ok.dao.BookingRepository;
import com.ok.dao.CompanyRepository;
import com.ok.dao.UserRepository;
import com.ok.dao.controllers.methods.BookingMethods;
import com.ok.dao.requests.booking.*;
import com.ok.dao.responses.booking.*;
import com.ok.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    BookingRepository bookRepo;

    @Autowired
    CompanyRepository compRepo;

    @Autowired
    UserRepository userRepo;


    private static String[] days = {"Pazartesi", "Sali", "Carsamba", "Persembe", "Cuma", "Cumartesi", "Pazar"};


    @PostMapping("/list-day")
    public ListBookingDayResponse dailyList(@RequestBody ListBookingDayRequest request) {
        ListBookingDayResponse response = new ListBookingDayResponse();
        BookingId bookingId = new BookingId(request.getCompanyName().toUpperCase(), request.getDay().toUpperCase());
        if (bookRepo.existsById(bookingId)) {
            Booking booking = bookRepo.findById(bookingId).get();
            response.setReservations(booking.getReservations());
            response.setSuccess(true);
            response.setMessage("Successful!");
        } else {
            response.setSuccess(false);
            response.setMessage("Booking doesn't exist!");
        }
        return response;

    }

    @PostMapping("cancel-hour")
    public CancelBookingHourResponse cancelBookingHour(@RequestBody CancelBookingHourRequest request) {
        CancelBookingHourResponse response = new CancelBookingHourResponse();
        if (compRepo.existsById(request.getCompanyNameBranch())) {
            BookingId bookingId = new BookingId(request.getCompanyNameBranch().toUpperCase(), request.getDay().toUpperCase());
            Booking booking = bookRepo.findById(bookingId).get();
            if (bookRepo.existsById(bookingId)) {
                Company company = compRepo.findByCompanyNameIgnoreCase(request.getCompanyNameBranch());
                BookingMethods.cancelHourUserBooking(userRepo, request, response);
                int companyBookingIndex = FindBookingCompanyNameAndDay.findIndex((ArrayList<Booking>) company.getBookings(), request.getCompanyNameBranch().toUpperCase(),
                        request.getDay().toUpperCase(), request.getHour());
                if (!company.getBookings().isEmpty()) {
                    company.getBookings().get(companyBookingIndex).getReservations().remove(request.getHour());
                }
                booking.getReservations().remove(request.getHour());
                bookRepo.save(booking);
                compRepo.save(company);
                response.setSuccess(true);
                response.setMessage("Successful!");
            } else {
                response.setSuccess(false);
                response.setMessage("Booking doesn't exist!");
            }
        } else {
            response.setSuccess(false);
            response.setMessage("Company doesn't exist!");
        }
        return response;
    }

    @PostMapping("/delete")
    public DeleteBookingResponse deleteBooking(@RequestBody DeleteBookingRequest request) {
        DeleteBookingResponse response = new DeleteBookingResponse();
        if (compRepo.existsById(request.getCompanyNameBranch())) {
            BookingId bookingId = new BookingId(request.getCompanyNameBranch().toUpperCase(), request.getDay().toUpperCase());
            if (bookRepo.existsById(bookingId)) {
                Company company = compRepo.findByCompanyNameIgnoreCase(request.getCompanyNameBranch());
                BookingMethods.cancelUserBooking(userRepo, request, response);
                int companyBookingIndex = FindBookingCompanyNameAndDay.findIndexWithoutHour((ArrayList<Booking>) company.getBookings(), request.getCompanyNameBranch().toUpperCase(),
                        request.getDay().toUpperCase());
                if (!company.getBookings().isEmpty()) {
                    company.getBookings().remove(companyBookingIndex);
                }
                bookRepo.deleteById(bookingId);
                compRepo.save(company);
                response.setSuccess(true);
                response.setMessage("Successful!");

            } else {
                response.setSuccess(false);
                response.setMessage("Booking doesn't exist!");
            }


        } else {
            response.setSuccess(false);
            response.setMessage("There is no company like that!");
        }
        return response;
    }


    @PostMapping("/reservation-times")
    public ListBookingResponse list(@RequestBody ListBookingRequest request) {
        ListBookingResponse response = new ListBookingResponse();
        Company company = compRepo.findByCompanyNameIgnoreCase(request.getCompanyNameBranch());
        if (company != null) {
            for (int i = 0; i < days.length; i++) {
                Reservation reservation = new Reservation();
                BookingId bookingId = new BookingId(request.getCompanyNameBranch().toUpperCase(), days[i].toUpperCase());
                if (bookRepo.existsById(bookingId)) {
                    Booking booking = bookRepo.findById(bookingId).get();
                    HashMap<String, Long> temp = new HashMap<>();
                    temp.putAll(booking.getReservations());
                    reservation.setDay(days[i]);
                    reservation.setReservations(temp);
                    response.setSuccess(true);
                    response.setMessage("Successful!");
                    response.getReservations().add(reservation);


                }
            }
        } else {
            response.setSuccess(false);
            response.setMessage("Company doesn't exist!");

        }
        return response;
    }


    @PostMapping("/create-booking")
    public CreateBookingResponse createBooking(@RequestBody CreateBookingRequest request) {
        CreateBookingResponse response = new CreateBookingResponse();
        if (compRepo.existsById(request.getCompanyNameBranch())) {
            Booking booking = new Booking();
            BookingId bookingId = new BookingId(request.getCompanyNameBranch(), request.getDay());
            BookingMethods.createBookingAction(bookingId, booking, bookRepo, request, response);
        } else {
            response.setSuccess(false);
            response.setMessage("There is no company like that!");
        }

        return response;
    }


    @PostMapping("create-reservation")
    public CreateReservationResponse createReservation(@RequestBody CreateReservationRequest request) {
        CreateReservationResponse response = new CreateReservationResponse();
        BookingId bookingId = new BookingId(request.getCompanyNameBranch().toUpperCase(), request.getDay().toUpperCase());
        if (ValidationChecks.isValidTime(request.getHour())) {
            if (bookRepo.existsById(bookingId)) {
                Booking booking = bookRepo.findById(bookingId).get();
                Company company = compRepo.findByCompanyNameIgnoreCase(request.getCompanyNameBranch());
                int companyBookingIndex = FindBookingCompanyNameAndDay.findIndexWithoutHour((ArrayList<Booking>) company.getBookings(), request.getCompanyNameBranch().toUpperCase(),
                        request.getDay().toUpperCase());
                if (companyBookingIndex == -1) {
                    company.getBookings().add(booking);

                } else {
                    company.getBookings().get(companyBookingIndex).getReservations().put(request.getHour(), request.getCapacity());

                }
                BookingMethods.createReservationAction(booking, company, bookRepo, compRepo, request, response);
            } else {
                response.setSuccess(false);
                response.setMessage("Booking doesn't exist!");
            }
        } else {
            response.setSuccess(false);
            response.setMessage("Hour is not valid!");
        }

        return response;
    }


}
