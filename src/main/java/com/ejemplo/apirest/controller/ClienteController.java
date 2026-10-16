//AQUI EL CONTROLADOR RESPONDERA A LAS PETICIONES HTTP

package com.ejemplo.apirest.controller;

import com.ejemplo.apirest.dto.ClienteRequest;
import com.ejemplo.apirest.dto.ClienteResponse;
import com.ejemplo.apirest.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/clientes")
@Tag(
        name = "Clientes",
        description = "Operaciones para adminstrar clientes"
)

public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }


    // =====================================================
    // POST - CREAR CLIENTE
    // =====================================================

    @PostMapping
    @Operation(
            summary = "Crear cliente",
            description = "Crea un nuevo cliente en la base de datos"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Cliente creado correctamente"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos inválidos"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "El correo ya está registrado"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor"
            )
    })
    public ClienteResponse crear(
            @Valid @RequestBody ClienteRequest request) {

        return clienteService.crear(request);
    }

    // =====================================================
    // GET - BUSCAR TODOS
    // =====================================================

    @GetMapping
    @Operation(
            summary = "Obtener todos los clientes",
            description = "Obtiene la lista completa de clientes registrados"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de clientes obtenida correctamente"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor"
            )
    })
    public List<ClienteResponse> buscarTodos() {

        return clienteService.buscarTodos();
    }


    // =====================================================
    // GET - BUSCAR POR ID
    // =====================================================

    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener cliente por ID",
            description = "Busca un cliente específico utilizando su ID"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Cliente encontrado correctamente"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Cliente no encontrado"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor"
            )
    })
    public ClienteResponse buscarPorId(
            @PathVariable Long id) {

        return clienteService.buscarPorId(id);
    }


    // =====================================================
    // PUT - ACTUALIZAR CLIENTE
    // =====================================================

    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar cliente",
            description = "Actualiza los datos de un cliente existente"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Cliente actualizado correctamente"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos inválidos"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Cliente no encontrado"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "El correo ya pertenece a otro cliente"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor"
            )
    })
    public ClienteResponse actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ClienteRequest request) {

        return clienteService.actualizar(id, request);
    }


    // =====================================================
    // DELETE - ELIMINAR CLIENTE
    // =====================================================

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar cliente",
            description = "Elimina un cliente existente utilizando su ID"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Cliente eliminado correctamente"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Cliente no encontrado"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor"
            )
    })
    public String eliminar(
            @PathVariable Long id) {

        clienteService.eliminar(id);

        return "Cliente eliminado correctamente";
    }
}
