package com.example.clientePersona.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ClienteTest {

    private Cliente cliente;

    @BeforeEach
    public void setUp() {
        cliente = new Cliente();
        cliente.setContrasena("password");
        cliente.setEstado(true);
    }

    @Test
    public void testPrePersistGeneratesClienteId() {
        // Llamar a prePersist para generar clienteId
        cliente.prePersist();

        // Verificar que el clienteId no sea nulo y tenga la longitud correcta
        assertNotNull(cliente.getClienteId());
        assertEquals(8, cliente.getClienteId().length());
    }

    @Test
    public void testPrePersistDoesNotChangeExistingClienteId() {
        // Asignar un clienteId existente
        String existingId = "12345678";
        cliente.setClienteId(existingId);

        // Llamar a prePersist
        cliente.prePersist();

        // Verificar que el clienteId no cambie
        assertEquals(existingId, cliente.getClienteId());
    }

    @Test
    public void testGettersAndSetters() {
        cliente.setClienteId("87654321");
        assertEquals("87654321", cliente.getClienteId());
        assertEquals("password", cliente.getContrasena());
        assertEquals(true, cliente.getEstado());
    }
}
