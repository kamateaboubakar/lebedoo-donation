package com.freewan.lebeboo.campaign;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CampaignRepository extends JpaRepository<Campaign, Long> {
    List<Campaign> findAllByOrganization_CustomerAccountId(String customerAccountId);
}