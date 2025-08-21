package br.edu.ifpb.instagram.repository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import br.edu.ifpb.instagram.model.entity.UserEntity;
import br.edu.ifpb.instagram.service.UserService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import br.edu.ifpb.instagram.model.entity.UserEntity;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import br.edu.ifpb.instagram.model.entity.UserEntity;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

//Essas annotations estão dando erro
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // Com ANY não funciona

@SpringBootTest

// Serve pra remover os dados do banco depois do teste ser realizado
@Transactional
public class UserRepositoryIntegrationTest {

    @Autowired
    UserRepository userRepository;

    //CREATE
    @Test
    //Carina
    void DadoUsuario_quandoSalvar_PersistirNoBanco(){

        UserEntity user = new UserEntity();
        user.setUsername("new_name");
        user.setEmail("new@email.com");
        user.setFullName("New Name");
        user.setEncryptedPassword("encodedPassword");

        UserEntity savedUser = userRepository.save(user);

        assertNotNull(savedUser.getId());
        assertEquals(user.getUsername(), savedUser.getUsername());
        assertEquals(user.getEmail(), savedUser.getEmail());
        assertEquals(user.getFullName(), savedUser.getFullName());
    }

    @Test
    void DadoUsuarioComEmailExistente_quandoSalvar_DeveLancarExcecao() {
        UserEntity user1 = new UserEntity();
        user1.setUsername("user1");
        user1.setEmail("duplicate@email.com");
        user1.setFullName("User One");
        user1.setEncryptedPassword("encodedPassword1");
        userRepository.save(user1);

        UserEntity user2 = new UserEntity();
        user2.setUsername("user2");
        user2.setEmail("duplicate@email.com"); // mesmo email
        user2.setFullName("User Two");
        user2.setEncryptedPassword("encodedPassword2");
        userRepository.save(user2);

        assertThrows(DataIntegrityViolationException.class, () -> {
            userRepository.save(user2);
        });
    }
    
    @Test
    void DadoUsuarioComFullNameNulo_quandoSalvar_DeveLancarExcecao() {
        UserEntity user = new UserEntity();
        user.setUsername("user1");
        user.setEmail("duplicate@email.com");
        user.setFullName(null);
        user.setEncryptedPassword("encodedPassword1");

        assertThrows(DataIntegrityViolationException.class, () -> {
            userRepository.save(user);
        });
    }

    //UPDATE
    //Winiicius
    @Test
    void updateUser_shouldUpdateFieldsCorrectly() {

        UserEntity user = new UserEntity();
        user.setFullName("Old Name");
        user.setUsername("olduser");
        user.setEmail("old@example.com");
        user.setEncryptedPassword("oldpass");
        user = userRepository.save(user);

        user.setFullName("New Name");
        user.setUsername("newuser");
        user.setEmail("new@example.com");
        user.setEncryptedPassword("newpass");
        UserEntity updated = userRepository.save(user);
        }
        
        //DELETE
        //Yasmiiin 
        @Test
        void deleteUser_existingUser_removesFromDatabase() {
            UserEntity user = new UserEntity();
        user.setFullName("Test User");
        user.setUsername("testuser");
        user.setEmail("testuser@example.com");
        user.setEncryptedPassword("encrypted");

        user = userRepository.save(user);
        Long userId = user.getId();
        
        assertTrue(userRepository.existsById(userId));
        
        userRepository.deleteById(userId);
        
        assertFalse(userRepository.existsById(userId));
    }
    
    //Beatriz
    void DadoUsuario_QuandoBuscarPorId_RetornarUsuario(){
    
    }
}