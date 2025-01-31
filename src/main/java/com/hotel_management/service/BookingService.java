package com.hotel_management.service;

import com.hotel_management.entity.Bookings;
import com.hotel_management.repository.BookingsRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class BookingService {
    private final BookingsRepository bookingsRepository;

    public BookingService(BookingsRepository bookingsRepository) {
        this.bookingsRepository = bookingsRepository;
    }

    public Bookings saveBooking(@RequestBody Bookings bookings){
        return bookingsRepository.save(bookings);
    }
}
