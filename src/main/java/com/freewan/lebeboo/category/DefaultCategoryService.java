package com.freewan.lebeboo.category;

import com.freewan.lebeboo.campaign.Campaign;
import com.freewan.lebeboo.common.BasicServiceImp;
import com.freewan.lebeboo.exception.DataNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DefaultCategoryService extends
        BasicServiceImp<Category, Long, CategoryRepository> implements CategoryService {
    private final CategoryRepository repository;

    public DefaultCategoryService(CategoryRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public Category findById(Long aLong) throws DataNotFoundException {
        try {
            return super.findById(aLong);
        } catch (DataNotFoundException e) {
            throw new DataNotFoundException("Category with id '%s' not found.".formatted(aLong));
        }
    }
}
