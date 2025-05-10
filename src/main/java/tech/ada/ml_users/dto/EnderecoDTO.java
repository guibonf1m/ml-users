package tech.ada.ml_users.dto;

import jakarta.validation.constraints.NotBlank;

public class EnderecoDTO {


    @NotBlank(message = "Cep não pode ser nulo ou vazio")
    private String cep;
    private String logradouro;
    private String bairro;
    private String localidade;

    public EnderecoDTO() {
    }

    public EnderecoDTO(String cep, String logradouro, String bairro, String localidade) {
        this.cep = cep;
        this.logradouro = logradouro;
        this.bairro = bairro;
        this.localidade = localidade;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }
}
