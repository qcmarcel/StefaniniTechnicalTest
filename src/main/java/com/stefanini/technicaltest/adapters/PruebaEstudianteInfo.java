package com.stefanini.technicaltest.adapters;

/**
 * Projection for {@link com.stefanini.technicaltest.entities.PruebaEstudiante}
 */
public interface PruebaEstudianteInfo {
    Long getId();

    String getNombre();

    String getEspecialidad();

    String getGrado();
}