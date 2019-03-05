package open.digytal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import open.digytal.model.acesso.Role;

public interface RoleRepository  extends JpaRepository<Role, String> {
	Role findByNome(String nome);
}
