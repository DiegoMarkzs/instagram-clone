package br.edu.ifpb.instagram.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.sql.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class JwtUtilsTest {

     // Diego

    @Mock
    private Authentication authentication;

    @InjectMocks
    private JwtUtils jwtUtils = new JwtUtils();

   

    @Test
    void testGenerateToken() {

        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("usuarioTeste");

        String token = jwtUtils.generateToken(authentication);

        assertNotNull(token, "O token gerado não deve ser nulo");
        assertFalse(token.isEmpty(), "O token gerado não deve ser vazio");
        assertEquals("usuarioTeste", jwtUtils.getUsernameFromToken(token),
                "O nome de usuário extraído do token deve ser igual ao nome do usuário autenticado");
    }

    @Test
    void testValidateToken_Valid() {

        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("usuarioTeste");

        String tokenGerado = jwtUtils.generateToken(authentication);

        assertNotNull(tokenGerado, "O token não deve ser nulo");
        assertTrue(jwtUtils.validateToken(tokenGerado), "O token gerado deve ser válido");

    }

    @Test
    void testValidateToken_Invalid() {
        String tokenInvalido = "PeroPeruano";

        assertFalse(jwtUtils.validateToken(tokenInvalido),
                "O token inválido não deve ser validado");
    }

    @Test
    void testGetUsernameFromToken() throws InterruptedException {
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("usuarioTeste");

        String token = jwtUtils.generateToken(authentication);
        String nome = jwtUtils.getUsernameFromToken(token);

        assertNotNull(nome, "O nome de usuário extraído do token não deve ser nulo");
        assertEquals("usuarioTeste", nome,
                "O nome de usuário extraído do token deve ser igual ao nome do usuário autenticado");
        assertEquals("usuarioTeste", jwtUtils.getUsernameFromToken(token),
                "O nome de usuário extraído do token deve ser igual ao nome do usuário autenticado");

    }

    @Test
    void testValidateToken_Expired() throws InterruptedException {
        String tokenExpirado = Jwts.builder()
                .setExpiration(new Date(System.currentTimeMillis() + 1))
                .compact();

        // Serve pra dar tempo do token expirar
        Thread.sleep(5);

        assertFalse(jwtUtils.validateToken(tokenExpirado), "Um token expirado não deve ser validado");

    }

}
