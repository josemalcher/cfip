package open.digytal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import open.digytal.model.acesso.EntidadeUsuario;

public interface UsuarioRepository extends JpaRepository<EntidadeUsuario, String> {
	EntidadeUsuario findByLogin(String login);
}
