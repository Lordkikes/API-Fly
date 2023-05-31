package com.yelko.travel.domain.repositories;

import com.yelko.travel.domain.entities.FlyEntity;
import com.yelko.travel.domain.entities.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface TicketRepository extends CrudRepository<TicketEntity, UUID> {
}
