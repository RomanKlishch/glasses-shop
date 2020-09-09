package com.rk.dto;

import com.rk.domain.Glasses;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
public class CatalogAndMessage {
    String message;
    List<Glasses> catalog;
}
