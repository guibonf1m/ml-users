package tech.ada.ml_users.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tech.ada.ml_users.dto.EnderecoDTO;
import tech.ada.ml_users.dto.mapper.EnderecoDTOMapper;
import tech.ada.ml_users.model.Endereco;

import java.net.http.HttpClient;

@Service
public class EnderecoService {

    @Value("${url.viacep}")
    private String urlViaCep;

    private final RestTemplate restTemplate;

    public EnderecoService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Endereco obterEnderecoPeloCep(String cep) {
        EnderecoDTO enderecoViaCep =  restTemplate.getForObject(String.format(urlViaCep, cep), EnderecoDTO.class);
        return EnderecoDTOMapper.toEntity(enderecoViaCep);
    }

    public void setUrlViaCep(String urlViaCep) {
        this.urlViaCep = urlViaCep;
    }
}
