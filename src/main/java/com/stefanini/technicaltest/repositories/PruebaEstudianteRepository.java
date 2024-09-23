package com.stefanini.technicaltest.repositories;

import com.stefanini.technicaltest.entities.PruebaEstudiante;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PruebaEstudianteRepository extends RepositoryBase<PruebaEstudiante, Long>, JpaSpecificationExecutor<PruebaEstudiante> {
}