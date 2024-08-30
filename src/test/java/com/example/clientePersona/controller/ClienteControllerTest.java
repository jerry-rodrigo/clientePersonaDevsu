package com.example.clientePersona.controller;

import com.example.clientePersona.exception.IdentificacionDuplicadaException;
import com.example.clientePersona.exception.ResourceNotFoundException;
import com.example.clientePersona.service.ClienteService;
import com.example.common.dto.request.ClienteRequestDto;
import com.example.common.dto.response.ClienteResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ClienteControllerTest {

    @InjectMocks
    private ClienteController clienteController;

    @Mock
    private ClienteService clienteService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllClientes() {
        // Arrange
        ClienteResponseDto clienteResponseDto = new ClienteResponseDto("00000001", "Carlos Fernández", "10948075", true);
        when(clienteService.getAllClientes()).thenReturn(Collections.singletonList(clienteResponseDto));

        // Act
        ResponseEntity<List<ClienteResponseDto>> response = clienteController.getAllClientes();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("Carlos Fernández", response.getBody().get(0).getNombre());
    }

    @Test
    public void testGetClienteById_Success() {
        // Arrange
        Long clienteId = 1L;
        ClienteResponseDto clienteResponseDto = new ClienteResponseDto("00000001", "Carlos Fernández", "10948075", true);
        when(clienteService.obtenerClientePorId(clienteId)).thenReturn(clienteResponseDto);

        // Act
        ResponseEntity<ClienteResponseDto> response = clienteController.getClienteById(clienteId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Carlos Fernández", response.getBody().getNombre());
    }


    @Test
    public void testCreateCliente_Success() {
        // Arrange
        ClienteRequestDto requestDto = new ClienteRequestDto("Carlos Fernández", "Masculino", 28, "10948075",
                "Avenida Siempre Viva 742", "5556789",
                "contrasena123", true);
        ClienteResponseDto responseDto = new ClienteResponseDto("00000001", "Carlos Fernández", "10948075", true);
        when(clienteService.crearCliente(requestDto)).thenReturn(responseDto);

        // Act
        ResponseEntity<String> response = clienteController.createCliente(requestDto);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Cliente creado exitosamente con ID: 00000001", response.getBody());
    }

    @Test
    public void testCreateCliente_IdentificacionDuplicada() {
        // Arrange
        ClienteRequestDto requestDto = new ClienteRequestDto("Carlos Fernández", "Masculino", 28, "10948075",
                "Avenida Siempre Viva 742", "5556789",
                "contrasena123", true);
        when(clienteService.crearCliente(requestDto)).thenThrow(new IdentificacionDuplicadaException("La identificación ya está en uso."));

        // Act
        ResponseEntity<String> response = clienteController.createCliente(requestDto);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("La identificación ya está en uso.", response.getBody());
    }

    @Test
    public void testUpdateCliente_Success() {
        // Arrange
        Long clienteId = 1L;
        ClienteRequestDto requestDto = new ClienteRequestDto("Carlos Fernández", "Masculino", 28, "10948075",
                "Avenida Siempre Viva 742", "5556789",
                "contrasena123", true);
        ClienteResponseDto responseDto = new ClienteResponseDto("00000001", "Carlos Fernández", "10948075", true);
        when(clienteService.editarCliente(clienteId, requestDto)).thenReturn(responseDto);

        // Act
        ResponseEntity<String> response = clienteController.updateCliente(clienteId, requestDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Cliente actualizado exitosamente con ID: 00000001", response.getBody());
    }

    @Test
    public void testUpdateCliente_IdentificacionDuplicada() {
        // Arrange
        Long clienteId = 1L;
        ClienteRequestDto requestDto = new ClienteRequestDto("Carlos Fernández", "Masculino", 28, "10948075",
                "Avenida Siempre Viva 742", "5556789",
                "contrasena123", true);
        when(clienteService.editarCliente(clienteId, requestDto)).thenThrow(new IdentificacionDuplicadaException("La identificación ya está en uso."));

        // Act
        ResponseEntity<String> response = clienteController.updateCliente(clienteId, requestDto);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("La identificación ya está en uso.", response.getBody());
    }

    @Test
    public void testUpdateCliente_NotFound() {
        // Arrange
        Long clienteId = 1L;
        ClienteRequestDto requestDto = new ClienteRequestDto("Carlos Fernández", "Masculino", 28, "10948075",
                "Avenida Siempre Viva 742", "5556789",
                "contrasena123", true);
        when(clienteService.editarCliente(clienteId, requestDto)).thenThrow(new ResourceNotFoundException("Cliente no encontrado con el ID: " + clienteId));

        // Act
        ResponseEntity<String> response = clienteController.updateCliente(clienteId, requestDto);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Cliente no encontrado con el ID: " + clienteId, response.getBody());
    }

    @Test
    public void testDeleteCliente_Success() {
        // Arrange
        Long clienteId = 1L;
        doNothing().when(clienteService).eliminarCliente(clienteId);

        // Act
        ResponseEntity<String> response = clienteController.deleteCliente(clienteId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals("Cliente eliminado exitosamente.", response.getBody());
    }

    @Test
    public void testDeleteCliente_NotFound() {
        // Arrange
        Long clienteId = 1L;
        doThrow(new ResourceNotFoundException("Cliente no encontrado con el ID: " + clienteId)).when(clienteService).eliminarCliente(clienteId);

        // Act
        ResponseEntity<String> response = clienteController.deleteCliente(clienteId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Cliente no encontrado con el ID: " + clienteId, response.getBody());
    }
}
