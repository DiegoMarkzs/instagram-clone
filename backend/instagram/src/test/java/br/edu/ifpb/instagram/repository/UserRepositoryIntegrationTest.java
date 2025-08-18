import br.edu.ifpb.instagram.repository.UserRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class UserRepositoryIntegrationTest {


    @Autowired
    UserRepository userRepository;

    //@Test
    void DadoUsuario_quandoSalvar_PersistirNoBanco(){

    }   

    void DadoUsuario_QuandoBuscarPorId_RetornarUsuario(){
User usuario = new User();
        usuario.setNome("Maria");
        usuario.setEmail("maria@email.com");
        usuario.setSenha("123456");
        usuario = userRepository.save(usuario);

        Optional<User> usuarioEncontrado = userRepository.findById(usuario.getId());
        assertTrue(usuarioEncontrado.isPresent());
        assertEquals("Maria", usuarioEncontrado.get().getNome());
        assertEquals("maria@email.com", usuarioEncontrado.get().getEmail());

}

}