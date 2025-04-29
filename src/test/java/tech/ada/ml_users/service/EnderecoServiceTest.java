package tech.ada.ml_users.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.client.RestTemplate;
import tech.ada.ml_users.dto.EnderecoDTO;
import tech.ada.ml_users.model.Endereco;

public class EnderecoServiceTest {

    EnderecoService service;
    RestTemplate restTemplate;
    String urlViaCep = "https://viacep.com.br/ws/%s/json/";

    @BeforeEach
    public void setUp() {
        restTemplate = Mockito.mock(RestTemplate.class);
        service = new EnderecoService(restTemplate);
        service.setUrlViaCep(urlViaCep);
    }

    @Test
    void deveRetornarUmEnderecoComSucessoAoInformarOCep() {
        //Cenário
        String cep = "71901129";

        EnderecoDTO enderecoRetornadoDTO = new EnderecoDTO();
        enderecoRetornadoDTO.setCep(cep);
        enderecoRetornadoDTO.setLocalidade("Águas Claras");
        enderecoRetornadoDTO.setBairro("Norte");
        String urlCep = String.format(urlViaCep, cep);

        Mockito.when(restTemplate.getForObject(urlCep, EnderecoDTO.class))
                .thenReturn(enderecoRetornadoDTO);

        //Execução
        Endereco enderecoRetornado = service.obterEnderecoPeloCep(cep);

        //Verificação
        Assertions.assertNotNull(enderecoRetornado);
    }

}
