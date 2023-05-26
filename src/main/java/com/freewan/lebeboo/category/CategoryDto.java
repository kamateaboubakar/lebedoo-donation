package com.freewan.lebeboo.category;

import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link Category} entity
 */
@Data
public class CategoryDto implements Serializable {
    private final Long id;
    private final String label;
    private final String description;
    private String icon;
    private String iconUrl;
}