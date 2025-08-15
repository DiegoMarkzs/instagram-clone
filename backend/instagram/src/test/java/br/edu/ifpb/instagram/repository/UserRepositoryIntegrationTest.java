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
