package com.hotel_management.controller;

import com.hotel_management.entity.Bookings;
import com.hotel_management.entity.Property;
import com.hotel_management.entity.Room;
import com.hotel_management.repository.BookingsRepository;
import com.hotel_management.service.BookingService;
import com.hotel_management.service.PdfService;
import com.hotel_management.service.PropertyService;
import com.hotel_management.service.RoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pdf")
public class BookingController {

    private final PdfService pdfService;
    private final PropertyService propertyService;
    private final BookingService bookingService;
    private final BookingsRepository bookingsRepository;
    private final RoomService roomService;

    public BookingController(PdfService pdfService, PropertyService propertyService, BookingService bookingService,
                             BookingsRepository bookingsRepository, RoomService roomService) {
        this.pdfService = pdfService;
        this.propertyService = propertyService;
        this.bookingService = bookingService;
        this.bookingsRepository = bookingsRepository;
        this.roomService = roomService;
    }

    @GetMapping("/generate")
    public ResponseEntity<?> generatePdf(
            @RequestParam long propertyId,
            @RequestBody Bookings bookings,
            @RequestParam String type
    ) {
        // Fetch property details
        Property property = propertyService.findById(propertyId);
        if (property == null) {
            return ResponseEntity.badRequest().body("Property not found!");
        }

        // Fetch available rooms
        List<Room> rooms = roomService.findByType(type, property, bookings.getFromDate(), bookings.getToDate());

        if (rooms.isEmpty()) {
            return ResponseEntity.badRequest().body("No rooms found for the given criteria!");
        }

        for (Room room : rooms) {
            if (room.getCount() == 0) {
                return ResponseEntity.badRequest().body("Sorry, no rooms available on " + room.getDate());
            }
        }

        //

        for (Room room : rooms) {
            double totalPrice = room.getPrice() * rooms.size() - 1;

            }


        // Save booking
        Bookings booking = bookingService.saveBooking(bookings);
        if (booking != null) {
            // Update room count
            for (Room room : rooms) {
                room.setCount(room.getCount() - 1);
                bookingsRepository.save(booking);
            }
        }

        // Generate and save PDF with property details
        String message = pdfService.generateAndSavePdf(property, bookings);
        return ResponseEntity.ok(message);
    }

}
