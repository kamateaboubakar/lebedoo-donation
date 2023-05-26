package com.freewan.lebeboo.donation;

import com.freewan.lebeboo.account.AccountResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * A DTO for the {@link Donation} entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DonationDto implements Serializable {
    private Long id;
    private Long campaignId;
    private AccountResponse donorDetails;
    private Double amount;
    private String donationMadeAt;
}