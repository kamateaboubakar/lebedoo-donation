package com.freewan.lebeboo.campaign;

import com.freewan.lebeboo.category.CategoryDto;
import com.freewan.lebeboo.organization.OrganizationDto;
import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link Campaign} entity
 */
@Data
public class CampaignDto implements Serializable {
    private final Long id;
    private final CategoryDto category;
    private final OrganizationDto organization;
    private final String label;
    private final Double amount;
    private final Double collectedAmount;
    private final String description;
    private final String createdAt;
    private final String deadline;
    private final String image;
    private final String imageUrl;
    private final String status;
    private final int donorCount;
}