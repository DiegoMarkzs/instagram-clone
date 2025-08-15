package test.java.br.edu.ifpb.instagram.controller;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;


    //@Test
    void dadoUsuario_quandoCriar_RetornarUsuarioCriado() throws Exception{

    }








}
