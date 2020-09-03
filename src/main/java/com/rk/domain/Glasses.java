package com.rk.domain;

import lombok.Data;

import java.util.List;

@Data
public class Glasses {
    private long glassesId;
    private String name;
    private String collection;
    private String category;
    private String details;
    private List<Photo> photos;
}
