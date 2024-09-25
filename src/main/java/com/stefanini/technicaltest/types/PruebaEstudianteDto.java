package com.stefanini.technicaltest.types;

import com.stefanini.technicaltest.entities.PruebaEstudiante;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * DTO for {@link com.stefanini.technicaltest.entities.PruebaEstudiante}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class PruebaEstudianteDto implements Serializable {
    @Min(100)
    @Positive
    private Long id;
    @NotNull
    @Size(max = 65)
    @NotEmpty
    @NotBlank
    private String nombre;
    @NotNull
    @NotEmpty
    @NotBlank
    private String especialidad;
    @NotEmpty
    @NotBlank
    private String grado;

    public PruebaEstudianteDto(PruebaEstudiante pe) {
        if (pe == null) {
            return;
        }
        this.id = pe.getId();
        this.nombre = pe.getNombre();
        this.especialidad = pe.getEspecialidad();
        this.grado = pe.getGrado();
    }

    public PruebaEstudiante merge(PruebaEstudiante from){
        this.id = from.getId();
        if (this.nombre == null || this.nombre.isEmpty()){
            this.nombre = from.getNombre();
        }
        if (this.especialidad == null || this.especialidad.isEmpty()){
            this.especialidad = from.getEspecialidad();
        }
        if (this.grado == null || this.grado.isEmpty()){
            this.grado = from.getGrado();
        }
        return this.toEntity();
    }

    public PruebaEstudiante toEntity() {
        PruebaEstudiante pe = new PruebaEstudiante();
        pe.setId(this.id);
        pe.setNombre(this.nombre);
        pe.setEspecialidad(this.especialidad);
        pe.setGrado(this.grado);
        return pe;
    }
}