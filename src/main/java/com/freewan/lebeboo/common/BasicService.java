package com.freewan.lebeboo.common;

import java.util.List;

public interface BasicService<ENTITY, T> {
    List<ENTITY> findAll();

    ENTITY findById(T id);

    ENTITY save(ENTITY entity);

    void deleteById(T id);

    void delete(ENTITY entity);
}
