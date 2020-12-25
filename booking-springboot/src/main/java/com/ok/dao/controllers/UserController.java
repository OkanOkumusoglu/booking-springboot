package com.ok.dao.controllers;


import com.ok.FindBookingCompanyNameAndDay;
import com.ok.ValidationChecks;
import com.ok.dao.BookingRepository;
import com.ok.dao.CompanyRepository;
import com.ok.dao.UserRepository;
import com.ok.dao.controllers.methods.UserMethods;
import com.ok.dao.requests.user.*;
import com.ok.dao.responses.GenericResponse;
import com.ok.dao.responses.user.CancelBookingResponse;
import com.ok.dao.responses.user.CreateUserResponse;
import com.ok.dao.responses.user.UserBookingResponse;
import com.ok.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserRepository userRepo;

    @Autowired
    BookingRepository bookRepo;

    @Autowired
    CompanyRepository compRepo;

    @GetMapping("/delete/{name}")
    public void delete(@PathVariable("name") String name) {
        userRepo.deleteById(name);
    }


    @GetMapping("/list/user-name/{userName}")
    public List<User> userListByUserName(@PathVariable("userName") String userName) {
        return userRepo.findUserByRegexpName(userName);
    }

    @GetMapping("/list/first-name/{firstName}")
    public List<User> userListByFirstName(@PathVariable("firstName") String firstName) {
        return userRepo.findUserByRegexpFirstName(firstName);
    }

    @GetMapping("/list/last-name/{lastName}")
    public List<User> userListByLastName(@PathVariable("lastName") String lastName) {
        return userRepo.findUserByRegexpLastName(lastName);
    }


    @PostMapping("/login")
    private GenericResponse userLogin(@RequestBody UserLoginRequest request) {
        GenericResponse response = new GenericResponse();
        if (userRepo.existsById(request.getUserName())) {
            User user = userRepo.findById(request.getUserName()).get();
            if (BCrypt.checkpw(request.getPassword(), user.getUserPassword())) {
                response.setSuccess(true);
                response.setMessage("Successful!");
            } else {
                response.setSuccess(false);
                response.setMessage("Wrong password!");
            }
        } else {
            response.setSuccess(false);
            response.setMessage("Wrong user name!");
        }
        return response;
    }

    @PostMapping("/cancel-booking")
    private CancelBookingResponse cancelBooking(@RequestBody CancelBookingRequest request) {
        CancelBookingResponse response = new CancelBookingResponse();
        BookingId bookingId = new BookingId(request.getCompanyNameBranch().toUpperCase(), request.getDay().toUpperCase());
        if (userRepo.existsById(request.getUserName())) {
            User user = userRepo.findById(request.getUserName()).get();
            if (!compRepo.existsById(request.getCompanyNameBranch())) {
                response.setSuccess(false);
                response.setMessage("Company doesn't exist!");

            } else if (!bookRepo.existsById(bookingId)) {
                response.setSuccess(false);
                response.setMessage("Booking doesn't exist!");
            } else {
                Booking userBooking = FindBookingCompanyNameAndDay.findWithHour(user.getBookings(), request.getCompanyNameBranch().toUpperCase(),
                        request.getDay().toUpperCase(), request.getHour());
                Booking companyBooking = bookRepo.findById(bookingId).get();
                if (userBooking != null) {
                    long userReservationAmount = userBooking.getReservations().get(request.getHour()).longValue();
                    if (request.getCancelReservationAmount() > userReservationAmount) {
                        response.setSuccess(false);
                        response.setMessage("You exceed reservation capacity!");
                    } else {
                        long companyBookingNewCapacity = companyBooking.getReservations().get(request.getHour()).longValue() +
                                request.getCancelReservationAmount();
                        long userNewCapacity = userReservationAmount - request.getCancelReservationAmount();
                        if (userNewCapacity == 0) {
                            int bookingIndex = FindBookingCompanyNameAndDay.findIndex(user.getBookings(), request.getCompanyNameBranch().toUpperCase(),
                                    request.getDay().toUpperCase(), request.getHour());
                            user.getBookings().remove(bookingIndex);
                            companyBooking.getReservations().replace(request.getHour(), companyBookingNewCapacity);
                        } else {
                            companyBooking.getReservations().replace(request.getHour(), companyBookingNewCapacity);
                            userBooking.getReservations().replace(request.getHour(), userNewCapacity);
                        }
                        response.setSuccess(true);
                        response.setMessage("Successful!");
                        userRepo.save(user);
                        bookRepo.save(companyBooking);

                    }

                } else {
                    response.setSuccess(false);
                    response.setMessage("You don't have reservation like that!");
                }

            }
        }
        return response;
    }


    @PostMapping("/booking")
    public UserBookingResponse booking(@RequestBody UserBookingRequest request) {
        UserBookingResponse response = new UserBookingResponse();
        BookingId bookingId = new BookingId(request.getCompanyNameBranch().toUpperCase(), request.getDay().toUpperCase());
        List<Company> companies = compRepo.findCompaniesByRegexpName(request.getCompanyNameBranch());
        if (!companies.isEmpty()) {
            Company company = compRepo.findById(request.getCompanyNameBranch()).get();
            if (!userRepo.existsById(request.getUserName())) {
                response.setSuccess(false);
                response.setMessage("User doesn't exist!");
            } else {
                User user = userRepo.findById(request.getUserName()).get();
                if (!bookRepo.existsById(bookingId)) {
                    response.setSuccess(false);
                    response.setMessage("There is no booking like that!");
                } else {
                    Booking booking = bookRepo.findById(bookingId).get();
                    if (!ValidationChecks.isValidTime(request.getHour())) {
                        response.setSuccess(false);
                        response.setMessage("Hour is invalid!");
                    } else if (!booking.getReservations().containsKey(request.getHour())) {
                        response.setSuccess(false);
                        response.setMessage("There is no available space at that hour!");
                    } else {
                        long capacityAmount = booking.getReservations().get(request.getHour());

                        if (capacityAmount < request.getCapacity()) {
                            response.setSuccess(false);
                            response.setMessage("You pass the capacity!");
                        }
                        if (UserMethods.canUserBook(user, request)) {
                            Booking userBooking = new Booking();
                            UserMethods.userBookingSave(booking, userBooking, company, user, compRepo, userRepo, bookRepo, capacityAmount, request, response);
                        } else {
                            response.setSuccess(false);
                            response.setMessage("You have already have a reservation from that hour for that company!");
                        }
                    }
                }
            }
        } else {
            response.setSuccess(false);
            response.setMessage("Company doesn't exist!");
        }

        return response;
    }


    @PostMapping("/create")
    public CreateUserResponse createUser(@RequestBody CreateUserRequest request) {
        CreateUserResponse response = new CreateUserResponse();
        User user = new User();

        if (userRepo.existsById(request.getUserName())) {
            response.setSuccess(false);
            response.setMessage("User name is taken! Try another one.");
        } else {
            if (!ValidationChecks.isValidEmailAddress(request.getMail())) {
                response.setSuccess(false);
                response.setMessage("Invalid email address!");
            } else {
                UserMethods.userCreateMethod(user, userRepo, request, response);
            }

        }
        return response;

    }


}
