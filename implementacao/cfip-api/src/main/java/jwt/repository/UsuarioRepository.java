package jwt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import jwt.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	//Optional<Usuario> findByLoginOrEmail(String login, String email);
	Optional<Usuario> findByUsername(String username);
}
