package com.mercadolibre.itemdetail.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    private String id;
    private String itemId;
    private String userId;
    private Integer rating;
    private String comment;
    private String date;
}

