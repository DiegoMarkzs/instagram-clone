package br.edu.ifpb.instagram.controller;

import br.edu.ifpb.instagram.model.dto.UserDto;
import br.edu.ifpb.instagram.model.entity.UserEntity;
import br.edu.ifpb.instagram.model.request.UserDetailsRequest;
import br.edu.ifpb.instagram.model.response.UserDetailsResponse;
import br.edu.ifpb.instagram.service.UserService;
import br.edu.ifpb.instagram.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.edu.ifpb.instagram.repository.UserRepository;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserRepository userRepository;

    @MockitoBean
    private UserServiceImpl userService;
    
    //Winiicius
    @Test
    @WithMockUser(username = "johndoe", roles = { "USER" })
    void testUpdateUser() throws Exception {
        UserDto updatedUserDto = new UserDto(1L, "John Doe Updated", "johndoe", "johndoe@example.com", null, null);

        UserDetailsResponse response = new UserDetailsResponse(1L, "John Doe Updated", "johndoe",
                "johndoe@example.com");

        Mockito.when(userService.updateUser(Mockito.any(UserDto.class))).thenReturn(updatedUserDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/users")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(response)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.fullName").value(updatedUserDto.fullName()));

        Mockito.verify(userService, Mockito.times(1)).updateUser(Mockito.any(UserDto.class));
    }

    // Yasmin

     @Test
    @WithMockUser(username = "johndoe", roles = { "USER" })
    void testDeleteUser() throws Exception {
        UserEntity user = new UserEntity();
        user.setFullName("Test User");
        user.setUsername("testuser");
        user.setEmail("testuser@example.com");
        user.setEncryptedPassword("encrypted");
        user = userRepository.save(user);
        Long userId = user.getId();

        assertTrue(userRepository.existsById(userId));

        mockMvc.perform(MockMvcRequestBuilders.delete("/users/{id}", userId)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("user was deleted!"));

        assertFalse(userRepository.existsById(userId));
    }

}
