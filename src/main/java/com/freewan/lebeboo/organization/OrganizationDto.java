package com.freewan.lebeboo.organization;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link Organization} entity
 */
@Data
public class OrganizationDto implements Serializable {
    private final Long id;
    private final String customerAccountId;
    @NotBlank
    private final String name;
    @NotBlank
    private final String description;
    private final String email;
    private final String address;
    @NotBlank
    private final String phoneNumber;
    private final String createdAt;
    private final boolean verified;
    private final String logo;
    private final String logoUrl;
}