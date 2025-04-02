package tech.ada.ml_users.service;

import org.springframework.stereotype.Service;
import tech.ada.ml_users.model.Usuario;

import java.util.List;

@Service
public class ObterUsuarioService {

    public List<Usuario> obterTodosUsuarios() {
        return List.of(new Usuario(
                "Rodolfo",
                "rodolfo.lima@email.com",
                "123456",
                31
        ));
    }
}