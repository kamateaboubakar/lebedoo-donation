package com.freewan.lebeboo.donation;

import com.freewan.lebeboo.account.AccountResponse;
import com.freewan.lebeboo.account.AccountClient;
import lombok.AllArgsConstructor;
import org.mapstruct.ObjectFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@AllArgsConstructor
public class CustomerDetailsResolver {
    private final AccountClient accountClient;

    @ObjectFactory
    DonationDto resolve(Donation donation) {
        DonationDto dto = new DonationDto();
        AccountResponse customer = new AccountResponse();
        if (donation.getCustomerAccountId() != null) {
            try {
                customer = accountClient.getAccountById(donation.getCustomerAccountId());
            } catch (Exception e) {
                customer.setAccountId(UUID.fromString(donation.getCustomerAccountId()));
            }
        }
        dto.setDonorDetails(customer);
        return dto;
    }
}
