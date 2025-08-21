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
    //@Test
    //Carina
    void DadoUsuario_quandoSalvar_PersistirNoBanco(){

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

        assertThat(updated.getFullName()).isEqualTo("New Name");
        assertThat(updated.getUsername()).isEqualTo("newuser");
        assertThat(updated.getEmail()).isEqualTo("new@example.com");
        assertThat(updated.getEncryptedPassword()).isEqualTo("newpass");
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

    // READ
    // Beatriz
    void DadoUsuario_QuandoBuscarPorId_RetornarUsuario() {

    }
}