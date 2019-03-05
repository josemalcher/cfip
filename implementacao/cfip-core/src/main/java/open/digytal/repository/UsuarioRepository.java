package open.digytal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import open.digytal.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {
	Optional<Usuario> findByLogin(String login);
}
