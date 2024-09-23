package com.stefanini.technicaltest.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "PRUEBAESTUDIANTE", schema = "mydatabase")
public class PruebaEstudiante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Eid", nullable = false)
    private Long id;

    @Size(max = 65)
    @NotNull
    @Column(name = "Nombre", nullable = false, length = 65)
    private String nombre;

    @NotNull
    @ColumnDefault("'HISTORIA'")
    @Lob
    @Column(name = "Especialidad", nullable = false)
    private String especialidad;

    @ColumnDefault("'JR'")
    @Lob
    @Column(name = "Grado")
    private String grado;

}