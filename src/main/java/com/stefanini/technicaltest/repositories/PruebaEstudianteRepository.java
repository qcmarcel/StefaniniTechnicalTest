package com.stefanini.technicaltest.repositories;

import com.stefanini.technicaltest.entities.PruebaEstudiante;
import jakarta.validation.constraints.*;

import java.util.Optional;

public interface PruebaEstudianteRepository extends RepositoryBase<PruebaEstudiante, Long>{
    Optional<PruebaEstudiante> findByIdOrNombre(@Min(100) @Positive Long id, @NotNull @Size(max = 65) @NotEmpty String nombre);
}