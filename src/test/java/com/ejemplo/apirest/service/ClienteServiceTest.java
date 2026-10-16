package com.ejemplo.apirest.service;

import com.ejemplo.apirest.dto.ClienteRequest;
import com.ejemplo.apirest.dto.ClienteResponse;
import com.ejemplo.apirest.entity.cliente;
import com.ejemplo.apirest.exception.CorreoDuplicadoException;
import com.ejemplo.apirest.mapper.ClienteMapper;
import com.ejemplo.apirest.repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import com.ejemplo.apirest.exception.ClienteNoEncontradoException;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ClienteMapper clienteMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private ClienteService clienteService;


    // =====================================================
    // ESTA ES UNA PRUEBA UNITARIA
    // PRUEBA 1
    // CREAR CLIENTE CORRECTAMENTE
    // =====================================================

    @Test
    void debeCrearClienteCorrectamente() {

        // ARRANGE
        ClienteRequest request = new ClienteRequest();

        request.setNombre("Cecilia");
        request.setCorreo("cecilia@test.com");
        request.setPassword("123456");

        cliente cliente = new cliente();

        cliente.setNombre("Cecilia");
        cliente.setCorreo("cecilia@test.com");
        cliente.setPassword("123456");

        ClienteResponse response = new ClienteResponse();

        when(clienteRepository.existsByCorreo("cecilia@test.com"))
                .thenReturn(false);

        when(clienteMapper.toEntity(request))
                .thenReturn(cliente);

        when(passwordEncoder.encode("123456"))
                .thenReturn("PASSWORD_ENCRIPTADO");

        when(clienteRepository.save(cliente))
                .thenReturn(cliente);

        when(clienteMapper.toResponse(cliente))
                .thenReturn(response);


        // ACT
        ClienteResponse resultado =
                clienteService.crear(request);


        // ASSERT
        assertNotNull(resultado);

        assertEquals(
                "PASSWORD_ENCRIPTADO",
                cliente.getPassword()
        );

        assertEquals(
                "CLIENTE",
                cliente.getRol()
        );


        // VERIFY
        verify(clienteRepository)
                .existsByCorreo("cecilia@test.com");

        verify(clienteRepository)
                .save(cliente);

        verify(passwordEncoder)
                .encode("123456");

        verify(clienteMapper)
                .toEntity(request);

        verify(clienteMapper)
                .toResponse(cliente);
    }


    // =====================================================
    // PRUEBA 2
    // CORREO DUPLICADO
    // =====================================================

    @Test
    void debeLanzarExcepcionSiCorreoYaExiste() {

        // ARRANGE
        ClienteRequest request = new ClienteRequest();

        request.setNombre("Cecilia");
        request.setCorreo("cecilia@gmail.com");
        request.setPassword("123456");

        when(clienteRepository.existsByCorreo("cecilia@gmail.com"))
                .thenReturn(true);


        // ACT + ASSERT
        assertThrows(
                CorreoDuplicadoException.class,
                () -> clienteService.crear(request)
        );


        // VERIFY
        verify(clienteRepository)
                .existsByCorreo("cecilia@gmail.com");

        verify(clienteRepository, never())
                .save(any(cliente.class));

        verifyNoInteractions(
                clienteMapper,
                passwordEncoder
        );
    }

    // =====================================================
    // PRUEBA 3
    // BUSCAR CLIENTE POR ID
    // =====================================================

    @Test
    void debeBuscarClientePorId() {

        // ARRANGE
        Long id = 1L;

        cliente cliente = new cliente();

        cliente.setNombre("Cecilia");
        cliente.setCorreo("cecilia@gmail.com");
        cliente.setPassword("PASSWORD_ENCRIPTADO");
        cliente.setRol("CLIENTE");

        ClienteResponse response = new ClienteResponse();

        when(clienteRepository.findById(id))
                .thenReturn(java.util.Optional.of(cliente));

        when(clienteMapper.toResponse(cliente))
                .thenReturn(response);


        // ACT
        ClienteResponse resultado =
                clienteService.buscarPorId(id);


        // ASSERT
        assertNotNull(resultado);


        // VERIFY
        verify(clienteRepository)
                .findById(id);

        verify(clienteMapper)
                .toResponse(cliente);
    }

    // =====================================================
    // PRUEBA 4
    // MANEJO DE EXCEPCIONES
    // =====================================================

    @Test
    void debeLanzarExcepcionSiClienteNoExiste() {

        // ARRANGE
        Long id = 999L;

        when(clienteRepository.findById(id))
                .thenReturn(java.util.Optional.empty());


        // ACT + ASSERT
        assertThrows(
                ClienteNoEncontradoException.class,
                () -> clienteService.buscarPorId(id)
        );


        // VERIFY
        verify(clienteRepository)
                .findById(id);

        verifyNoInteractions(clienteMapper);
    }
    // =====================================================
    // PRUEBA 5
    // BUSCAR TODOS
    // =====================================================
    @Test
    void debeBuscarTodosLosClientes() {

        // ARRANGE
        cliente cliente1 = new cliente();
        cliente1.setId(1L);
        cliente1.setNombre("Cecilia");
        cliente1.setCorreo("cecilia@gmail.com");

        cliente cliente2 = new cliente();
        cliente2.setId(2L);
        cliente2.setNombre("Paola");
        cliente2.setCorreo("paola123@gmail.com");

        ClienteResponse response1 = new ClienteResponse();
        ClienteResponse response2 = new ClienteResponse();

        when(clienteRepository.findAll())
                .thenReturn(List.of(cliente1, cliente2));

        when(clienteMapper.toResponse(cliente1))
                .thenReturn(response1);

        when(clienteMapper.toResponse(cliente2))
                .thenReturn(response2);


        // ACT
        List<ClienteResponse> resultado =
                clienteService.buscarTodos();


        // ASSERT
        assertNotNull(resultado);
        assertEquals(2, resultado.size());

        assertSame(response1, resultado.get(0));
        assertSame(response2, resultado.get(1));


        // VERIFY
        verify(clienteRepository)
                .findAll();

        verify(clienteMapper)
                .toResponse(cliente1);

        verify(clienteMapper)
                .toResponse(cliente2);
    }
    // =====================================================
    // PRUEBA 6
    // ACTUALIZAR CLIENTE
    // =====================================================
    @Test
    void debeActualizarClienteCorrectamente() {

        // ARRANGE
        Long id = 1L;

        cliente cliente = new cliente();
        cliente.setId(id);
        cliente.setNombre("Cecilia");
        cliente.setCorreo("cecilia@gmail.com");
        cliente.setPassword("passwordViejo");
        cliente.setRol("CLIENTE");

        ClienteRequest request = new ClienteRequest();
        request.setNombre("Cecilia Actualizada");
        request.setCorreo("cecilia_nueva@gmail.com");
        request.setPassword("123456");

        ClienteResponse response = new ClienteResponse();

        when(clienteRepository.findById(id))
                .thenReturn(java.util.Optional.of(cliente));

        when(clienteRepository.findByCorreo(request.getCorreo()))
                .thenReturn(java.util.Optional.empty());

        when(passwordEncoder.encode(request.getPassword()))
                .thenReturn("passwordEncriptado");

        when(clienteRepository.save(cliente))
                .thenReturn(cliente);

        when(clienteMapper.toResponse(cliente))
                .thenReturn(response);


        // ACT
        ClienteResponse resultado =
                clienteService.actualizar(id, request);


        // ASSERT
        assertNotNull(resultado);

        assertEquals(
                "Cecilia Actualizada",
                cliente.getNombre()
        );

        assertEquals(
                "cecilia_nueva@gmail.com",
                cliente.getCorreo()
        );

        assertEquals(
                "passwordEncriptado",
                cliente.getPassword()
        );


        // VERIFY
        verify(clienteRepository)
                .findById(id);

        verify(clienteRepository)
                .findByCorreo(request.getCorreo());

        verify(passwordEncoder)
                .encode(request.getPassword());

        verify(clienteRepository)
                .save(cliente);

        verify(clienteMapper)
                .toResponse(cliente);
    }
    // =====================================================
    // PRUEBA 7
    // ACTUALIZAR CON CORREO DUPLICADO
    // =====================================================
    @Test
    void debeLanzarExcepcionAlActualizarConCorreoDuplicado() {

        // ARRANGE
        Long id = 1L;

        cliente clienteActual = new cliente();
        clienteActual.setId(id);
        clienteActual.setNombre("Cecilia");
        clienteActual.setCorreo("cecilia@gmail.com");

        cliente otroCliente = new cliente();
        otroCliente.setId(2L);
        otroCliente.setNombre("Paola");
        otroCliente.setCorreo("paola123@gmail.com");

        ClienteRequest request = new ClienteRequest();
        request.setNombre("Cecilia Actualizada");
        request.setCorreo("paola123@gmail.com");
        request.setPassword("123456");

        when(clienteRepository.findById(id))
                .thenReturn(java.util.Optional.of(clienteActual));

        when(clienteRepository.findByCorreo(request.getCorreo()))
                .thenReturn(java.util.Optional.of(otroCliente));


        // ACT + ASSERT
        assertThrows(
                CorreoDuplicadoException.class,
                () -> clienteService.actualizar(id, request)
        );


        // VERIFY
        verify(clienteRepository)
                .findById(id);

        verify(clienteRepository)
                .findByCorreo(request.getCorreo());

        verify(clienteRepository, never())
                .save(any(cliente.class));

        verifyNoInteractions(passwordEncoder);
    }
    // =====================================================
// PRUEBA 10
// ACTUALIZAR CLIENTE CONSERVANDO SU PROPIO CORREO
// =====================================================
    @Test
    void debeActualizarClienteConSuMismoCorreo() {

        // ARRANGE
        Long id = 1L;

        cliente cliente = new cliente();
        cliente.setId(id);
        cliente.setNombre("Cecilia");
        cliente.setCorreo("cecilia@gmail.com");
        cliente.setPassword("passwordViejo");
        cliente.setRol("CLIENTE");

        ClienteRequest request = new ClienteRequest();
        request.setNombre("Cecilia Actualizada");
        request.setCorreo("cecilia@gmail.com");
        request.setPassword("123456");

        ClienteResponse response = new ClienteResponse();

        when(clienteRepository.findById(id))
                .thenReturn(java.util.Optional.of(cliente));

        when(clienteRepository.findByCorreo(request.getCorreo()))
                .thenReturn(java.util.Optional.of(cliente));

        when(passwordEncoder.encode(request.getPassword()))
                .thenReturn("passwordEncriptado");

        when(clienteRepository.save(cliente))
                .thenReturn(cliente);

        when(clienteMapper.toResponse(cliente))
                .thenReturn(response);

        // ACT
        ClienteResponse resultado =
                clienteService.actualizar(id, request);

        // ASSERT
        assertNotNull(resultado);

        assertEquals(
                "Cecilia Actualizada",
                cliente.getNombre()
        );

        assertEquals(
                "cecilia@gmail.com",
                cliente.getCorreo()
        );

        assertEquals(
                "passwordEncriptado",
                cliente.getPassword()
        );

        // VERIFY
        verify(clienteRepository)
                .findById(id);

        verify(clienteRepository)
                .findByCorreo(request.getCorreo());

        verify(passwordEncoder)
                .encode(request.getPassword());

        verify(clienteRepository)
                .save(cliente);

        verify(clienteMapper)
                .toResponse(cliente);
    }
    // =====================================================
// PRUEBA 11
// ACTUALIZAR CLIENTE QUE NO EXISTE
// =====================================================
    @Test
    void debeLanzarExcepcionAlActualizarClienteQueNoExiste() {

        // ARRANGE
        Long id = 999L;

        ClienteRequest request = new ClienteRequest();
        request.setNombre("Cecilia");
        request.setCorreo("cecilia@gmail.com");
        request.setPassword("123456");

        when(clienteRepository.findById(id))
                .thenReturn(java.util.Optional.empty());

        // ACT + ASSERT
        assertThrows(
                ClienteNoEncontradoException.class,
                () -> clienteService.actualizar(id, request)
        );

        // VERIFY
        verify(clienteRepository)
                .findById(id);

        verify(clienteRepository, never())
                .findByCorreo(anyString());

        verify(clienteRepository, never())
                .save(any(cliente.class));

        verifyNoInteractions(
                passwordEncoder,
                clienteMapper
        );
    }
    // =====================================================
    // PRUEBA 8
    // ELIMINAR CLIENTE
    // =====================================================
    @Test
    void debeEliminarClienteCorrectamente() {

        // ARRANGE
        Long id = 1L;

        cliente cliente = new cliente();
        cliente.setId(id);
        cliente.setNombre("Cecilia");
        cliente.setCorreo("cecilia@gmail.com");

        when(clienteRepository.findById(id))
                .thenReturn(java.util.Optional.of(cliente));

        // ACT
        clienteService.eliminar(id);

        // VERIFY
        verify(clienteRepository)
                .findById(id);

        verify(clienteRepository)
                .delete(cliente);
    }

    // =====================================================
// PRUEBA 9
// ELIMINAR CLIENTE QUE NO EXISTE
// =====================================================
    @Test
    void debeLanzarExcepcionAlEliminarClienteQueNoExiste() {

        // ARRANGE
        Long id = 999L;

        when(clienteRepository.findById(id))
                .thenReturn(java.util.Optional.empty());

        // ACT + ASSERT
        assertThrows(
                ClienteNoEncontradoException.class,
                () -> clienteService.eliminar(id)
        );

        // VERIFY
        verify(clienteRepository)
                .findById(id);

        verify(clienteRepository, never())
                .delete(any(cliente.class));
    }

}