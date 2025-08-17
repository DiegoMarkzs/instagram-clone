package br.edu.ifpb.instagram.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
class JwtUtilsTest {

    @MockitoBean
    private Authentication authentication;

    @InjectMocks
    private JwtUtils jwtUtils = new JwtUtils(); 


    //Minha pessoa


  //  @Test
    void testGenerateToken() {

        String token = jwtUtils.generateToken(authentication);
        when(jwtUtils.getUsernameFromToken(token)).thenReturn("usuarioTeste");

        assertNotNull(token, "O token gerado não deve ser nulo");
        assertFalse(token.isEmpty(), "O token gerado não deve ser vazio");
        assertEquals("usuarioTeste", jwtUtils.getUsernameFromToken(token), "O nome de usuário extraído do token deve ser igual ao nome do usuário autenticado");
   
        verify(jwtUtils, times(1)).getUsernameFromToken(token);

    }

   // @Test
    void testValidateToken_Valid() {
      
    
        String tokenValido = "cubano2013";
        String  tokenGerado = jwtUtils.generateToken(authentication);

        when(jwtUtils.validateToken(tokenGerado)).thenReturn(true);

        assertEquals(tokenValido, tokenGerado, "O token gerado deve ser igual ao token válido");
        assertFalse(jwtUtils.validateToken(tokenGerado), "O token válido deve ser validado");
        
        verify(jwtUtils, times(1)).validateToken(tokenGerado);
        
    }

   // @Test
    void testValidateToken_Invalid() {
        String tokenInvalido = "PeroPeruano";
        String tokenGerado = jwtUtils.generateToken(authentication);

        when(jwtUtils.validateToken(tokenGerado)).thenReturn(false);

        assertFalse(jwtUtils.validateToken(tokenInvalido), "O token inválido não deve ser validado");
        assertNotEquals(tokenInvalido, tokenGerado, "O token inválido não deve ser igual ao token gerado");
        verify(jwtUtils, times(1)).validateToken(tokenGerado);
       
    }

   // @Test
    void testGetUsernameFromToken() {
        when(authentication.getName()).thenReturn(null);
        String token = jwtUtils.generateToken(authentication);
        String nome = jwtUtils.getUsernameFromToken(token);
        assertNotNull(nome, "O nome de usuário extraído do token não deve ser nulo");
        assertEquals("usuarioTeste", nome, "O nome de usuário extraído do token deve ser igual ao nome do usuário autenticado");
        assertEquals("usuarioTeste", jwtUtils.getUsernameFromToken(token), "O nome de usuário extraído do token deve ser igual ao nome do usuário autenticado");
       
    }

    //@Test
    void testValidateToken_Expired() {
        String tokenExpirado = jwtUtils.generateToken(authentication);
        when(jwtUtils.validateToken(tokenExpirado)).thenReturn(false);
        verify(jwtUtils, times(1)).validateToken(tokenExpirado);
        
        
    
}






}
