package tech.ada.ml_users.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.ada.ml_users.dto.CriarUsuarioRequestDTO;
import tech.ada.ml_users.dto.UsuarioDTO;
import tech.ada.ml_users.dto.mapper.CriarUsuarioRequestMapper;
import tech.ada.ml_users.model.Usuario;
import tech.ada.ml_users.service.BuscarUsuariosService;
import tech.ada.ml_users.service.CriarUsuarioService;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuariosController {

    private final BuscarUsuariosService buscarUsuariosService;
    private final CriarUsuarioService criarUsuarioService;

    public UsuariosController(BuscarUsuariosService buscarUsuariosService,
                              CriarUsuarioService criarUsuarioService) {
        this.buscarUsuariosService = buscarUsuariosService;
        this.criarUsuarioService = criarUsuarioService;
    }

    @GetMapping
    public List<UsuarioDTO> buscarTodosOsUsuarios() {
        return buscarUsuariosService.buscarTodosOsUsuarios();
    }

    @GetMapping(path = "/{id}")
    public Usuario buscarUsuarioPorId(@PathVariable(value = "id") Long id) {
        return buscarUsuariosService.buscarUsuarioPorId(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Usuario criarUsuario(@RequestBody @Valid CriarUsuarioRequestDTO usuario) {
        return criarUsuarioService.criarUsuario(CriarUsuarioRequestMapper.toEntity(usuario));
//        return ResponseEntity
//                .status(HttpStatus.CREATED)
//                .body(criarUsuarioService.criarUsuario(CriarUsuarioRequestMapper.toEntity(usuario)));
    }


}
