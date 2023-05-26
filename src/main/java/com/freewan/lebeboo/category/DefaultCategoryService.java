package com.freewan.lebeboo.category;

import com.freewan.lebeboo.common.BasicServiceImp;
import org.springframework.stereotype.Service;

@Service
public class DefaultCategoryService extends
        BasicServiceImp<Category, Long, CategoryRepository> implements CategoryService {
    private final CategoryRepository repository;

    public DefaultCategoryService(CategoryRepository repository) {
        super(repository);
        this.repository = repository;
    }
}
