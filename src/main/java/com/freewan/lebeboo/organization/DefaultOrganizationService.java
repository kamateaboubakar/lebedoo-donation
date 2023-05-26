package com.freewan.lebeboo.organization;

import com.freewan.lebeboo.common.BasicServiceImp;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultOrganizationService extends
        BasicServiceImp<Organization, Long, OrganizationRepository> implements OrganizationService {
    private final OrganizationRepository repository;

    public DefaultOrganizationService(OrganizationRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public List<Organization> findAllByCustomerAccountId(String customerAccountId) {
        return repository.findAllByCustomerAccountId(customerAccountId);
    }
}
