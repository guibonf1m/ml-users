package tech.ada.ml_users.dto.mapper;

import tech.ada.ml_users.dto.CriarUsuarioRequestDTO;
import tech.ada.ml_users.model.Usuario;

public class CriarUsuarioRequestMapper {

    public static Usuario toEntity(CriarUsuarioRequestDTO dto) {
            Usuario usuario = new Usuario();
            usuario.setNome(dto.getNome());
            usuario.setEmail(dto.getEmail());
            usuario.setSenha(dto.getSenha());
            usuario.setIdade(dto.getIdade());
            return usuario;
    }


}
