package tech.ada.ml_users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.ada.ml_users.model.Usuario;

@Repository
public interface UsuariosRepository extends JpaRepository<Usuario, Long> {



}
