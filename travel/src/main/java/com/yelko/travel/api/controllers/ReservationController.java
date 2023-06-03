package com.yelko.travel.api.controllers;

import com.yelko.travel.api.models.request.ReservationRequest;
import com.yelko.travel.api.models.request.TicketRequest;
import com.yelko.travel.api.models.responses.ReservationResponse;
import com.yelko.travel.api.models.responses.TicketResponse;
import com.yelko.travel.infraestructure.abstract_services.IReservationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/reservation")
@AllArgsConstructor
public class ReservationController {

    private final IReservationService reservationService;

    @GetMapping("/byId/{id}")
    public ResponseEntity<ReservationResponse> getReservationById(@PathVariable UUID id){
        return ResponseEntity.ok(reservationService.read(id));
    }

    @GetMapping("/byPrice")
    public ResponseEntity<Map<String, BigDecimal>> getReservationPrice(@RequestParam Long hotelId){
        return ResponseEntity.ok(Collections.singletonMap("ticketPrice", this.reservationService.findPrice(hotelId)));
    }

    @PostMapping("/createReservation")
    public ResponseEntity<ReservationResponse> createReservation(@RequestBody ReservationRequest request){
        return ResponseEntity.ok(reservationService.create(request));
    }

    @PutMapping("/updateReservation/{id}")
    public ResponseEntity<ReservationResponse> updateReservation(@PathVariable UUID id, @RequestBody ReservationRequest request){
        return ResponseEntity.ok(this.reservationService.update(request, id));

    }

    @DeleteMapping("/deleteReservation/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable UUID id){
        this.reservationService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
