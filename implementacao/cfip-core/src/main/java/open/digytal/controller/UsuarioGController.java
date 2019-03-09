package open.digytal.controller;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Controller;

import open.digytal.model.acesso.Usuario;
import open.digytal.repository.persistence.Controllers;
import open.digytal.service.UsuarioService;
import open.digytal.util.Filtro;

@Controller
public class UsuarioGController extends Controllers<Usuario> implements UsuarioService   {
	@Override
	public List<Usuario> listar(Filtro... filtros) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT NEW ");
		sb.append(getClasse().getName() + " ");
		sb.append("(e.login,e.nome,e.email,e.login) ");
		sb.append("FROM ");
		sb.append(getEntidade() + " e ");
		Query query = em.createQuery(sb.toString());
		List<Usuario> list = query.getResultList();
		return list;
		
	}


}
