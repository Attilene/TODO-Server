package com.example.demo.services;

import java.util.List;

public interface AbstractService<T> {
    List<T> getAll();
    T getById(Long id);
    T add(T node);
    T update(T node);
    void delete(T node);
}
