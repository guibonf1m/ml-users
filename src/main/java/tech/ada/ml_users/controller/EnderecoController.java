package tech.ada.ml_users.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tech.ada.ml_users.dto.EnderecoDTO;
import tech.ada.ml_users.service.AtualizarEnderecoService;
import tech.ada.ml_users.service.AtualizarUsuarioService;
import tech.ada.ml_users.service.CriarEnderecoService;
import tech.ada.ml_users.service.DeletarEnderecoService;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

    private final CriarEnderecoService criarEnderecoService;
    private final AtualizarEnderecoService atualizarEnderecoService;
    private final DeletarEnderecoService deletarEnderecoService;

    public EnderecoController(CriarEnderecoService criarEnderecoService,
                              AtualizarEnderecoService atualizarEnderecoService,
                              DeletarEnderecoService deletarEnderecoService) {
        this.criarEnderecoService = criarEnderecoService;
        this.atualizarEnderecoService = atualizarEnderecoService;
        this.deletarEnderecoService = deletarEnderecoService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public EnderecoDTO criar(@RequestBody @Valid EnderecoDTO enderecoDTO) {
        return criarEnderecoService.criar();
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public void atualizar(@PathVariable Long id, @RequestBody EnderecoDTO enderecoDTO) {
        atualizarEnderecoService.atualizar(enderecoDTO, id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        deletarEnderecoService.deletar(id);
    }
}
