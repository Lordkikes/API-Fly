package com.yelko.travel.infraestructure.services;

import com.yelko.travel.api.models.request.TicketRequest;
import com.yelko.travel.api.models.responses.FlyResponse;
import com.yelko.travel.api.models.responses.TicketResponse;
import com.yelko.travel.domain.entities.TicketEntity;
import com.yelko.travel.domain.repositories.CustomerRepository;
import com.yelko.travel.domain.repositories.FlyRepository;
import com.yelko.travel.domain.repositories.TicketRepository;
import com.yelko.travel.infraestructure.abstract_services.ITicketService;
import com.yelko.travel.util.BestTravelUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static com.yelko.travel.util.Constants.CHARGER_PRICE_PERCENTAGE_25;

@Transactional
@Service
@Slf4j
@AllArgsConstructor
public class TicketService implements ITicketService {

    private final FlyRepository flyRepository;
    private final CustomerRepository customerRepository;
    private final TicketRepository ticketRepository;

    @Override
    public TicketResponse create(TicketRequest request) {

        var fly = flyRepository.findById(request.getIdFly()).orElseThrow();
        var customer = customerRepository.findById(request.getIdClient()).orElseThrow();

        var ticketToPersist = TicketEntity.builder()
                .id(UUID.randomUUID())
                .fly(fly)
                .customer(customer)
                .price(fly.getPrice().add(fly.getPrice().multiply(CHARGER_PRICE_PERCENTAGE_25)))
                .purchaseDate(LocalDate.now())
                .arrivalDate(BestTravelUtil.getRandomLatter())
                .departureDate(BestTravelUtil.getRandomSoon())
                .build();

        var ticketPersisted = this.ticketRepository.save(ticketToPersist);
        log.info("Ticket saved with ID: {}", ticketPersisted.getId());

        return this.entityToResponse(ticketPersisted);
    }

    @Override
    public TicketResponse read(UUID id) {

        var ticketFromDB = this.ticketRepository.findById(id).orElseThrow();
        return this.entityToResponse(ticketFromDB);
    }

    @Override
    public TicketResponse update(TicketRequest request, UUID id) {
        var ticketToUpdate = ticketRepository.findById(id).orElseThrow();

        var fly = flyRepository.findById(request.getIdFly()).orElseThrow();

        ticketToUpdate.setFly(fly);
        ticketToUpdate.setPrice(fly.getPrice().add(fly.getPrice().multiply(CHARGER_PRICE_PERCENTAGE_25)));
        ticketToUpdate.setArrivalDate(BestTravelUtil.getRandomLatter());
        ticketToUpdate.setDepartureDate(BestTravelUtil.getRandomSoon());

        var ticketUpdated = this.ticketRepository.save(ticketToUpdate);

        log.info("Ticket Updated with ID {}", ticketUpdated.getId());

        return this.entityToResponse(ticketUpdated);
    }

    @Override
    public void delete(UUID id) {

        var ticketToDelete = ticketRepository.findById(id).orElseThrow();
        this.ticketRepository.delete(ticketToDelete);

    }

    private TicketResponse entityToResponse(TicketEntity entity) {

        var response = new TicketResponse();
        BeanUtils.copyProperties(entity, response);

        var flyResponse = new FlyResponse();
        BeanUtils.copyProperties(entity.getFly(), flyResponse);

        response.setFly(flyResponse);
        return response;

    }

    @Override
    public BigDecimal findPrice(Long flyId) {

        var fly = this.flyRepository.findById(flyId).orElseThrow();
        return fly.getPrice().add(fly.getPrice().multiply(CHARGER_PRICE_PERCENTAGE_25));
    }
}
