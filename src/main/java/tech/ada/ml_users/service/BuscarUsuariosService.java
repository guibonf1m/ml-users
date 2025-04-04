package tech.ada.ml_users.service;

import org.springframework.stereotype.Service;
import tech.ada.ml_users.model.Usuario;
import tech.ada.ml_users.repository.UsuariosRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BuscarUsuariosService {

    private final UsuariosRepository usuariosRepository;

    public BuscarUsuariosService(UsuariosRepository usuariosRepository) {
        this.usuariosRepository = usuariosRepository;
    }

    public List<Usuario> buscarTodosOsUsuarios() {
        return usuariosRepository.findAll();
    }

    public Usuario buscarUsuarioPorId(Long id) {
        Optional<Usuario> usuarioOptional = usuariosRepository.findById(id);
        return usuarioOptional
                .orElseThrow(() -> new RuntimeException("Usuário com ID " + id + " não encontrado"));
    }
}
