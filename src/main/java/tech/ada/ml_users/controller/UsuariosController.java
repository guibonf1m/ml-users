package tech.ada.ml_users.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.ada.ml_users.dto.CriarUsuarioRequestDTO;
import tech.ada.ml_users.dto.UsuarioDTO;
import tech.ada.ml_users.dto.mapper.CriarUsuarioRequestMapper;
import tech.ada.ml_users.exception.UsuarioNaoEncontradoException;
import tech.ada.ml_users.model.Usuario;
import tech.ada.ml_users.service.AtualizarUsuarioService;
import tech.ada.ml_users.service.BuscarUsuariosService;
import tech.ada.ml_users.service.CriarUsuarioService;
import tech.ada.ml_users.service.DeletarUsuarioService;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuariosController {

    private final BuscarUsuariosService buscarUsuariosService;
    private final CriarUsuarioService criarUsuarioService;
    private final AtualizarUsuarioService atualizarUsuarioService;
    private final DeletarUsuarioService deletarUsuarioService;

    public UsuariosController(BuscarUsuariosService buscarUsuariosService,
                              CriarUsuarioService criarUsuarioService,
                              AtualizarUsuarioService atualizarUsuarioService,
                              DeletarUsuarioService deletarUsuarioService) {
        this.buscarUsuariosService = buscarUsuariosService;
        this.criarUsuarioService = criarUsuarioService;
        this.atualizarUsuarioService = atualizarUsuarioService;
        this.deletarUsuarioService = deletarUsuarioService;
    }

    @GetMapping
    public List<UsuarioDTO> buscarTodosOsUsuarios() {
        return buscarUsuariosService.buscarTodosOsUsuarios();
    }

    @GetMapping(path = "/{id}")
    public Usuario buscarUsuarioPorId(@PathVariable(value = "id") Long id) {
        return buscarUsuariosService.buscarUsuarioPorId(id);
    }

    @PostMapping
    public ResponseEntity<Usuario> criarUsuario(@RequestBody @Valid CriarUsuarioRequestDTO usuario) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(criarUsuarioService.criarUsuario(CriarUsuarioRequestMapper.toEntity(usuario)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizarUsuario(@PathVariable(value = "id") Long id,
                                                 @RequestBody @Valid CriarUsuarioRequestDTO usuario) {
        atualizarUsuarioService.atualizarUsuario(CriarUsuarioRequestMapper.toEntity(usuario), id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable(value = "id") Long id) {
         deletarUsuarioService.deletarUsuarioPorId(id);
         return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
