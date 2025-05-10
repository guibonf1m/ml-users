package tech.ada.ml_users.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.Times;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tech.ada.ml_users.dto.EnderecoDTO;
import tech.ada.ml_users.model.Endereco;
import tech.ada.ml_users.service.AtualizarEnderecoService;
import tech.ada.ml_users.service.CriarEnderecoService;
import tech.ada.ml_users.service.DeletarEnderecoService;
import tech.ada.ml_users.utils.TestUtils;

import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class EnderecoControllerTest {

    @InjectMocks
    EnderecoController controller;

    @Mock
    CriarEnderecoService criarEnderecoService;

    @Mock
    AtualizarEnderecoService atualizarEnderecoService;

    @Mock
    DeletarEnderecoService deletarEnderecoService;

    MockMvc mockMvc;

    final String ENDERECOS_PATH = "/enderecos";

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void deveLancarStatus400QuandoCepForInvalido() throws Exception {
        //cenário
        EnderecoDTO enderecoInvalido = TestUtils.umEndereco();
        enderecoInvalido.setCep(null);

        String enderecoAsJson = TestUtils.asJsonString(enderecoInvalido);

        //execução e validação
        mockMvc.perform(MockMvcRequestBuilders.post(ENDERECOS_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(enderecoAsJson))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void deveLancarStatus400QuandoCepForVazio() throws Exception {
        //cenário
        EnderecoDTO enderecoInvalido = TestUtils.umEndereco();
        enderecoInvalido.setCep("");

        String enderecoAsJson = TestUtils.asJsonString(enderecoInvalido);

        //execução e validação
        mockMvc.perform(MockMvcRequestBuilders.post(ENDERECOS_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(enderecoAsJson))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void deveAtualizarUmEnderecoComSucesso() throws Exception {
        //cenário
        EnderecoDTO enderecoValido = TestUtils.umEndereco();
        Long id = 1L;

        //execução e validação
        mockMvc.perform(MockMvcRequestBuilders.put(ENDERECOS_PATH + "/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.asJsonString(enderecoValido)))
                        .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
                        .andDo(MockMvcResultHandlers.print());

        Mockito.verify(atualizarEnderecoService).atualizar(
                Mockito.any(EnderecoDTO.class), Mockito.anyLong());
    }

    @Test
    void deveDeletarUmEnderecoComSucesso() throws Exception {
        //cenário
        Long id = 1L;
        String pathDeletar = ENDERECOS_PATH + "/" + id;

        //execução e validação
        mockMvc.perform(MockMvcRequestBuilders.delete(pathDeletar))
                        .andExpect(MockMvcResultMatchers.status().is(HttpStatus.NO_CONTENT.value()))
                        .andDo(MockMvcResultHandlers.print());

        Mockito.verify(deletarEnderecoService, times(1)).deletar(id);
    }


}
