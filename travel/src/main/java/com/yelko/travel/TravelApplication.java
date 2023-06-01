package com.yelko.travel;

import com.yelko.travel.domain.entities.HotelEntity;
import com.yelko.travel.domain.entities.ReservationEntity;
import com.yelko.travel.domain.entities.TicketEntity;
import com.yelko.travel.domain.entities.TourEntity;
import com.yelko.travel.domain.repositories.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@SpringBootApplication
@Slf4j
public class TravelApplication implements CommandLineRunner {

    private final HotelRepository hotelRepository;
    private final FlyRepository flyRepository;
    private final TicketRepository ticketRepository;
    private final ReservationRepository reservationRepository;
    private final TourRepository tourRepository;
    private final CustomerRepository customerRepository;

    public TravelApplication(HotelRepository hotelRepository, FlyRepository flyRepository,
                             TicketRepository ticketRepository, ReservationRepository reservationRepository,
                             TourRepository tourRepository, CustomerRepository customerRepository) {
        this.hotelRepository = hotelRepository;
        this.flyRepository = flyRepository;
        this.ticketRepository = ticketRepository;
        this.reservationRepository = reservationRepository;
        this.tourRepository = tourRepository;
        this.customerRepository = customerRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(TravelApplication.class, args);
    }

	@Override
	public void run(String... args) throws Exception {
		/*var fly = flyRepository.findById(15L).get();
		var hotel = hotelRepository.findById(7L).get();
        var ticket = ticketRepository.findById(UUID.fromString("22345678-1234-5678-3235-567812345678")).get();
        var reservation = reservationRepository.findById(UUID.fromString("22345678-1234-5678-1234-567812345678")).get();
        var customer = customerRepository.findById("VIKI771012HMCRG093").get();

		log.info(String.valueOf(fly));
		log.info(String.valueOf(hotel));
        log.info(String.valueOf(ticket));
        log.info(String.valueOf(reservation));
        log.info(String.valueOf(customer));

		 */

       // this.flyRepository.selectLessPrice(BigDecimal.valueOf(20)).forEach(f -> System.out.println(f));
       // this.flyRepository.selectBetwednPrice(BigDecimal.valueOf(10), BigDecimal.valueOf(15)).forEach(f -> System.out.println(f));
        //this.flyRepository.selectOriginDestiny("Grecia", "Mexico").forEach(System.out::println);

       // var fly = flyRepository.findByTicketId(UUID.fromString("12345678-1234-5678-2236-567812345678"));
        //System.out.println(fly);

       // hotelRepository.findByPriceLessThan(BigDecimal.valueOf(70)).forEach(System.out::println);
      //  hotelRepository.findByPriceIsBetween(BigDecimal.valueOf(70), BigDecimal.valueOf(150)).forEach(System.out::println);

        //hotelRepository.findByRatingGreaterThan(3).forEach(System.out::println);

        // var hotel = hotelRepository.findByReservationsId(UUID.fromString("12345678-1234-5678-1234-567812345678"));
        //System.out.println(hotel);

        var customer = customerRepository.findById("VIKI771012HMCRG093").orElseThrow();
        log.info("Client name: " + customer.getFullName());

        var fly = flyRepository.findById(11L).orElseThrow();
        log.info("Fly: " + fly.getOriginName() + " - " + fly.getDestinyName());

        var hotel = hotelRepository.findById(3L).orElseThrow();
        log.info("Hotel name: " + hotel.getName());

        var tour = TourEntity.builder()
                .customer(customer)
                .build();

        var ticket = TicketEntity.builder()
                .id(UUID.randomUUID())
                .price(fly.getPrice().multiply(BigDecimal.TEN))
                .arrivalDate(LocalDate.now())
                .departureDate(LocalDate.now())
                .purchaseDate(LocalDate.now())
                .customer(customer)
                .tour(tour)
                .fly(fly)
                .build();

        var reservation = ReservationEntity.builder()
                .id(UUID.randomUUID())
                .dateTimeReservation(LocalDateTime.now())
                .dateEnd(LocalDate.now().plusDays(2))
                .dateStart(LocalDate.now().plusDays(1))
                .hotel(hotel)
                .customer(customer)
                .tour(tour)
                .totalDays(1)
                .price(hotel.getPrice().multiply(BigDecimal.TEN))
                .build();

        System.out.println("-----SAVING------");

        tour.addReservation(reservation);
        tour.updateReservation();
        tour.addTicket(ticket);
        tour.updateTickets();

        this.tourRepository.save(tour);


    }
}
