package open.digytal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import open.digytal.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	//Optional<Usuario> findByLoginOrEmail(String login, String email);
	Optional<Usuario> findByLogin(String login);
}
