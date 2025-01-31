package com.hotel_management.repository;

import com.hotel_management.entity.Property;
import com.hotel_management.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query("SELECT r FROM Room r WHERE r.type = :type AND r.property = :property AND r.date BETWEEN :startDate AND :endDate")
    List<Room> findByTypeAndProperty(
            @Param("type") String type,
            @Param("property") Property property,
            @Param("startDate") LocalDate fromDate,
            @Param("endDate") LocalDate toDate
    );
}