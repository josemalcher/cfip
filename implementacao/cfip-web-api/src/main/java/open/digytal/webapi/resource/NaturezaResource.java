package open.digytal.webapi.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import open.digytal.model.entity.EntidadeNatureza;
import open.digytal.model.enums.Roles;
import open.digytal.model.enums.TipoMovimento;
import open.digytal.service.CadastroService;
import open.digytal.webapi.secutiry.JwtSession;

@RestController
@RequestMapping("/cadastros/naturezas")
public class NaturezaResource {
	@Autowired
	private CadastroService service;
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "nome", value = "Nome",defaultValue="", required = false, dataType = "string")
	  })
	@PreAuthorize(Roles.PRE_USER_ADMIN)
	@GetMapping(value= {"","/{nome}"})
	public List<EntidadeNatureza> todas(@PathVariable(name ="nome",required = false) String nome){
		return service.listarNaturezas(JwtSession.getLogin(),nome);
	}
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "tipo", value = "Tipo",defaultValue="", required = true, dataType = "string")
	  })
	@PreAuthorize(Roles.PRE_USER_ADMIN)
	@GetMapping(value= {"/tipo/{tipo}"})
	public List<EntidadeNatureza> tipo(@PathVariable(name ="tipo",required = false) String tipo){
		return service.listarNaturezas(JwtSession.getLogin(),TipoMovimento.valueOf(tipo.toUpperCase()));
	}
	@PostMapping
    public void incluir(@RequestBody EntidadeNatureza entidade){
        service.salvarNatureza(entidade);
    }
}
