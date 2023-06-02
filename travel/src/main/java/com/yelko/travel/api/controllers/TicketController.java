package com.yelko.travel.api.controllers;

import com.yelko.travel.api.models.request.TicketRequest;
import com.yelko.travel.api.models.responses.TicketResponse;
import com.yelko.travel.infraestructure.abstract_services.ITicketService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/ticket")
@AllArgsConstructor
public class TicketController {

    private final ITicketService ticketService;

    @GetMapping("/byId/{id}")
    public ResponseEntity<TicketResponse> getTicket(@PathVariable UUID id){
        return ResponseEntity.ok(ticketService.read(id));
    }

    @PostMapping("/createTicket")
    public ResponseEntity<TicketResponse> post(@RequestBody TicketRequest request){
        return ResponseEntity.ok(ticketService.create(request));
    }

    @PutMapping("/updateTicket/{id}")
    public ResponseEntity<TicketResponse> updateTicket(@PathVariable UUID id, @RequestBody TicketRequest request){
        return ResponseEntity.ok(this.ticketService.update(request, id));

    }

    @DeleteMapping("/deleteTicket/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable UUID id){
        this.ticketService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
