package com.yelko.travel.api.controllers;

import com.yelko.travel.api.models.responses.HotelResponse;
import com.yelko.travel.infraestructure.abstract_services.IHotelService;
import com.yelko.travel.util.SortType;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping("/hotel")
@AllArgsConstructor
public class HotelController {

    private final IHotelService hotelService;

    @GetMapping("/getAll")
    private ResponseEntity<Page<HotelResponse>> getAllHotels(
            @RequestParam Integer page, @RequestParam Integer size,
            @RequestHeader(required = false) SortType sortType){
        if (Objects.isNull(sortType)) sortType = SortType.NONE;
        var response = this.hotelService.realAll(page, size, sortType);
        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    @GetMapping("/lessPrice")
    private ResponseEntity<Set<HotelResponse>> getLessPrice(@RequestParam BigDecimal price){

        var response = this.hotelService.readLessPrice(price);
        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    @GetMapping("/betweenPrice")
    private ResponseEntity<Set<HotelResponse>> getBetweenPrice(@RequestParam BigDecimal min, @RequestParam BigDecimal max){

        var response = this.hotelService.realBetweenPrices(min, max);
        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    @GetMapping("/rating")
    private ResponseEntity<Set<HotelResponse>> getHotelByRating(@RequestParam Integer rating){

        if(rating > 4) rating = 4;
        if(rating < 1) rating = 1;

        var response = this.hotelService.readByRating(rating);
        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }


}
