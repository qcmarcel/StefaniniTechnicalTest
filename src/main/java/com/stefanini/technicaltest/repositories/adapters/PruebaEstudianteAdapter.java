package com.stefanini.technicaltest.repositories.adapters;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stefanini.technicaltest.entities.PruebaEstudiante;
import com.stefanini.technicaltest.repositories.crud.PruebaEstudianteCrudRepository;
import com.stefanini.technicaltest.repositories.PruebaEstudianteRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PruebaEstudianteAdapter extends RepositoryAdapterBase<PruebaEstudiante,Long, PruebaEstudianteCrudRepository> implements PruebaEstudianteRepository {
    protected PruebaEstudianteAdapter(PruebaEstudianteCrudRepository repository, RedisTemplate<String, String> redisTemplate, ObjectMapper mapper) {
        super(repository, redisTemplate, estudiante -> String.valueOf(estudiante.getId()), PruebaEstudiante.class, mapper);
    }

    @Override
    public TypeReference<PruebaEstudiante> getTypeReference() {
        return new TypeReference<>() {};
    }

    @Override
    public Optional<PruebaEstudiante> findByIdOrNombre(Long id, String nombre) {
        return getRepository().findByIdOrNombre(id, nombre);
    }
}
