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
import open.digytal.model.entity.EntidadeNatureza;
import open.digytal.model.enums.Roles;
import open.digytal.service.CadastroService;
import open.digytal.webapi.secutiry.JwtSession;

@RestController
@RequestMapping("/naturezas")
public class NaturezaResource {
	@Autowired
	private CadastroService service;
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "nome", value = "Nome",defaultValue="", required = false, dataType = "string")
	  })
	@PreAuthorize(Roles.PRE_USER)
	@GetMapping(value="/{nome}")
	public List<EntidadeNatureza> todas(@PathVariable("nome") String nome){
		if(nome!=null &&nome.equals("undefined")) 
			nome=null;
		return service.listarNaturezas(JwtSession.getLogin(),nome);
	}
}
