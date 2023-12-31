package com.hotel.pethotel;

import com.hotel.pethotel.Reservation.ReservationModel;
import com.hotel.pethotel.Reservation.ReservationStatus;
import com.hotel.pethotel.Rooms.RoomModel;
import com.hotel.pethotel.model.*;
import com.hotel.pethotel.repository.ReservationRepository;
import com.hotel.pethotel.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@SpringBootApplication
@RequiredArgsConstructor
public class PethotelApplication implements ApplicationRunner {
	private final RoomRepository roomRepository;
	private final ReservationRepository reservationRepository;

	public static void main(String[] args) {
		SpringApplication.run(PethotelApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) {
		RoomModel room1 = new RoomModel();
		room1.setName("Koci basic");
		room1.setActive(true);
		room1.setDescription("Przytulny koci pokoik z podstawowym wyposażeniem. Drapak, miseczki, hamaczek.");
		room1.setCostPerNight(BigDecimal.ONE);
		room1.setStandard(Standard.BASIC);
		room1.setAllowedAnimalSizes(Set.of(AnimalSize.SMALL, AnimalSize.MEDIUM));
		room1.setAllowedAnimalTypes(Set.of(Type.CAT));

		roomRepository.save(room1);

		RoomModel room2 = new RoomModel();
		room2.setName("Psi premium - niebieski");
		room2.setActive(true);
		room2.setDescription("Przytulny pokój dla małych i średnich piesków. Zawiera w sobie gryzaki, maty węchowe i pluszaki. Pokój jest w pełni klimatyzowany.");
		room2.setCostPerNight(BigDecimal.ONE);
		room2.setStandard(Standard.PREMIUM);
		room2.setAllowedAnimalSizes(Set.of(AnimalSize.SMALL, AnimalSize.MEDIUM));
		room2.setAllowedAnimalTypes(Set.of(Type.DOG));
		room2.setCostPerNight(BigDecimal.valueOf(100));
		roomRepository.save(room2);

		RoomModel room3 = new RoomModel();
		room3.setName("Psi premium - żółty");
		room3.setActive(true);
		room3.setDescription("Przytulny pokój dla dużych i średnich psów. Zawiera w sobie gryzaki, maty węchowe i pluszaki. Pokój jest w pełni klimatyzowany.");
		room3.setCostPerNight(BigDecimal.ONE);
		room3.setStandard(Standard.PREMIUM);
		room3.setAllowedAnimalSizes(Set.of(AnimalSize.MEDIUM, AnimalSize.LARGE));
		room3.setAllowedAnimalTypes(Set.of(Type.DOG));
		room3.setCostPerNight(BigDecimal.valueOf(150.00));
		roomRepository.save(room3);

//		RoomModel room4 = new RoomModel();
//		room4.setName("test-pokoj-4 trwajaca rezerwacja");
//		room4.setActive(true);
//		room4.setCostPerNight(BigDecimal.ONE);
//		room4.setStandard(Standard.PREMIUM);
//		room4.setAllowedAnimalSizes(Set.of(AnimalSize.SMALL, AnimalSize.MEDIUM, AnimalSize.LARGE));
//		room4.setAllowedAnimalTypes(Set.of(Type.DOG));
//		room4.setCostPerNight(BigDecimal.valueOf(250.50));
//		room4.setDescription("miły pokoik");
//		roomRepository.save(room4);

		ReservationModel reservation1 = new ReservationModel();
		reservation1.setRoom(room2);
		reservation1.setStartDate(LocalDate.of(2023,12,9));
		reservation1.setEndDate(LocalDate.of(2023,12,15));
		reservation1.setReservationStatus(ReservationStatus.CANCELLED);
		reservationRepository.save(reservation1);
//przyszla rezerwacja
		ReservationModel reservation2 = new ReservationModel();
		reservation2.setRoom(room2);
		reservation2.setStartDate(LocalDate.of(2023,12,11));
		reservation2.setEndDate(LocalDate.of(2023,12,15));
		reservation2.setReservationStatus(ReservationStatus.CONFIRMED);
		reservationRepository.save(reservation2);

//trwajaca rezerwacja
		ReservationModel reservation3 = new ReservationModel();
		reservation3.setRoom(room3);
		reservation3.setStartDate(LocalDate.of(2023,12,9));
		reservation3.setEndDate(LocalDate.of(2023,12,19));
		reservation3.setReservationStatus(ReservationStatus.PENDING);
		reservationRepository.save(reservation3);

//		RoomModel room5 = new RoomModel();
//		room5.setName("test-pokoj-5 przeszla rezerwacja");
//		room5.setActive(true);
//		room5.setCostPerNight(BigDecimal.ONE);
//		room5.setStandard(Standard.PREMIUM);
//		room5.setAllowedAnimalSizes(Set.of(AnimalSize.SMALL, AnimalSize.MEDIUM, AnimalSize.LARGE));
//		room5.setAllowedAnimalTypes(Set.of(Type.DOG));
//		room5.setCostPerNight(BigDecimal.valueOf(250.50));
//		room5.setDescription("miły pokoik");
//		roomRepository.save(room5);
////przeszla rezerwacja
//		ReservationModel reservation4 = new ReservationModel();
//		reservation4.setRoom(room5);
//		reservation4.setStartDate(LocalDate.of(2023,12,1));
//		reservation4.setEndDate(LocalDate.of(2023,12,5));
//		reservation4.setReservationStatus(ReservationStatus.CONFIRMED);
//		reservationRepository.save(reservation4);

//		var room1 = new RoomModel();
//		room1.setName("test-pokoj-1");
//		room1.setActive(true);
//		room1.setCostPerNight(BigDecimal.ONE);
//		room1.setStandard(Standard.BASIC);
//		room1.setAllowedAnimalSizes(Set.of(AnimalSize.SMALL, AnimalSize.MEDIUM));
//		room1.setAllowedAnimalTypes(Set.of(Type.DOG));
//
//		roomRepository.save(room1);
//
//		var room2 = new RoomModel();
//		room2.setName("test-pokoj-2");
//		room2.setActive(true);
//		room2.setCostPerNight(BigDecimal.ONE);
//		room2.setStandard(Standard.PREMIUM);
//		room2.setAllowedAnimalSizes(Set.of(AnimalSize.SMALL, AnimalSize.MEDIUM, AnimalSize.LARGE));
//		room2.setAllowedAnimalTypes(Set.of(Type.DOG));
//		roomRepository.save(room2);
//
//		var reservation = new ReservationModel();
//		reservation.setRoom(room2);
//		reservation.setStartDate(LocalDate.of(2022,01,01));
//		reservation.setEndDate(LocalDate.of(2023,01,01));
//		reservation.setReservationStatus(ReservationStatus.CANCELED);
//
//		reservationRepository.save(reservation);
	}
}
