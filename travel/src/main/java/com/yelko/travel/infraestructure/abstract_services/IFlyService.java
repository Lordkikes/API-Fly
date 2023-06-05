package com.yelko.travel.infraestructure.abstract_services;

import com.yelko.travel.api.models.responses.FlyResponse;

import java.util.Set;

public interface IFlyService extends CatalogService<FlyResponse>{

    Set<FlyResponse> readByOriginDestiny(String origin, String destiny);
}
