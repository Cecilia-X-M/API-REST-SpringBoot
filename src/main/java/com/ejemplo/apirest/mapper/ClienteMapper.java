package com.ejemplo.apirest.mapper;
//MÉTODOS PARA CONVERTIR CLIENTE Y CLIENTEDTO

import com.ejemplo.apirest.dto.ClienteRequest;
import com.ejemplo.apirest.dto.ClienteResponse;
import com.ejemplo.apirest.entity.cliente;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    cliente toEntity(ClienteRequest request);

    ClienteResponse toResponse(cliente cliente);
}