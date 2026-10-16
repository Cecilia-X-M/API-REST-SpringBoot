package com.ejemplo.apirest.repository;

import com.ejemplo.apirest.entity.Compra;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompraRepository
        extends JpaRepository<Compra, Long> {
}