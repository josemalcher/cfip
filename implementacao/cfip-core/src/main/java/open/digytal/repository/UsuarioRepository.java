package open.digytal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import open.digytal.model.entity.EntidadeUsuario;

public interface UsuarioRepository extends JpaRepository<EntidadeUsuario, String> {
	EntidadeUsuario findByLogin(String login);
}
