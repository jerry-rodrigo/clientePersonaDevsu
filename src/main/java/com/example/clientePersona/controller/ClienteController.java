package com.example.clientePersona.controller;

import com.example.clientePersona.exception.IdentificacionDuplicadaException;
import com.example.clientePersona.exception.ResourceNotFoundException;
import com.example.clientePersona.service.ClienteService;
import com.example.common.dto.request.ClienteRequestDto;
import com.example.common.dto.response.ClienteResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para manejar operaciones relacionadas con clientes.
 * Proporciona endpoints para crear, obtener, actualizar y eliminar clientes.
 */
@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    /**
     * Obtiene una lista de todos los clientes.
     *
     * @return ResponseEntity con la lista de clientes y el estado HTTP OK.
     */
    @GetMapping
    public ResponseEntity<List<ClienteResponseDto>> getAllClientes() {
        return ResponseEntity.ok(clienteService.getAllClientes());
    }

    /**
     * Obtiene los detalles de un cliente específico por su ID.
     *
     * @param id ID del cliente a buscar.
     * @return ResponseEntity con el cliente encontrado y el estado HTTP OK.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDto> getClienteById(@PathVariable Long id) {
        ClienteResponseDto clienteResponseDto = clienteService.obtenerClientePorId(id);
        return ResponseEntity.ok(clienteResponseDto);
    }

    /**
     * Crea un nuevo cliente con los datos proporcionados.
     *
     * @param clienteRequestDto Datos del cliente a crear.
     * @return ResponseEntity con un mensaje de éxito y el estado HTTP CREATED si la creación es exitosa,
     *         o con un mensaje de error y el estado HTTP BAD REQUEST si hay una excepción.
     */
    @PostMapping
    public ResponseEntity<String> createCliente(@Valid @RequestBody ClienteRequestDto clienteRequestDto) {
        try {
            ClienteResponseDto clienteResponseDto = clienteService.crearCliente(clienteRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Cliente creado exitosamente con ID: " + clienteResponseDto.getClienteId());
        } catch (IdentificacionDuplicadaException | IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * Actualiza un cliente existente con los nuevos datos proporcionados.
     *
     * @param id ID del cliente a actualizar.
     * @param clienteRequestDto Datos actualizados del cliente.
     * @return ResponseEntity con un mensaje de éxito y el estado HTTP OK si la actualización es exitosa,
     *         o con un mensaje de error y el estado HTTP correspondiente si ocurre alguna excepción.
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> updateCliente(@PathVariable Long id, @Valid @RequestBody ClienteRequestDto clienteRequestDto) {
        try {
            ClienteResponseDto clienteResponseDto = clienteService.editarCliente(id, clienteRequestDto);
            return ResponseEntity.ok("Cliente actualizado exitosamente con ID: " + clienteResponseDto.getClienteId());
        } catch (IdentificacionDuplicadaException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * Elimina un cliente específico por su ID.
     *
     * @param id ID del cliente a eliminar.
     * @return ResponseEntity con un mensaje de éxito y el estado HTTP NO CONTENT si la eliminación es exitosa,
     *         o con un mensaje de error y el estado HTTP NOT FOUND si el cliente no existe.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCliente(@PathVariable Long id) {
        try {
            clienteService.eliminarCliente(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Cliente eliminado exitosamente.");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
