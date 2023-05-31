package com.yelko.travel.domain.repositories;

import com.yelko.travel.domain.entities.CustomerEntity;
import com.yelko.travel.domain.entities.FlyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<CustomerEntity, String> {
}
