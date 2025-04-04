package tech.ada.ml_users.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.ada.ml_users.model.Usuario;
import tech.ada.ml_users.service.BuscarUsuariosService;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuariosController {

    private final BuscarUsuariosService buscarUsuariosService;

    public UsuariosController(BuscarUsuariosService buscarUsuariosService) {
        this.buscarUsuariosService = buscarUsuariosService;
    }

    @GetMapping
    public List<Usuario> buscarTodosOsUsuarios() {
        return buscarUsuariosService.buscarTodosOsUsuarios();
    }

    @GetMapping(path = "/{id}")
    public Usuario buscarUsuarioPorId(@PathVariable(value = "id") Long id) {
        return buscarUsuariosService.buscarUsuarioPorId(id);
    }




}
