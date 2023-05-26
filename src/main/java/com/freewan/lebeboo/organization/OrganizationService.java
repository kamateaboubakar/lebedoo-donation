package com.freewan.lebeboo.organization;

import com.freewan.lebeboo.common.BasicService;

import java.util.List;

public interface OrganizationService extends BasicService<Organization, Long> {
    List<Organization> findAllByCustomerAccountId(String customerAccountId);
}
