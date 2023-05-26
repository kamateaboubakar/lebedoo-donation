package com.freewan.lebeboo.campaign;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link Campaign} entity
 */
@Data
public class CampaignRequest implements Serializable {
    private final Long id;
    @NotNull
    private final Long organizationId;
    @NotNull
    private final Long categoryId;
    @NotBlank
    private final String label;
    @NotBlank
    private final Double amount;
    @NotBlank
    private final String description;
    private final String deadline;
    private final String image;
}