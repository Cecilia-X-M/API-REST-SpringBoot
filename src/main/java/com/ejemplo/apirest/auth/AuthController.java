package com.ejemplo.apirest.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.ejemplo.apirest.entity.cliente;
import com.ejemplo.apirest.repository.ClienteRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

@RestController
@RequestMapping("/auth")
public class AuthController {

        private final AuthenticationManager authenticationManager;
        private final JwtService jwtService;

        public AuthController(AuthenticationManager authenticationManager,
                              JwtService jwtService) {
            this.authenticationManager = authenticationManager;
            this.jwtService = jwtService;
        }

        @PostMapping("/login")
        public LoginResponse login(@RequestBody LoginRequest request) {

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getCorreo(),
                            request.getPassword()
                    )
            );

            String token = jwtService.generarToken(authentication.getName());

            return new LoginResponse("Login correcto", authentication.getName(), token);
        }
    }