package com.ejemplo.apirest.service;

import com.ejemplo.apirest.entity.cliente;
import com.ejemplo.apirest.repository.ClienteRepository;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ClienteUserDetailsService implements UserDetailsService {

    private final ClienteRepository clienteRepository;

    public ClienteUserDetailsService(
            ClienteRepository clienteRepository) {

        this.clienteRepository = clienteRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String correo)
            throws UsernameNotFoundException {

        cliente cliente = clienteRepository
                .findByCorreo(correo)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "Cliente no encontrado"
                        )
                );

        return User.builder()
                .username(cliente.getCorreo())
                .password(cliente.getPassword())
                .roles(cliente.getRol())
                .build();
    }
}