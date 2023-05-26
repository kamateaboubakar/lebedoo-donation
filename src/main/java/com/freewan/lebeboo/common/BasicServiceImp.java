package com.freewan.lebeboo.common;

import com.freewan.lebeboo.exception.DataNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public abstract class BasicServiceImp<T, ID, REPO extends JpaRepository<T, ID>> implements BasicService<T, ID> {

    private final REPO repository;

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public T findById(ID id) throws DataNotFoundException {
        return repository.findById(id).orElseThrow(() -> new DataNotFoundException(String.format("Aucun élément trouvé avec l'identifiant '%s'", id)));
    }

    @Override
    public T save(T entity) {
        return repository.save(entity);
    }

    public T update(T entity) {
        return repository.save(entity);
    }

    @Override
    public void deleteById(ID id) throws DataNotFoundException {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw new DataNotFoundException(String.format("Aucun élément trouvé avec l'identifiant '%s'", id));
        }
    }

    @Override
    public void delete(T entity) throws DataNotFoundException {
        repository.delete(entity);
    }
}
