package br.edu.ifpb.instagram.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import br.edu.ifpb.instagram.model.dto.UserDto;
import br.edu.ifpb.instagram.model.entity.UserEntity;
import br.edu.ifpb.instagram.repository.UserRepository;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @MockitoBean
    UserRepository userRepository; // Repositório simulado

    @Autowired
    UserServiceImpl userService; // Classe sob teste

    @Test
    void testFindById_ReturnsUserDto() {
        // Configurar o comportamento do mock
        Long userId = 1L;

        UserEntity mockUserEntity = new UserEntity();
        mockUserEntity.setId(userId);
        mockUserEntity.setFullName("Paulo Pereira");
        mockUserEntity.setEmail("paulo@ppereira.dev");

        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUserEntity));

        // Executar o método a ser testado
        UserDto userDto = userService.findById(userId);

        // Verificar o resultado
        assertNotNull(userDto);
        assertEquals(mockUserEntity.getId(), userDto.id());
        assertEquals(mockUserEntity.getFullName(), userDto.fullName());
        assertEquals(mockUserEntity.getEmail(), userDto.email());

        // Verificar a interação com o mock
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testFindById_ThrowsExceptionWhenUserNotFound() {
        // Configurar o comportamento do mock
        Long userId = 999L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Executar e verificar a exceção
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.findById(userId);
        });

        assertEquals("User not found", exception.getMessage());

        // Verificar a interação com o mock
        verify(userRepository, times(1)).findById(userId);
    }

     //faça no minimo 2 testes

    //@Teste
    //Carina
    void create_MODELO(){

    }
    
    //@Teste
    //Winiicius
    void update_MODELO(){

    }

    //@Teste
    //Beatriz
    void findAll_MODELO(){

    }

    @Test
    void testDeleteUser_WhenExists_DeletesUser() {
        Long userId = 1L;
        UserEntity mockUserEntity = new UserEntity();
        mockUserEntity.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUserEntity));

        userService.deleteUser(userId);

        verify(userRepository, times(1)).delete(mockUserEntity);
    }

    @Test
    void testDeleteUser_WhenNotFound_ThrowsException() {
        Long userId = 999L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.deleteUser(userId);
        });

        assertEquals("User not found", exception.getMessage());
        verify(userRepository, never()).delete(any());
    }

    @Test
    void testDeleteUser_WhenRepositoryFails_ThrowsException() {
        Long userId = 2L;
        UserEntity mockUserEntity = new UserEntity();
        mockUserEntity.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUserEntity));
        doThrow(new RuntimeException("DB error")).when(userRepository).delete(mockUserEntity);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.deleteUser(userId);
        });

        assertEquals("DB error", exception.getMessage());
        verify(userRepository, times(1)).delete(mockUserEntity);
    }

    @Test
    void testDeleteUser_WhenIdIsNull_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            userService.deleteUser(null);
        });

        verify(userRepository, never()).findById(any());
        verify(userRepository, never()).delete(any());
    }
}
