package com.ejemplo.apirest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ejemplo.apirest.entity.cliente;
import java.util.List;
import java.util.Optional;

public interface ClienteRepository
       extends JpaRepository<cliente, Long> {

    Optional<cliente> findByCorreo(String correo);

    Optional<cliente> findByNombre(String nombre);

    boolean existsByCorreo(String correo);

    List<cliente> findByNombreContaining(String nombre);
}



//====comentarios====
//CON ESTA INTERFAZ YA PUEDO GUARDAR, BUSCAR,
//ACTUALIZAR Y ELIMINAR SON ESCRIBIR SQL.
//save()
//findAll()
//finf¿dById()
//delete()
//count