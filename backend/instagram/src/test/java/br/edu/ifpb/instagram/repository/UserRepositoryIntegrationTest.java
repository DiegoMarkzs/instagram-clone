import br.edu.ifpb.instagram.repository.UserRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class UserRepositoryIntegrationTest {


    @Autowired
    UserRepository userRepository;

    void DadoUsuario_QuandoBuscarPorId_RetornarUsuario(){
when(userService.findById(1L)).thenReturn(new UserDTO(1L, "maria", "maria@email.com"));

        mvc.perform(get("/api/users/1"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.id").value(1))
           .andExpect(jsonPath("$.name").value("maria"));
   
}

}