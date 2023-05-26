package com.freewan.lebeboo.donation;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DonationRequest {
    @NotBlank
    private String customerAccountId;
    @NotBlank
    private Double amount;
}
