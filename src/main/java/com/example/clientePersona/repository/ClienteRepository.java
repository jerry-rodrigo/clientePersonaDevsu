package com.example.clientePersona.repository;

import com.example.clientePersona.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio de JPA para la entidad {@link Cliente}.
 * Proporciona métodos CRUD y la capacidad de buscar clientes por su identificación.
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    /**
     * Busca un cliente por su identificación.
     *
     * @param clienteId La identificación del cliente a buscar.
     * @return Un Optional que contiene el cliente si se encuentra, o vacío si no se encuentra.
     */
    Optional<Cliente> findByClienteId(String clienteId);

    /**
     * Verifica si existe un cliente con la identificación proporcionada.
     *
     * @param identificacion La identificación del cliente a verificar.
     * @return {@code true} si existe un cliente con la identificación dada, {@code false} en caso contrario.
     */
    boolean existsByIdentificacion(String identificacion);
}
