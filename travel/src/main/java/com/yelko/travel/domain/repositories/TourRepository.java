package com.yelko.travel.domain.repositories;

import com.yelko.travel.domain.entities.FlyEntity;
import com.yelko.travel.domain.entities.TourEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface TourRepository extends CrudRepository<TourEntity, Long> {
}
