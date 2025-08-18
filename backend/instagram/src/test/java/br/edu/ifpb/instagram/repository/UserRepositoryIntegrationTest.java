package br.edu.ifpb.instagram.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import br.edu.ifpb.instagram.repository.UserRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class UserRepositoryIntegrationTest {


    @Autowired
    UserRepository userRepository;

    //@Test
    void DadoUsuario_quandoSalvar_PersistirNoBanco(){

    }




}
