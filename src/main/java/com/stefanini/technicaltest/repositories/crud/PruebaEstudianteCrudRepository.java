package com.stefanini.technicaltest.repositories.crud;

import com.stefanini.technicaltest.entities.PruebaEstudiante;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PruebaEstudianteCrudRepository extends CrudRepository<PruebaEstudiante, Long> {
    Optional<PruebaEstudiante> findByIdOrNombre(Long id, String nombre);
}