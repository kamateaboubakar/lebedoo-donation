package com.freewan.lebeboo.donation;

import com.freewan.lebeboo.campaign.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DonationRepository extends JpaRepository<Donation, Long> {
    List<Donation> findAllByCampaignAndCustomerAccountId(Campaign campaign, String customerAccountId);

    List<Donation> findAllByCampaign(Campaign campaign);

    List<Donation> findAllByCustomerAccountId(String customerAccountId);
}