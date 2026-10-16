package com.ejemplo.apirest;
// =====================================================
// ESTA ES UNA PRUEBA INTEGRACION JUNIT Y MOCKITO
// =====================================================
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class ClienteControllerIntegrationTest {

    @Test
    void contextoCargaCorrectamente() {
    }
}
