package tech.ada.ml_users.service;


import org.springframework.stereotype.Service;
import tech.ada.ml_users.model.Usuario;
import tech.ada.ml_users.repository.UsuariosRepository;

@Service
public class CriarUsuarioService {

    private final UsuariosRepository repository;

    public CriarUsuarioService(UsuariosRepository repository) {
        this.repository = repository;
    }

    public Usuario criarUsuario(Usuario usuario) {
        return repository.save(usuario);
    }
}
