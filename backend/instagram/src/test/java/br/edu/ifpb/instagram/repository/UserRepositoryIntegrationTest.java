package br.edu.ifpb.instagram.repository;

import br.edu.ifpb.instagram.model.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // Com ANY não funciona
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
    void DadoUsuario_quandoDeletar_DeletarNoBanco(){

    }

    //READ
    //Beatriz
    void DadoUsuario_QuandoBuscarPorId_RetornarUsuario(){

    }




}
