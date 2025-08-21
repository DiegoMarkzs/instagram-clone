package br.edu.ifpb.instagram.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.edu.ifpb.instagram.model.request.LoginRequest;
import br.edu.ifpb.instagram.model.request.UserDetailsRequest;
import br.edu.ifpb.instagram.service.impl.AuthServiceImpl;
import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
public class JwtUtilControllerIntegrationTest {

    //Diego

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AuthServiceImpl authService;

    @MockitoBean
    private Authentication authentication;

    

    @Test
    void dadoUsuario_quandoLogar_retornarToken() throws Exception {
        LoginRequest loginRequest = new LoginRequest("usuarioTeste", "senha123");
        String token = "tokenDahora";

        when(authService.authenticate(any(LoginRequest.class)))
                .thenReturn(token);

        mockMvc.perform(post("/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("usuarioTeste")))
                .andExpect(jsonPath("$.token", is(token)));
    }

    @Transactional
    @Test
    void dadoUsuario_quandoCriarConta_retornarUsuarioCriado() throws Exception {
        long numeroAleatorio = System.currentTimeMillis();
        String numeroEmail = String.valueOf(numeroAleatorio);

        UserDetailsRequest userDetailsRequest = new UserDetailsRequest(
            null,
            "albertwesker" + numeroEmail + "@gmail.com",
            "umbrella123",
            "Albert wesker"+numeroEmail,
            "Albertowyskas"+numeroEmail
        );

         mockMvc.perform(post("/auth/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userDetailsRequest)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.email", is("albertwesker" + numeroEmail + "@gmail.com")))
            .andExpect(jsonPath("$.fullName", is("Albert wesker"+numeroEmail)))
            .andExpect(jsonPath("$.username", is("Albertowyskas"+numeroEmail)));

        
      

        }

}
