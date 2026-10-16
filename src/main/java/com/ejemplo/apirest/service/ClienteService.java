package com.ejemplo.apirest.service;

// AQUI IRÁ LA LOGICA DEL NEGOCIO

import com.ejemplo.apirest.dto.ClienteRequest;
import com.ejemplo.apirest.dto.ClienteResponse;
import com.ejemplo.apirest.entity.cliente;
import com.ejemplo.apirest.exception.ClienteNoEncontradoException;
import com.ejemplo.apirest.exception.CorreoDuplicadoException;
import com.ejemplo.apirest.mapper.ClienteMapper;
import com.ejemplo.apirest.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;



@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;
    private final PasswordEncoder passwordEncoder;

    public ClienteService(
            ClienteRepository clienteRepository,
            ClienteMapper clienteMapper,
            PasswordEncoder passwordEncoder) {

        this.clienteRepository = clienteRepository;
        this.clienteMapper = clienteMapper;
        this.passwordEncoder = passwordEncoder;
    }


    // =====================================================
    // CREAR CLIENTE
    // =====================================================

    public ClienteResponse crear(ClienteRequest request) {

        // VALIDAR SI EL CORREO YA EXISTE
        if (clienteRepository.existsByCorreo(request.getCorreo())) {

            throw new CorreoDuplicadoException(
                    "El correo " + request.getCorreo() +  " ya está registrado"
            );
        }

        // DTO -> ENTITY
        cliente cliente = clienteMapper.toEntity(request);
        //ENCRIPTAR PASSWORD
        cliente.setPassword(
                passwordEncoder.encode(request.getPassword())
        );
        cliente.setRol("CLIENTE");

        // GUARDAR EN BASE DE DATOS
        cliente guardado = clienteRepository.save(cliente);

        // ENTITY -> DTO
        return clienteMapper.toResponse(guardado);
    }


    // =====================================================
    // BUSCAR CLIENTE POR ID
    // =====================================================

    public ClienteResponse buscarPorId(Long id) {

        cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() ->
                        new ClienteNoEncontradoException(
                                "No existe un cliente con el id: " + id
                        )
                );

        return clienteMapper.toResponse(cliente);
    }


    // =====================================================
    // BUSCAR TODOS LOS CLIENTES
    // =====================================================

    public List<ClienteResponse> buscarTodos() {

        return clienteRepository.findAll()
                .stream()
                .map(clienteMapper::toResponse)
                .toList();
    }


    // =====================================================
    // ACTUALIZAR CLIENTE
    // =====================================================

    public ClienteResponse actualizar(
            Long id,
            ClienteRequest request) {

        // BUSCAR CLIENTE
        cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() ->
                        new ClienteNoEncontradoException(
                                "No existe un cliente con el id: " + id
                        )
                );


        // COMPROBAR QUE EL CORREO NO PERTENEZCA
        // A OTRO CLIENTE
        var existente =
                clienteRepository.findByCorreo(
                        request.getCorreo()
                );

        if (existente.isPresent()
                && !existente.get().getId().equals(id)) {

            throw new CorreoDuplicadoException(
                    "El correo " + request.getCorreo() + " ya pertenece a otro cliente"
            );
        }


        // ACTUALIZAR DATOS
        cliente.setNombre(request.getNombre());
        cliente.setCorreo(request.getCorreo());

        // ACTUALIZAR PASSWORD
        cliente.setPassword(
                passwordEncoder.encode(request.getPassword())
        );


        // GUARDAR
        cliente actualizado =
                clienteRepository.save(cliente);


        // ENTITY -> DTO
        return clienteMapper.toResponse(actualizado);
    }


    // =====================================================
    // ELIMINAR CLIENTE
    // =====================================================

    public void eliminar(Long id) {

        cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() ->
                        new ClienteNoEncontradoException(
                                "No existe un cliente con el id: " + id
                        )
                );

        clienteRepository.delete(cliente);
    }
}