package br.edu.ifpb.instagram.repository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import br.edu.ifpb.instagram.model.entity.UserEntity;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class UserRepositoryIntegrationTest {

    @Autowired
    UserRepository userRepository;

    //CREATE
    //@Test
    //Carina
    void DadoUsuario_quandoSalvar_PersistirNoBanco(){

    }

    //UPDATE
    //@Test
    //Winiicius
    void DadoUsuario_quandoAtualizar_AtualizarNoBanco(){

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

    //READ
    //Beatriz
    void DadoUsuario_QuandoBuscarPorId_RetornarUsuario(){

    }
}