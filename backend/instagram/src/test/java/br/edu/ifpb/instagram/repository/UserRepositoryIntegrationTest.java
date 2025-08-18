package br.edu.ifpb.instagram.repository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import br.edu.ifpb.instagram.model.entity.UserEntity;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import br.edu.ifpb.instagram.model.entity.UserEntity;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

//Essas annotations estão dando erro
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)

@SpringBootTest

// Serve pra remover os dados do banco depois do teste ser realizado
@Transactional
public class UserRepositoryIntegrationTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private EntityManager entityManager;

    // CREATE
    @Test
    // Carina
    void DadoUsuario_quandoSalvar_PersistirNoBanco() {

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
        //alterei alguns nomes porque tava dando erro de duplicidade com dados antigos, nao sei como
        UserEntity user1 = new UserEntity();
        user1.setUsername("usuario1");
        user1.setEmail("duplicate1@email.com");
        user1.setFullName("User One");
        user1.setEncryptedPassword("encodedPassword1");
        userRepository.save(user1);
        entityManager.flush(); // força o INSERT

        UserEntity user2 = new UserEntity();
        user2.setUsername("usuario2");
        user2.setEmail("duplicate1@email.com"); // mesmo email
        user2.setFullName("User Two");
        user2.setEncryptedPassword("encodedPassword2");
        // Essa logica deve ficar apenas no assertThrows, se não da erro antes do esperado
        // userRepository.save(user2);

        //O tipo de excessão mostrado no docker foi de ConstraintViolationException
        assertThrows(ConstraintViolationException.class, () -> {
            userRepository.save(user2);
            entityManager.flush(); // o flush serve pra forçar o envio de todas as alterações pendentes pro BD
        });



    }

    //Deveria ser um teste de READ, no final da classe tem o metodo
    //Beatriz
    //@Test
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

    // UPDATE
    // @Test
    // Winiicius
    void DadoUsuario_quandoAtualizar_AtualizarNoBanco() {

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