package br.edu.ifpb.instagram.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import br.edu.ifpb.instagram.model.dto.UserDto;
import br.edu.ifpb.instagram.model.entity.UserEntity;
import br.edu.ifpb.instagram.repository.UserRepository;

@SpringBootTest
public class UserServiceImplTest {

    @Mock
    UserRepository userRepository; // Repositório simulado

    @InjectMocks
    UserServiceImpl userService; // Classe sob teste

    @Mock
    private PasswordEncoder passwordEncoder;

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

        assertEquals("User not found with id: " + userId, exception.getMessage());

        // Verificar a interação com o mock
        verify(userRepository, times(1)).findById(userId);
    }

    // faça no minimo 2 testes

    // @Teste
    // Carina
    void create_MODELO() {

    }

    @Test
    void updateUser_withValidDataAndPassword_shouldUpdateAndReturnDto() {

        var existingUser = new UserEntity();
        existingUser.setId(1L);
        existingUser.setFullName("Old Name");
        existingUser.setUsername("old_username");
        existingUser.setEmail("old@email.com");
        existingUser.setEncryptedPassword("oldEncryptedPassword");

        UserDto userDto = new UserDto(
                1L,
                "New Name",
                "new_username",
                "new@email.com",
                "newPassword",
                null);

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.encode("newPassword")).thenReturn("encodedPassword");

        when(userRepository.save(any(UserEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        UserDto result = userService.updateUser(userDto);

        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("New Name", result.fullName());
        assertEquals("new_username", result.username());
        assertEquals("new@email.com", result.email());

        assertEquals(null, result.encryptedPassword());
        assertEquals(null, result.password());

        verify(passwordEncoder).encode("newPassword");
        verify(userRepository).save(any(UserEntity.class));
    }

    @Test
    void updateUser_withValidDataAndEmptyPassword_shouldUpdateOtherFieldsOnly() {
        var existingUser = new UserEntity();
        existingUser.setId(1L);
        existingUser.setFullName("Old Name");
        existingUser.setUsername("old_username");
        existingUser.setEmail("old@email.com");
        existingUser.setEncryptedPassword("oldEncryptedPassword");

        UserDto userDto = new UserDto(
                1L,
                "New Name",
                "new_username",
                "new@email.com",
                null,
                null);

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(UserEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        UserDto result = userService.updateUser(userDto);

        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("New Name", result.fullName());
        assertEquals("new_username", result.username());
        assertEquals("new@email.com", result.email());

        assertNull(result.password());
        assertNull(result.encryptedPassword());

        verify(userRepository).findById(1L);
        verify(userRepository).save(any(UserEntity.class));

        verify(passwordEncoder, times(0)).encode(any());
    }

    @Test
    void updateUser_shouldThrowExceptionWhenUserDtoIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.updateUser(null);
        });

        assertEquals("UserDto or UserDto.id must not be null", exception.getMessage());

        verifyNoInteractions(userRepository);
        verifyNoInteractions(passwordEncoder);
    }

    @Test
    void updateUser_shouldThrowExceptionWhenUserIdIsNull() {
        UserDto userDto = new UserDto(
                null,
                "New Name",
                "new_username",
                "new@email.com",
                "newPassword",
                null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.updateUser(userDto);
        });

        assertEquals("UserDto or UserDto.id must not be null", exception.getMessage());

        verifyNoInteractions(userRepository);
        verifyNoInteractions(passwordEncoder);
    }

    @Test
    void updateUser_shouldThrowExceptionWhenUserNotFound() {
        Long userId = 999L;

        UserDto userDto = new UserDto(
                userId,
                "New Name",
                "new_username",
                "new@email.com",
                "newPassword",
                null);

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.updateUser(userDto);
        });

        assertEquals("User not found with id: " + userId, exception.getMessage());

        verify(userRepository).findById(userId);

        verifyNoInteractions(passwordEncoder);
    }

    @Test
    // Beatriz
    void findAll_MODELO() {
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

    // Yasmiiiin

    @Test
    void testDeleteUser_WhenExists_DeletesUser() {
        Long userId = 1L;
        when(userRepository.existsById(userId)).thenReturn(true);

        userService.deleteUser(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void testDeleteUser_WhenNotFound_ThrowsException() {
        Long userId = 999L;
        when(userRepository.existsById(userId)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.deleteUser(userId);
        });

        assertEquals("User not found with id: " + userId, exception.getMessage());
        verify(userRepository, never()).deleteById(any());
    }

    @Test
    void testDeleteUser_WhenRepositoryFails_ThrowsException() {
        Long userId = 2L;
        when(userRepository.existsById(userId)).thenReturn(true);
        doThrow(new RuntimeException("DB error")).when(userRepository).deleteById(userId);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.deleteUser(userId);
        });

        assertEquals("DB error", exception.getMessage());
        verify(userRepository, times(1)).deleteById(userId);
    }
}
