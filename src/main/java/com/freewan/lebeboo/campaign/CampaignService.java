package com.freewan.lebeboo.campaign;

import com.freewan.lebeboo.common.BasicService;

import java.util.List;

public interface CampaignService extends BasicService<Campaign, Long> {
    List<Campaign> findAllByCustomerAccountId(String customerAccountId);
}
