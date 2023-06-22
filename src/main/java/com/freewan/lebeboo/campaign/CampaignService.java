package com.freewan.lebeboo.campaign;

import com.freewan.lebeboo.common.BasicService;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CampaignService extends BasicService<Campaign, Long> {
    List<Campaign> findAllByCustomerAccountId(String customerAccountId);
    List<Campaign> findAllByCategoryId(int categoryId);
}
