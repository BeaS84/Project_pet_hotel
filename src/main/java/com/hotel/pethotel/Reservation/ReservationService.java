package com.hotel.pethotel.Reservation;

import com.hotel.pethotel.Exceptions.ReservationErrorException;
import com.hotel.pethotel.Rooms.RoomModel;
import com.hotel.pethotel.model.AnimalModel;
import com.hotel.pethotel.model.ClientModel;
import com.hotel.pethotel.repository.AnimalRepository;
import com.hotel.pethotel.repository.RoomRepository;
import com.hotel.pethotel.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;



import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;
    private final AnimalRepository animalRepository;

    public ReservationModel createReservation(ClientModel client, AnimalModel animal, RoomModel room, LocalDate startDate, LocalDate endDate) {


        boolean isRoomAvailable = roomRepository.isRoomAvailable(room.getId(), startDate, endDate);
        boolean isReservationCancelled = reservationRepository.isExistsByRoomAndReservationStatus(room.getId(), List.of(ReservationStatus.CANCELLED), startDate, endDate);

        if (isReservationCancelled) {
            System.out.println("Istnieje anulowana rezerwacja dla tego klienta, zwierzaka i pokoju. Tworzę nową rezerwację.");
        }

        if (isRoomAvailable || isReservationCancelled) {

            ReservationModel reservation = new ReservationModel();
            reservation.setClient(client);
            reservation.setAnimal(animal);
            reservation.setRoom(room);
            reservation.setStartDate(startDate);
            reservation.setEndDate(endDate);
            reservation.setPrice(calculateReservationPrice(startDate, endDate, room.getCostPerNight()));
            reservation.setReservationStatus(ReservationStatus.PENDING);
            UUID uuid = UUID.randomUUID();
            String uuidAsString = uuid.toString();
            reservation.setReservationNumber(uuidAsString);
            
            return reservationRepository.save(reservation);
        } else {
            System.out.println("Pokój jest już zajęty.");
            throw new ReservationErrorException("Pokój jest zajęty. Zmień na coś innego.");
        }
    }

    public BigDecimal calculateReservationPrice(LocalDate startDate, LocalDate endDate, BigDecimal costPerNight) {
        long numberOfNights = ChronoUnit.DAYS.between(startDate, endDate);
        return costPerNight.multiply(BigDecimal.valueOf(numberOfNights));
    }

    public List<ReservationModel> getAllReservationList() {
        return reservationRepository.findAll();
    }

    public List<ReservationModel> getReservationsByStatus(String status) {
        return reservationRepository.findByReservationStatus(ReservationStatus.valueOf(status));
    }


    public void deleteReservation(Long id) {

        if (Objects.requireNonNull(reservationRepository.findById(id).orElse(null))
                .getReservationStatus().equals(ReservationStatus.CANCELLED)) {
            reservationRepository.deleteById(id);
        } else {
            throw new ReservationErrorException("Możesz anulować tylko rezerwacje ze statusem CANCELLED.");
       }
    }

    public ReservationModel getReservationById(Long id) {
        return reservationRepository.getById(id);
    }

    public void saveReservation(ReservationModel reservation) {
        reservationRepository.save(reservation);
    }

    public List<ReservationModel> getActiveReservationsForAnimal(AnimalModel animal) {
        LocalDate currentDate = LocalDate.now();
        Set<ReservationStatus> activeStatuses = Set.of(ReservationStatus.PENDING, ReservationStatus.CONFIRMED);
        return reservationRepository.findActiveReservationsForAnimal(
                animal.getId(),
                currentDate,
                activeStatuses
        );
    }
    public void deleteAnimalWithReservations(Long animalId) {
        AnimalModel animalModel = animalRepository.findById(animalId).orElse(null);
        if (animalModel != null) {
            List<ReservationModel> reservations = reservationRepository.findByAnimalId(animalId);
            reservationRepository.deleteAll(reservations);
            animalRepository.deleteById(animalId);
        } else {
            throw new ReservationErrorException("Zwierzak o podanym ID nie istnieje.");
        }
    }



//    public List<ReservationModel> findReservationsForAnimalByStatus(Long animalId, ReservationStatus status) {
//        return reservationRepository.findReservationsForAnimalByStatus(animalId, status);
//    }
//    public void confirmDeleteAnimal(Long animalId) {
//        List<ReservationModel> pendingReservations = reservationRepository.findPendingReservationsForAnimal(animalId);
//        if (!pendingReservations.isEmpty()) {
//            throw new ReservationErrorException("Nie możesz usunąć zwierzaka, ponieważ istnieją aktywne rezerwacje");
//        }
//    }
}



