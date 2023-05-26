package com.freewan.lebeboo.campaign;

import com.freewan.lebeboo.common.BasicServiceImp;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultCampaignService extends
        BasicServiceImp<Campaign, Long, CampaignRepository> implements CampaignService {
    private final CampaignRepository repository;

    public DefaultCampaignService(CampaignRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public List<Campaign> findAllByCustomerAccountId(String customerAccountId) {
        return repository.findAllByOrganization_CustomerAccountId(customerAccountId);
    }
}
