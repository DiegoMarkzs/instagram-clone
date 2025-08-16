package br.edu.ifpb.instagram.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        UserEntity mockUserEntity1 = new UserEntity();
        mockUserEntity1.setId(1L);
        mockUserEntity1.setFullName("Beatriz");
        mockUserEntity1.setEmail("beatriz.z@gmail.com");
    
        UserEntity mockUserEntity2 = new UserEntity();
        mockUserEntity2.setId(2L);
        mockUserEntity2.setFullName("Maria Silva");
        mockUserEntity2.setEmail("maria@silva.dev");
    
        List<UserEntity> users = List.of(mockUserEntity1, mockUserEntity2);
    
        when(userRepository.findAll()).thenReturn(users);
    
        List<UserDto> result = userService.findAll();
    
        assertNotNull(result);
        assertEquals(2, result.size());
    
        assertEquals(mockUserEntity1.getId(), result.get(0).id());
        assertEquals(mockUserEntity1.getFullName(), result.get(0).fullName());
        assertEquals(mockUserEntity1.getEmail(), result.get(0).email());
    
        assertEquals(mockUserEntity2.getId(), result.get(1).id());
        assertEquals(mockUserEntity2.getFullName(), result.get(1).fullName());
        assertEquals(mockUserEntity2.getEmail(), result.get(1).email());
    
        verify(userRepository, times(1)).findAll();
    }

    //@Teste
    //Yasmiiiin
    void delete_MODELO(){

    }

    
    




    
}
