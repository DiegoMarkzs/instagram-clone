import br.edu.ifpb.instagram.repository.UserRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class UserRepositoryIntegrationTest {


    @Autowired
    UserRepository userRepository;

    void DadoUsuario_QuandoBuscarPorId_RetornarUsuario(){
        UserEntity foundUser = userRepository.findById(savedUser.getId())
    .orElseThrow(() -> new AssertionError("O usuário deveria ser encontrado pelo ID."));

        assertEquals(savedUser.getId(), foundUser.getId());
        assertEquals("maria", foundUser.getUsername());
        assertEquals("maria@email.com", foundUser.getEmail());
        assertEquals("Maria da Silva", foundUser.getFullName());
    }

}
