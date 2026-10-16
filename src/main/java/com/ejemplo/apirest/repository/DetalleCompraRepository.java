package com.ejemplo.apirest.repository;

import com.ejemplo.apirest.entity.DetalleCompra;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetalleCompraRepository
    extends JpaRepository<DetalleCompra, Long> {

}
