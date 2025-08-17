package br.edu.ifpb.instagram.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

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
    void DadoUsuario_quandoDeletar_DeletarNoBanco(){

    }

    //READ
    //Beatriz
    void DadoUsuario_QuandoBuscarPorId_RetornarUsuario(){

    }




}
