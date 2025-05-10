package tech.ada.ml_users.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import tech.ada.ml_users.model.Usuario;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ActiveProfiles("integration-test")
@DataJpaTest
public class UsuariosRepositoryTest {

    @Autowired
    UsuariosRepository repository;

    Usuario usuario;

    @BeforeEach
    public void setUp() {
        usuario = new Usuario();
        repository.save(new Usuario("Rodolfo", "rodolfo@email.com", 30, "1234567", "71901120"));
        repository.save(new Usuario("Yuri", "yuri@email.com", 26, "1234567", "71901122"));
        repository.save(new Usuario("Helena", "helena@email.com", 4, "1234567", "71901123"));
    }

    @Test
    void deveSalvarUmUsuarioValidoComSucesso() {
        //cenário
        usuario.setNome("Lucas");
        usuario.setEmail("lucas.ferreira@email.com");
        usuario.setIdade(30);
        usuario.setSenha("123456");

        //execução
        Usuario usuarioSalvo = repository.save(usuario);

        //validação
        Assertions.assertNotNull(usuarioSalvo);
        Assertions.assertNotNull(usuarioSalvo.getId());
        Optional<Usuario> usuarioOptional = repository.findById(usuarioSalvo.getId());
        Assertions.assertFalse(usuarioOptional.isEmpty());
        Assertions.assertEquals(usuario.getNome(), usuarioOptional.get().getNome());
    }

    @Test
    void deveLancarExcecaoQuandoConstraintForViolada() {
        //cenário
        usuario.setNome(null);

        //execução
        DataIntegrityViolationException exception = Assertions.assertThrows(DataIntegrityViolationException.class,
                () -> repository.save(usuario));

        //validação
        Assertions.assertNotNull(exception);
    }

    @Test
    void deveBuscarTodosOsUsuariosComSucesso() {
        //execução
        List<Usuario> usuarios = repository.findAll();

        //validação
        Assertions.assertNotNull(usuarios);
        Assertions.assertEquals(3, usuarios.size());
    }

    @Test
    void deveAtualizarUmUsuarioValidoComSucesso() {
        //Cenário
        Usuario usuarioQueSeraAtualizado = new Usuario("Boris", "boris@gmail.com", 30, "123456", "71901120");
        Usuario usuarioSalvo = repository.save(usuarioQueSeraAtualizado);

        usuarioSalvo.setNome("Boris Johnson");

        //execução
        Usuario usuarioAtualizado = repository.save(usuarioSalvo);

        //validação
        Assertions.assertEquals(usuarioSalvo.getId(), usuarioAtualizado.getId());
        Optional<Usuario> usuarioAtualizadoRecuperado = repository.findById(usuarioAtualizado.getId());
        Assertions.assertFalse(usuarioAtualizadoRecuperado.isEmpty());
        Assertions.assertEquals(usuarioSalvo.getNome(), usuarioAtualizadoRecuperado.get().getNome());
    }

    @Test
    void deveDeletarUmUsuarioComSucesso() {
        //cenário
        Usuario usuarioSalvo = repository.save(new Usuario("Adamastor", "adamastor@gmail.com", 65, "1234567", "71901120"));

        //execução
        repository.deleteById(usuarioSalvo.getId());

        //validação
        Optional<Usuario> usuarioRetornado = repository.findById(usuarioSalvo.getId());
        Assertions.assertTrue(usuarioRetornado.isEmpty());
    }

    @Test
    void deveBuscarIdadesEntreRange() {
        //cenário
        int idadeInicial = 4;
        int idadeFinal = 26;

        //execução
        List<Usuario> usuariosRetornados = repository.findByIdadeBetween(idadeInicial, idadeFinal);

        //validação
        Assertions.assertNotNull(usuariosRetornados);
        Assertions.assertEquals(2, usuariosRetornados.size());
        Assertions.assertEquals("Yuri", usuariosRetornados.get(0).getNome());
        Assertions.assertEquals("Helena", usuariosRetornados.get(1).getNome());
    }

    @Test
    void deveBuscarUsuarioPorEmailComSucesso() {
        //cenário
        String email = "rOdOlFo@EmAiL.cOm";

        //execução
        Optional<Usuario> usuario = repository.findByEmailIgnoreCase(email);

        //validação
        Assertions.assertTrue(usuario.isPresent());
        Assertions.assertEquals("rodolfo@email.com", usuario.get().getEmail());
    }

    @Test
    void deveBuscarUsuarioComEndereco() {
        //cenário
        String cep = "71901120";

        //execução
        List<Usuario> usuariosRetornadosPeloCep = repository.buscarUsuariosPorCep(cep);

        //validação
        Assertions.assertNotNull(usuariosRetornadosPeloCep);
        Assertions.assertEquals(1, usuariosRetornadosPeloCep.size());
    }

    @Test
    void deveBuscarUsuariosAtualizadosNoPeriodo() {
        //cenário
        LocalDateTime dataHoraAtualizacao = LocalDateTime.now().minusDays(1);
        LocalDateTime dataFim = LocalDateTime.now();
        Usuario usuario = new Usuario("Almir Sater", "almir@email.com", 60, "1234567", "71901120");
        usuario.setDataAtualizacao(dataHoraAtualizacao);
        repository.save(usuario);

        //execução
        List<Usuario> usuarios = repository.buscarUsuariosAtualizadosNoPeriodo(dataHoraAtualizacao.minusHours(1), dataFim);

        //validação
        Assertions.assertNotNull(usuarios);
        Assertions.assertEquals(1, usuarios.size());
    }
}
