package tech.ada.ml_users.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tech.ada.ml_users.dto.CriarUsuarioRequestDTO;
import tech.ada.ml_users.dto.EnderecoDTO;
import tech.ada.ml_users.dto.UsuarioDTO;
import tech.ada.ml_users.model.Usuario;
import tech.ada.ml_users.service.AtualizarUsuarioService;
import tech.ada.ml_users.service.BuscarUsuariosService;
import tech.ada.ml_users.service.CriarUsuarioService;
import tech.ada.ml_users.service.DeletarUsuarioService;
import tech.ada.ml_users.utils.TestUtils;

import java.util.List;

import static org.mockito.Mockito.times;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class UsuariosControllerTest {

    @InjectMocks
    UsuariosController controller;
    @Mock
    BuscarUsuariosService buscarUsuariosService;
    @Mock
    CriarUsuarioService criarUsuarioService;
    @Mock
    AtualizarUsuarioService atualizarUsuarioService;
    @Mock
    DeletarUsuarioService deletarUsuarioService;

    final String PATH = "/usuarios";

    CriarUsuarioRequestDTO request;

    MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        request = new CriarUsuarioRequestDTO("Rodolfo", "rodolfo.lima@email.com", "12345678", 30, "71901120");
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void deveBuscarTodosOsUsuariosComSucesso() throws Exception {
        //cenário
        UsuarioDTO usuario = new UsuarioDTO("Rodolfo", 30,  "rodolfo.lima@email.com");
        UsuarioDTO usuario2 = new UsuarioDTO("Helena", 5, "helena.lima@email.com");
        List<UsuarioDTO> usuariosRetornados = List.of(usuario, usuario2);
        Mockito.when(buscarUsuariosService.buscarTodosOsUsuarios())
                .thenReturn(usuariosRetornados);

        //execução & validação
        mockMvc.perform(get(PATH)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(content().json(TestUtils.asJsonString(usuariosRetornados)))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void deveAtualizarUmEnderecoComSucesso() throws Exception {
        //cenário
       Long id = 1L;

        //execução e validação
        mockMvc.perform(MockMvcRequestBuilders.put(PATH + "/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.asJsonString(request)))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
                .andDo(MockMvcResultHandlers.print());

        Mockito.verify(atualizarUsuarioService).atualizarUsuario(
                Mockito.any(), Mockito.anyLong());
    }

    @Test
    void deveCriarUmUsuarioValidoComSucesso() throws Exception {
        //cenário
        Usuario usuarioCriado = new Usuario("Rodolfo", "rodolfo.lima@email.com", 30, "12345678", "71901120");

        Mockito.when(criarUsuarioService.criarUsuario(Mockito.any()))
                .thenReturn(usuarioCriado);

        //execução & validação
        mockMvc.perform(post(PATH)
                        .contentType(APPLICATION_JSON)
                        .content(TestUtils.asJsonString(request)))
                .andExpect(status().is(HttpStatus.CREATED.value()))
                .andExpect(content().json(TestUtils.asJsonString(usuarioCriado)))
                .andDo(print());
    }

    @Test
    void deveRetornarBadRequestQuandoCriarUmUsuarioInvalido() throws Exception {
        //cenário
        request.setCep(null);

        //execução & validação
        mockMvc.perform(post(PATH)
                        .contentType(APPLICATION_JSON)
                        .content(TestUtils.asJsonString(request)))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andDo(print());
    }

    @Test
    void deveDeletarUmUsuarioComSucesso() throws Exception {
        //cenário
        Long id = 1L;

        //execução & validação
        mockMvc.perform(delete(PATH + "/" + id))
                .andExpect(status().is(HttpStatus.NO_CONTENT.value()))
                .andDo(print());

        Mockito.verify(deletarUsuarioService, times(1)).deletarUsuarioPorId(id);
    }



}