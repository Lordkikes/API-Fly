package com.yelko.travel.infraestructure.abstract_services;

import com.yelko.travel.api.models.responses.HotelResponse;

import java.util.Set;

public interface IHotelService extends CatalogService<HotelResponse> {

    Set<HotelResponse> readByRating(Integer rating);
}
