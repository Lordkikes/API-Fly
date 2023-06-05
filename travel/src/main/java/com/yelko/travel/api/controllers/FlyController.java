package com.yelko.travel.api.controllers;

import com.yelko.travel.api.models.responses.FlyResponse;
import com.yelko.travel.infraestructure.abstract_services.IFlyService;
import com.yelko.travel.util.SortType;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping("/fly")
@AllArgsConstructor
public class FlyController {

    private final IFlyService flyService;

    @GetMapping("/getAll")
    private ResponseEntity<Page<FlyResponse>> getAllFlys(
            @RequestParam Integer page, @RequestParam Integer size,
            @RequestHeader(required = false) SortType sortType){
        if (Objects.isNull(sortType)) sortType = SortType.NONE;
        var response = this.flyService.realAll(page, size, sortType);
        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    @GetMapping("/lessPrice")
    private ResponseEntity<Set<FlyResponse>> getLessPrice(@RequestParam BigDecimal price){

        var response = this.flyService.readLessPrice(price);
        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    @GetMapping("/betweenPrice")
    private ResponseEntity<Set<FlyResponse>> getBetweenPrice(@RequestParam BigDecimal min, @RequestParam BigDecimal max){

        var response = this.flyService.realBetweenPrices(min, max);
        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    @GetMapping("/byOriginDestiny")
    private ResponseEntity<Set<FlyResponse>> getOriginDestiny(@RequestParam String origin, @RequestParam String destiny){

        var response = this.flyService.readByOriginDestiny(origin, destiny);
        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

}
