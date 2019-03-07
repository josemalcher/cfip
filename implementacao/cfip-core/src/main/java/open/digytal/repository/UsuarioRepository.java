package open.digytal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import open.digytal.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {
	Usuario findByLogin(String login);
}
