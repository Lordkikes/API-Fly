package com.yelko.travel;

import com.yelko.travel.domain.repositories.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
		var fly = flyRepository.findById(15L).get();
		var hotel = hotelRepository.findById(7L).get();
        var ticket = ticketRepository.findById(UUID.fromString("22345678-1234-5678-3235-567812345678"));
        var reservation = reservationRepository.findById(UUID.fromString("22345678-1234-5678-1234-567812345678"));
        var customer = customerRepository.findById("VIKI771012HMCRG093");

		log.info(String.valueOf(fly));
		log.info(String.valueOf(hotel));
        log.info(String.valueOf(ticket));
        log.info(String.valueOf(reservation));
        log.info(String.valueOf(customer));
	}
}
