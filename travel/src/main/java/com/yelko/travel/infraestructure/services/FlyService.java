package com.yelko.travel.infraestructure.services;

import com.yelko.travel.api.models.responses.FlyResponse;
import com.yelko.travel.domain.entities.FlyEntity;
import com.yelko.travel.domain.repositories.CustomerRepository;
import com.yelko.travel.domain.repositories.FlyRepository;
import com.yelko.travel.domain.repositories.TicketRepository;
import com.yelko.travel.infraestructure.abstract_services.IFlyService;
import com.yelko.travel.util.SortType;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class FlyService implements IFlyService {

    private final FlyRepository flyRepository;
    private final CustomerRepository customerRepository;
    private final TicketRepository ticketRepository;
    @Override
    public Page<FlyResponse> realAll(Integer page, Integer size, SortType sortType) {

        PageRequest pageRequest = null;
        switch (sortType){
            case NONE -> pageRequest = PageRequest.of(page, size);
            case LOWER -> pageRequest = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).ascending());
            case UPPER -> pageRequest = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).descending());
        }
        return this.flyRepository.findAll(pageRequest).map(this::entityToResponse);
    }

    @Override
    public Set<FlyResponse> readLessPrice(BigDecimal price) {

       return this.flyRepository.selectLessPrice(price)
               .stream()
               .map(this::entityToResponse)
               .collect(Collectors.toSet());

    }

    @Override
    public Set<FlyResponse> realBetweenPrices(BigDecimal min, BigDecimal max) {
        return this.flyRepository.selectBetwednPrice(min, max)
                .stream()
                .map(this::entityToResponse)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<FlyResponse> readByOriginDestiny(String origin, String destiny) {
        return this.flyRepository.selectOriginDestiny(origin, destiny)
                .stream()
                .map(this::entityToResponse)
                .collect(Collectors.toSet());
    }

    private FlyResponse entityToResponse(FlyEntity entity){
        FlyResponse response = new FlyResponse();
        BeanUtils.copyProperties(entity, response);
        return response;
    }
}
