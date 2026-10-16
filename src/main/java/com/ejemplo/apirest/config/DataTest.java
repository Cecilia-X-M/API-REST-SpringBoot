package com.ejemplo.apirest.config;

import com.ejemplo.apirest.entity.cliente;
import com.ejemplo.apirest.repository.ClienteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


public class DataTest implements CommandLineRunner {

    private final ClienteRepository clienteRepository;

    public DataTest(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public void run(String... args) {

        System.out.println("===== PRUEBA REPOSITORY =====");

        // CLIENTE 1
        cliente cliente1 = new cliente();

        cliente1.setNombre("Cecilia");
        cliente1.setCorreo("cecilia@gmail.com");
        cliente1.setPassword("123456");
        cliente1.setRol("ADMIN");

        // CLIENTE 2
        cliente cliente2 = new cliente();

        cliente2.setNombre("Paola");
        cliente2.setCorreo("paola123@gmail.com");
        cliente2.setPassword("34567");
        cliente2.setRol("CLIENTE");

        // CLIENTE 3
        cliente cliente3 = new cliente();

        cliente3.setNombre("Luis");
        cliente3.setCorreo("luis99@gmail.com");
        cliente3.setPassword("741852");
        cliente3.setRol("CLIENTE");

        // CLIENTE 4
        cliente cliente4 = new cliente();

        cliente3.setNombre("Sebastian");
        cliente3.setCorreo("sebas99@gmail.com");
        cliente3.setPassword("12345678");
        cliente3.setRol("CLIENTE");

        // CLIENTE 5
        cliente cliente5 = new cliente();

        cliente3.setNombre("Carlos");
        cliente3.setCorreo("carlos2026@gmail.com");
        cliente3.setPassword("987456");
        cliente3.setRol("CLIENTE");

        // GUARDAR CLIENTES
        guardarSiNoExiste(cliente1);
        guardarSiNoExiste(cliente2);
        guardarSiNoExiste(cliente3);
        guardarSiNoExiste(cliente4);
        guardarSiNoExiste(cliente5);

        System.out.println("===== FIN PRUEBA REPOSITORY =====");
    }

    private void guardarSiNoExiste(cliente cliente) {

        if (clienteRepository.existsByCorreo(cliente.getCorreo())) {

            System.out.println(
                    "El cliente ya existe: "
                            + cliente.getCorreo()
            );

        } else {

            cliente guardado =
                    clienteRepository.save(cliente);

            System.out.println(
                    "Cliente guardado: "
                            + guardado.getId()
            );
        }
    }
}
