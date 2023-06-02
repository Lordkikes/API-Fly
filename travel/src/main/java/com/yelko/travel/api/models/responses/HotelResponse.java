package com.yelko.travel.api.models.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HotelResponse {

    private Long id;
    private String name;
    private String adddress;
    private Integer rating;
    private BigDecimal price;
}
