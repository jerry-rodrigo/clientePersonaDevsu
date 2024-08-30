package com.example.clientePersona.service;

import com.example.clientePersona.entity.Cliente;
import com.example.clientePersona.exception.IdentificacionDuplicadaException;
import com.example.clientePersona.exception.ResourceNotFoundException;
import com.example.clientePersona.repository.ClienteRepository;
import com.example.common.dto.request.ClienteRequestDto;
import com.example.common.dto.response.ClienteResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class ClienteServiceTest {

    @InjectMocks
    private ClienteService clienteService;

    @Mock
    private ClienteRepository clienteRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCrearCliente_Success() {
        // Arrange
        ClienteRequestDto requestDto = new ClienteRequestDto("Carlos Fernández", "Masculino", 28, "10948075",
                "Avenida Siempre Viva 742", "5556789",
                "contrasena123", true);
        Cliente cliente = new Cliente();
        cliente.setClienteId("00000001");
        cliente.setNombre("Carlos Fernández");
        cliente.setIdentificacion("10948075");
        cliente.setEstado(true);

        when(clienteRepository.existsByIdentificacion(anyString())).thenReturn(false);

        // Encriptar la contraseña
        String encriptada = BCrypt.hashpw(requestDto.getContrasena(), BCrypt.gensalt());
        cliente.setContrasena(encriptada);

        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        // Act
        ClienteResponseDto responseDto = clienteService.crearCliente(requestDto);

        // Assert
        assertNotNull(responseDto);
        assertEquals("00000001", responseDto.getClienteId());
        assertEquals("Carlos Fernández", responseDto.getNombre());
        assertEquals("10948075", responseDto.getIdentificacion());
        assertTrue(responseDto.getEstado());
    }

    @Test
    public void testCrearCliente_IdentificacionDuplicada() {
        // Arrange
        ClienteRequestDto requestDto = new ClienteRequestDto("Carlos Fernández", "Masculino", 28, "10948075",
                "Avenida Siempre Viva 742", "5556789",
                "contrasena123", true);

        when(clienteRepository.existsByIdentificacion(anyString())).thenReturn(true);

        // Act & Assert
        IdentificacionDuplicadaException exception = assertThrows(IdentificacionDuplicadaException.class, () -> {
            clienteService.crearCliente(requestDto);
        });
        assertEquals("La identificación ya está en uso.", exception.getMessage());
    }

    @Test
    public void testObtenerClientePorId_Success() {
        // Arrange
        Long clienteId = 1L;
        Cliente cliente = new Cliente();
        cliente.setClienteId("00000001");
        cliente.setNombre("Carlos Fernández");
        cliente.setIdentificacion("10948075");
        cliente.setEstado(true);

        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(cliente));

        // Act
        ClienteResponseDto responseDto = clienteService.obtenerClientePorId(clienteId);

        // Assert
        assertNotNull(responseDto);
        assertEquals("00000001", responseDto.getClienteId());
        assertEquals("Carlos Fernández", responseDto.getNombre());
        assertEquals("10948075", responseDto.getIdentificacion());
        assertTrue(responseDto.getEstado());
    }

    @Test
    public void testObtenerClientePorId_NotFound() {
        // Arrange
        Long clienteId = 1L;
        when(clienteRepository.findById(clienteId)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            clienteService.obtenerClientePorId(clienteId);
        });
        assertEquals("Cliente no encontrado con el ID: " + clienteId, exception.getMessage());
    }

    @Test
    public void testEditarCliente_Success() {
        // Arrange
        Long clienteId = 1L;
        ClienteRequestDto requestDto = new ClienteRequestDto("Carlos Fernández", "Masculino", 28, "10948075",
                "Avenida Siempre Viva 742", "5556789",
                "contrasena123", true);
        Cliente cliente = new Cliente();
        cliente.setClienteId("00000001");
        cliente.setNombre("Carlos Fernández");
        cliente.setIdentificacion("10948075");
        cliente.setEstado(true);

        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(cliente));
        when(clienteRepository.existsByIdentificacion(anyString())).thenReturn(false);

        // Encriptar la nueva contraseña
        String encriptada = BCrypt.hashpw(requestDto.getContrasena(), BCrypt.gensalt());
        cliente.setContrasena(encriptada);

        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        // Act
        ClienteResponseDto responseDto = clienteService.editarCliente(clienteId, requestDto);

        // Assert
        assertNotNull(responseDto);
        assertEquals("00000001", responseDto.getClienteId());
        assertEquals("Carlos Fernández", responseDto.getNombre());
        assertEquals("10948075", responseDto.getIdentificacion());
        assertTrue(responseDto.getEstado());
    }

    @Test
    public void testEditarCliente_NotFound() {
        // Arrange
        Long clienteId = 1L;
        ClienteRequestDto requestDto = new ClienteRequestDto("Carlos Fernández", "Masculino", 28, "10948075",
                "Avenida Siempre Viva 742", "5556789",
                "contrasena123", true);
        when(clienteRepository.findById(clienteId)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            clienteService.editarCliente(clienteId, requestDto);
        });
        assertEquals("Cliente no encontrado con el ID: " + clienteId, exception.getMessage());
    }

    @Test
    public void testEliminarCliente_Success() {
        // Arrange
        Long clienteId = 1L;
        Cliente cliente = new Cliente();
        cliente.setClienteId("00000001");
        cliente.setNombre("Carlos Fernández");
        cliente.setIdentificacion("10948075");
        cliente.setEstado(true);

        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(cliente));

        // Act
        clienteService.eliminarCliente(clienteId);

        // Assert
        verify(clienteRepository, times(1)).delete(cliente);
    }

    @Test
    public void testEliminarCliente_NotFound() {
        // Arrange
        Long clienteId = 1L;
        when(clienteRepository.findById(clienteId)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            clienteService.eliminarCliente(clienteId);
        });
        assertEquals("Cliente no encontrado con el ID: " + clienteId, exception.getMessage());
    }
}