package com.rk.domain;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Glasses {
    private long id;
    private String name;
    private String collection;
    private String category;
    private String details;
    private double price;
    @EqualsAndHashCode.Exclude
    @Singular
    private List<Photo> photos;
}
