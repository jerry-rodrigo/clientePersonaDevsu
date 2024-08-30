package com.example.clientePersona.service;

import com.example.clientePersona.entity.Cliente;
import com.example.clientePersona.exception.IdentificacionDuplicadaException;
import com.example.clientePersona.exception.ResourceNotFoundException;
import com.example.clientePersona.repository.ClienteRepository;
import com.example.common.dto.request.ClienteRequestDto;
import com.example.common.dto.response.ClienteResponseDto;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio para la gestión de clientes.
 * Proporciona métodos para crear, obtener, editar y eliminar clientes, así como para listar todos los clientes.
 */
@Service
public class ClienteService {

    private static final Logger logger = LoggerFactory.getLogger(ClienteService.class);

    private final ClienteRepository clienteRepository;


    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    /**
     * Crea un nuevo cliente y lo guarda en la base de datos.
     *
     * @param clienteRequestDto DTO con la información del cliente a crear.
     * @return DTO con la información del cliente creado.
     * @throws IdentificacionDuplicadaException si ya existe un cliente con la misma identificación.
     * @throws IllegalArgumentException si el nombre del cliente está vacío o hay un error al guardar el cliente.
     */
    public ClienteResponseDto crearCliente(ClienteRequestDto clienteRequestDto) {
        logger.info("Intentando crear un nuevo cliente con identificación: {}", clienteRequestDto.getIdentificacion());

        if (clienteRepository.existsByIdentificacion(clienteRequestDto.getIdentificacion())) {
            throw new IdentificacionDuplicadaException("La identificación ya está en uso.");
        }
        if (clienteRequestDto.getNombre() == null || clienteRequestDto.getNombre().isEmpty()) {
            throw new IllegalArgumentException("El nombre del cliente es obligatorio.");
        }
        Cliente cliente = new Cliente();
        cliente.setNombre(clienteRequestDto.getNombre());
        cliente.setGenero(clienteRequestDto.getGenero());
        cliente.setEdad(clienteRequestDto.getEdad());
        cliente.setIdentificacion(clienteRequestDto.getIdentificacion());
        cliente.setDireccion(clienteRequestDto.getDireccion());
        cliente.setTelefono(clienteRequestDto.getTelefono());
        cliente.setEstado(clienteRequestDto.getEstado());

        String encriptada = BCrypt.hashpw(clienteRequestDto.getContrasena(), BCrypt.gensalt());
        cliente.setContrasena(encriptada);

        try {
            cliente = clienteRepository.save(cliente);
            logger.info("Cliente creado exitosamente con ID: {}", cliente.getClienteId());
        } catch (DataIntegrityViolationException e) {
            logger.error("Error al guardar el cliente: {}", e.getMessage());
            throw new IllegalArgumentException("Error al guardar el cliente. Verifique los datos ingresados.");
        }

        return new ClienteResponseDto(cliente.getClienteId(), cliente.getNombre(), cliente.getIdentificacion(), cliente.getEstado());
    }

    /**
     * Obtiene un cliente por su ID.
     *
     * @param clienteId ID del cliente a obtener.
     * @return DTO con la información del cliente encontrado.
     * @throws ResourceNotFoundException si no se encuentra un cliente con el ID proporcionado.
     */
    public ClienteResponseDto obtenerClientePorId(Long clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con el ID: " + clienteId));

        return new ClienteResponseDto(cliente.getClienteId(), cliente.getNombre(), cliente.getIdentificacion(), cliente.getEstado());
    }

    /**
     * Actualiza un cliente existente.
     *
     * @param clienteId ID del cliente a actualizar.
     * @param clienteRequestDto DTO con la nueva información del cliente.
     * @return DTO con la información del cliente actualizado.
     * @throws IdentificacionDuplicadaException si la nueva identificación ya está en uso.
     * @throws ResourceNotFoundException si no se encuentra un cliente con el ID proporcionado.
     * @throws IllegalArgumentException si hay un error al actualizar el cliente.
     */
    public ClienteResponseDto editarCliente(Long clienteId, ClienteRequestDto clienteRequestDto) {
        logger.info("Editando cliente con ID: {}", clienteId);
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con el ID: " + clienteId));

        if (clienteRequestDto.getIdentificacion() != null &&
                !cliente.getIdentificacion().equals(clienteRequestDto.getIdentificacion()) &&
                clienteRepository.existsByIdentificacion(clienteRequestDto.getIdentificacion())) {
            throw new IdentificacionDuplicadaException("La identificación ya está en uso.");
        }

        cliente.setNombre(clienteRequestDto.getNombre());
        cliente.setGenero(clienteRequestDto.getGenero());
        cliente.setEdad(clienteRequestDto.getEdad());
        cliente.setIdentificacion(clienteRequestDto.getIdentificacion());
        cliente.setDireccion(clienteRequestDto.getDireccion());
        cliente.setTelefono(clienteRequestDto.getTelefono());
        cliente.setEstado(clienteRequestDto.getEstado());

        String encriptada = BCrypt.hashpw(clienteRequestDto.getContrasena(), BCrypt.gensalt());
        cliente.setContrasena(encriptada);

        try {
            cliente = clienteRepository.save(cliente);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Error al actualizar el cliente. Verifique los datos ingresados.");
        }

        return new ClienteResponseDto(cliente.getClienteId(), cliente.getNombre(), cliente.getIdentificacion(), cliente.getEstado());
    }

    /**
     * Elimina un cliente por su ID.
     *
     * @param clienteId ID del cliente a eliminar.
     * @throws ResourceNotFoundException si no se encuentra un cliente con el ID proporcionado.
     */
    public void eliminarCliente(Long clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con el ID: " + clienteId));
        clienteRepository.delete(cliente);
    }

    /**
     * Obtiene todos los clientes.
     *
     * @return Lista de DTOs con la información de todos los clientes.
     */
    public List<ClienteResponseDto> getAllClientes() {
        List<Cliente> clientes = clienteRepository.findAll();
        return clientes.stream()
                .map(cliente -> new ClienteResponseDto(cliente.getClienteId(), cliente.getNombre(), cliente.getIdentificacion(), cliente.getEstado()))
                .collect(Collectors.toList());
    }
}
