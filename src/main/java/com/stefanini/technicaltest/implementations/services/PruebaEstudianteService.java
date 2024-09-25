package com.stefanini.technicaltest.implementations.services;

import com.stefanini.technicaltest.types.PruebaEstudianteDto;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface PruebaEstudianteService {
    ResponseEntity<?> all();

    ResponseEntity<?> findById(Long id);

    ResponseEntity<?> store(PruebaEstudianteDto estudiante);

    ResponseEntity<?> update(PruebaEstudianteDto estudiante, Long id);

    ResponseEntity<?> delete(Long id, Map<String, String> queryParams);
}
