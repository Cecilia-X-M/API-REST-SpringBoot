package com.ejemplo.apirest.entity;
//clic derecho en entity >>New>>Java Class>>Name Cliente>>Enter
//LO SIGUIENTE ES PONER ANOTACIONES DE JPA

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Entity
@Data
@Table(name = "clientes")
public class cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, unique = true)
    private String correo;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String rol;

    private String telefono;

    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    private LocalDateTime fechaActualizacion;



    @OneToMany(mappedBy = "cliente")
    private List<Compra> compras;

    public cliente () {
    }

    @PrePersist
    protected void alCrear() {
        fechaCreacion = LocalDateTime.now();
        fechaActualizacion = fechaCreacion;
    }

    @PreUpdate
    protected void  alActualizar() {
        fechaActualizacion = LocalDateTime.now();
    }

    // Getters y setters
}
