package com.rk.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Glasses {
    private long glassesId;
    private String name;
    private String collection;
    private String category;
    private String details;
    private double price;
    @EqualsAndHashCode.Exclude
    private List<Photo> photos;
}
