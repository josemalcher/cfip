package open.digytal.webapi.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import open.digytal.model.Conta;
import open.digytal.model.Natureza;
import open.digytal.model.acesso.Roles;
import open.digytal.repository.NaturezaRepository;
import open.digytal.webapi.secutiry.JwtSession;

//@RestController
//@RequestMapping("/naturezas")
public class NaturezaResource {
	@Autowired
	private NaturezaRepository repository;
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "nome", value = "Nome",defaultValue="", required = false, dataType = "string")
	  })
	@PreAuthorize(Roles.PRE_USER)
	@GetMapping(value="/{nome}")
	public List<Natureza> todas(@PathVariable("nome") String nome){
		if(nome==null || nome.trim().isEmpty() || nome.equals("undefined")) //undefined - swagger
			return repository.listarTodas(JwtSession.getLogin());
		else
			return repository.listar(JwtSession.getLogin(),nome);
	}
	@GetMapping(value="/teste")
	public List<Natureza> teste(){
		return repository.listarTodas("gso");
	}
	
}
