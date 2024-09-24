package com.stefanini.technicaltest.implementations.services;

import org.springframework.http.ResponseEntity;

public interface PruebaEstudianteService {
    ResponseEntity<?> all();

    ResponseEntity<?> findById(Long id);
}
