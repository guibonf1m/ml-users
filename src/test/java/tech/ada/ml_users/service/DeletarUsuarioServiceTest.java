package tech.ada.ml_users.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;
import tech.ada.ml_users.exception.UsuarioNaoEncontradoException;
import tech.ada.ml_users.repository.UsuariosRepository;

public class DeletarUsuarioServiceTest {

    DeletarUsuarioService deletarUsuarioService;
    UsuariosRepository repository;
    BuscarUsuariosService buscarUsuarioService;


    @BeforeEach
    public void setUp() {
        repository = Mockito.mock(UsuariosRepository.class);
        buscarUsuarioService = Mockito.mock(BuscarUsuariosService.class);
        deletarUsuarioService = new DeletarUsuarioService(repository, buscarUsuarioService);
    }

    @DisplayName("Deve encontrar o usuário no banco de dados e excluí-lo")
    @Test
    void deveEncontrarUmUsuarioNoBancoDeDadosEExcluilo() {
        //Cenário
        Long id = 1L;

        //Execução
        deletarUsuarioService.deletarUsuarioPorId(id);

        //Verificação
        Mockito.verify(buscarUsuarioService, Mockito.times(1)).buscarUsuarioPorId(id);
        Mockito.verify(repository, Mockito.times(1)).deleteById(id);
        InOrder inOrder = Mockito.inOrder(buscarUsuarioService, repository);
        inOrder.verify(buscarUsuarioService).buscarUsuarioPorId(id);
        inOrder.verify(repository).deleteById(id);
    }

    @Test
    void deveLancarUmaExcecaoAoNaoEncontrarUmUsuario() {
        //Cenário
        Long id = 1L;

        Mockito.when(buscarUsuarioService.buscarUsuarioPorId(id))
                .thenThrow(new UsuarioNaoEncontradoException("Usuário com ID " + id + " não encontrado"));

        //Execução
        UsuarioNaoEncontradoException exception = Assertions.assertThrows(UsuarioNaoEncontradoException.class,
                () -> deletarUsuarioService.deletarUsuarioPorId(id));

        //Verificação
        Assertions.assertNotNull(exception);
        Assertions.assertNotNull(exception.getMessage());
    }

}
