package com.yelko.travel.infraestructure.services;

import com.yelko.travel.api.models.request.ReservationRequest;
import com.yelko.travel.api.models.responses.HotelResponse;
import com.yelko.travel.api.models.responses.ReservationResponse;
import com.yelko.travel.domain.entities.ReservationEntity;
import com.yelko.travel.domain.repositories.*;
import com.yelko.travel.infraestructure.abstract_services.IReservationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.yelko.travel.util.Constants.CHARGER_PRICE_PERCENTAGE_20;


@Transactional
@Service
@Slf4j
@AllArgsConstructor
public class ReservationService implements IReservationService {


    private final HotelRepository hotelRepository;
    private final CustomerRepository customerRepository;
    private final ReservationRepository reservationRepository;
    @Override
    public ReservationResponse create(ReservationRequest request) {

        var hotel = this.hotelRepository.findById(request.getIdHotel()).orElseThrow();
        var customer = customerRepository.findById(request.getIdClient()).orElseThrow();

        var reservationToPersist = ReservationEntity.builder()
                .id(UUID.randomUUID())
                .hotel(hotel)
                .customer(customer)
                .totalDays(request.getTotalDays())
                .dateTimeReservation(LocalDateTime.now())
                .dateStart(LocalDate.now())
                .dateEnd(LocalDate.now())
                .price(hotel.getPrice().add(hotel.getPrice().multiply(CHARGER_PRICE_PERCENTAGE_20)))
                .build();

        var reservationPersisted = reservationRepository.save(reservationToPersist);
        return this.entityToResponse(reservationPersisted);

    }

    @Override
    public ReservationResponse read(UUID id) {
        var reservationFromDB = this.reservationRepository.findById(id).orElseThrow();
        return this.entityToResponse(reservationFromDB);

    }

    @Override
    public ReservationResponse update(ReservationRequest request, UUID id) {

        var hotel = this.hotelRepository.findById(request.getIdHotel()).orElseThrow();
        var reservationToUpdate = this.reservationRepository.findById(id).orElseThrow();

        reservationToUpdate.setHotel(hotel);
        reservationToUpdate.setTotalDays(request.getTotalDays());
        reservationToUpdate.setDateTimeReservation(LocalDateTime.now());
        reservationToUpdate.setDateStart(LocalDate.now());
        reservationToUpdate.setDateEnd(LocalDate.now().plusDays(request.getTotalDays()));
        reservationToUpdate.setPrice(hotel.getPrice().add(hotel.getPrice().multiply(CHARGER_PRICE_PERCENTAGE_20)));

        var reservationUpdated = this.reservationRepository.save(reservationToUpdate);

        log.info("Reservation Updated with ID {}", reservationUpdated.getId());

        return this.entityToResponse(reservationUpdated);
    }

    @Override
    public void delete(UUID id) {
        var reservationToDelete = reservationRepository.findById(id).orElseThrow();
        this.reservationRepository.delete(reservationToDelete);
    }

    @Override
    public BigDecimal findPrice(Long hotelId) {
        var hotel = hotelRepository.findById(hotelId).orElseThrow();
        return hotel.getPrice().add(hotel.getPrice().multiply(CHARGER_PRICE_PERCENTAGE_20));
    }

    private ReservationResponse entityToResponse(ReservationEntity entity) {

        var response = new ReservationResponse();
        BeanUtils.copyProperties(entity, response);

        var hotelResponse = new HotelResponse();
        BeanUtils.copyProperties(entity.getHotel(), hotelResponse);

        response.setHotel(hotelResponse);
        return response;

    }
}
