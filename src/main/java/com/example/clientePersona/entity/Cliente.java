package com.example.clientePersona.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entidad que representa a un cliente en el sistema.
 * Hereda de la entidad {@link Persona} y se utiliza para gestionar los datos específicos de un cliente.
 */
@Entity
@Table(name = "personas")
@DiscriminatorValue("Cliente")
@Data
@EqualsAndHashCode(callSuper = true)
public class Cliente extends Persona {

    @Column(name = "cliente_id", unique = true, nullable = false)
    private String clienteId;

    @Column(name = "contraseña", nullable = false)
    private String contrasena;

    @Column(nullable = false)
    private Boolean estado;

    /**
     * Método de ciclo de vida de JPA que se ejecuta antes de persistir el cliente en la base de datos.
     * Genera un identificador único para el cliente si no está ya establecido.
     */
    @PrePersist
    public void prePersist() {
        if (this.clienteId == null) {
            this.clienteId = generateClienteId();
        }
    }

    /**
     * Genera un identificador único para el cliente.
     * El identificador es una cadena de 8 dígitos.
     *
     * @return El identificador único generado.
     */
    private String generateClienteId() {
        return String.format("%08d", (int)(Math.random() * 1000000));
    }
}
