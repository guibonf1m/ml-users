package tech.ada.ml_users.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import tech.ada.ml_users.model.Usuario;
import tech.ada.ml_users.repository.UsuariosRepository;

import static org.mockito.Mockito.times;

public class AtualizarUsuarioServiceTest {

    AtualizarUsuarioService service;

    UsuariosRepository repository;
    BuscarUsuariosService buscarService;

    @BeforeEach
    public void setUp() {
        repository = Mockito.mock(UsuariosRepository.class);
        buscarService = Mockito.mock(BuscarUsuariosService.class);
        service = new AtualizarUsuarioService(repository, buscarService);
    }

    @Test
    void deveAtualizarUmUsuarioComSucesso() {
        //cenário
        Long id = 1L;
        Usuario usuarioQueSeraAtualizado = new Usuario(
                "Marcos", "marcos@gmail.com", 25, "1234568", "71901122"
        );

        //execução
        service.atualizarUsuario(usuarioQueSeraAtualizado, id);

        //validação
        Mockito.verify(buscarService, times(1)).buscarUsuarioPorId(id);
        Assertions.assertNotNull(usuarioQueSeraAtualizado.getId());
        Assertions.assertEquals(id, usuarioQueSeraAtualizado.getId());
        Assertions.assertNotNull(usuarioQueSeraAtualizado.getDataAtualizacao());
        Mockito.verify(repository, times(1)).save(usuarioQueSeraAtualizado);
    }

}
