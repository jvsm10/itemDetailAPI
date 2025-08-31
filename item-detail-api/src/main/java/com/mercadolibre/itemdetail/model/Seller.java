package com.mercadolibre.itemdetail.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Seller {
    private String id;
    private String name;
    private Double rating;
    private String address;
}

