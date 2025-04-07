package tech.ada.ml_users.service;

import org.springframework.stereotype.Service;
import tech.ada.ml_users.model.Usuario;

import java.time.LocalDateTime;

@Service
public class AtualizarUsuarioService {

    private final BuscarUsuariosService buscarUsuariosService;
    private final CriarUsuarioService criarUsuarioService;

    public AtualizarUsuarioService(BuscarUsuariosService buscarUsuariosService,
                                   CriarUsuarioService criarUsuarioService) {
        this.buscarUsuariosService = buscarUsuariosService;
        this.criarUsuarioService = criarUsuarioService;
    }

    public Usuario atualizarUsuario(Usuario usuario, Long id) {
        buscarUsuariosService.buscarUsuarioPorId(id);
        usuario.setId(id);
        usuario.setDataAtualizacao(LocalDateTime.now());
        return criarUsuarioService.criarUsuario(usuario);
    }


}
