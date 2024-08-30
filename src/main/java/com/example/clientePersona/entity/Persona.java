package com.example.clientePersona.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidad base que representa una persona en el sistema.
 * Utiliza una tabla única para la jerarquía de herencia y se extiende en otras entidades como {@link Cliente}.
 */
@Entity
@Table(name = "personas")  // Aquí definimos la tabla única para la jerarquía
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_persona", discriminatorType = DiscriminatorType.STRING)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String genero;
    private int edad;
    private String identificacion;
    private String direccion;
    private String telefono;
}
