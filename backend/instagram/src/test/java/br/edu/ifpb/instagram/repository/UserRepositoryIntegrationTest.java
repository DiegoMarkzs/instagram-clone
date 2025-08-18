package br.edu.ifpb.instagram.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import br.edu.ifpb.instagram.model.entity.UserEntity;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
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
    //@Test
    //Winiicius
    void DadoUsuario_quandoAtualizar_AtualizarNoBanco(){

    }


    //DELETE
    //Yasmiiin
    void DadoUsuario_quandoDeletar_DeletarNoBanco(){

    }

    //READ
    //Beatriz
    void DadoUsuario_QuandoBuscarPorId_RetornarUsuario(){

    }




}
