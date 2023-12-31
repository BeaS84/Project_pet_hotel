package com.hotel.pethotel.repository;

import com.hotel.pethotel.Reservation.ReservationStatus;
import com.hotel.pethotel.Rooms.RoomModel;
import com.hotel.pethotel.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<RoomModel, Long> {
    @Query("""
            SELECT room FROM RoomModel room
            INNER JOIN AnimalModel animal
                ON animal.id = :animalId
            INNER JOIN room.allowedAnimalTypes allowedAnimalType
                ON animal.type = allowedAnimalType
            INNER JOIN room.allowedAnimalSizes allowedAnimalSize
                ON animal.size = allowedAnimalSize
            WHERE room.active
            AND room.standard = :standard
            AND NOT EXISTS (
                SELECT reservation FROM ReservationModel reservation
                WHERE reservation.room.id = room.id
                AND reservation.startDate <= :reservationEndDate
                AND reservation.endDate >= :reservationStartDate
                AND NOT reservation.reservationStatus= :reservationStatus
            )         
        """)
    List<RoomModel> findAvailableRooms(
            String animalId,
            Standard standard,
            LocalDate reservationStartDate,
            LocalDate reservationEndDate,
            ReservationStatus reservationStatus
            );

    @Query("""
            SELECT
                CASE WHEN COUNT(*) > 0 THEN false
                ELSE true
            END
            FROM ReservationModel reservation
            WHERE reservation.room.id = :roomId
            AND reservation.startDate <= :reservationEndDate
            AND reservation.endDate >= :reservationStartDate
            
    """)
    boolean isRoomAvailable(
            Long roomId,
            LocalDate reservationStartDate,
            LocalDate reservationEndDate
    );



}
