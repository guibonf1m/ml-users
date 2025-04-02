package tech.ada.ml_users.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.ada.ml_users.model.Usuario;
import tech.ada.ml_users.service.ObterUsuarioService;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuariosController {

    private final ObterUsuarioService obterUsuariosService;

    public UsuariosController(ObterUsuarioService obterUsuariosService) {
        this.obterUsuariosService = obterUsuariosService;
    }

    @GetMapping
    public List<Usuario> obterTodosUsuarios() {
        return obterUsuariosService.obterTodosUsuarios();
    }
}