package br.edu.ifpb.instagram.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.sql.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import br.edu.ifpb.instagram.model.request.LoginRequest;
import br.edu.ifpb.instagram.service.impl.AuthServiceImpl;
import br.edu.ifpb.instagram.service.impl.UserDetailsServiceImpl;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class JwtUtilsTest {

    // Diego

    @Mock
    private Authentication authentication;

    @InjectMocks
    private JwtUtils jwtUtils = new JwtUtils();

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

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

    @Test
    void testValidateToken_InvalidSignature() {
        String tokenComAssinaturaInvalida = Jwts.builder()
                .setSubject("usuarioTeste")
                .signWith(Keys.secretKeyFor(SignatureAlgorithm.HS256))
                .compact();

        String tokenAlterado = tokenComAssinaturaInvalida.substring(0, tokenComAssinaturaInvalida.length() - 1) + "X";

        assertFalse(jwtUtils.validateToken(tokenAlterado), "Token com assinatura inválida não deve ser validado");
    }

    @Test
    void testDoFilterInternal_ValidToken() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);

        UserDetailsServiceImpl userDetailsServiceImpl = mock(UserDetailsServiceImpl.class);
        UserDetails userDetails = mock(UserDetails.class);

        when(userDetails.getUsername()).thenReturn("usuarioTeste");
        when(userDetailsServiceImpl.loadUserByUsername("usuarioTeste")).thenReturn(userDetails);

        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtUtils, userDetailsServiceImpl);

        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("usuarioTeste");
        String token = jwtUtils.generateToken(authentication);

        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);

        assertNotNull(SecurityContextHolder.getContext().getAuthentication(),
                "A autenticação deve ser definida no contexto de segurança");
        assertEquals("usuarioTeste", SecurityContextHolder.getContext().getAuthentication().getName(),
                "O nome do usuário autenticado no contexto deve ser igual ao nome do usuário autenticado");
    }
    
    @Test
void givenValidCredentials_whenAuthenticate_thenReturnToken() {
    
    AuthenticationManager authenticationManager = mock(AuthenticationManager.class);
    JwtUtils jwtUtils = mock(JwtUtils.class);

    
    LoginRequest loginRequest = new LoginRequest("usuarioTeste", "senha123");

    
    Authentication authentication = mock(Authentication.class);
    when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
            .thenReturn(authentication);

   
    when(jwtUtils.generateToken(authentication)).thenReturn("tokenValido");

    AuthServiceImpl authService = new AuthServiceImpl(authenticationManager, jwtUtils);

  
    String token = authService.authenticate(loginRequest);

    assertNotNull(token);
    assertEquals("tokenValido", token);
}

}
