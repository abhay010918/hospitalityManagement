package com.hotel_management.service;

import com.hotel_management.entity.Property;
import com.hotel_management.entity.Room;
import com.hotel_management.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<Room> findByType(String type, Property property, LocalDate fromDate, LocalDate toDate){
         return roomRepository.findByTypeAndProperty(type,property,fromDate,toDate);
    }
}
