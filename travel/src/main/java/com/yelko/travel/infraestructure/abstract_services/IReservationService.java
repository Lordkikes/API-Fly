package com.yelko.travel.infraestructure.abstract_services;

import com.yelko.travel.api.models.request.ReservationRequest;
import com.yelko.travel.api.models.responses.ReservationResponse;

import java.math.BigDecimal;
import java.util.UUID;

public interface IReservationService extends CrudService<ReservationRequest, ReservationResponse, UUID>{

    BigDecimal findPrice(Long hotelId);
}
