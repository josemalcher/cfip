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
import open.digytal.model.entity.EntidadeConta;
import open.digytal.model.enums.Roles;
import open.digytal.service.CadastroService;
import open.digytal.webapi.secutiry.JwtSession;

@RestController
@RequestMapping("/contas")
public class ContaResource {
	@Autowired
	private CadastroService service;
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "nome", value = "Nome",defaultValue="", required = false, dataType = "string")
	  })
	@PreAuthorize(Roles.PRE_USER)
	@GetMapping(value="/{nome}")
	public List<EntidadeConta> todas(@PathVariable("nome") String nome){
		if(nome!=null &&nome.equals("undefined")) 
			nome=null;
		return service.listarContas (JwtSession.getLogin(),nome);
	}
	@PreAuthorize(Roles.PRE_USER)
	@GetMapping(value="/correntepoupanca")
	public List<EntidadeConta> contasCorrentePoupanca(){
		return service.listarCorrentesPoupanca(JwtSession.getLogin());
	}
	@PreAuthorize(Roles.PRE_USER)
	@GetMapping(value="/cartaocredito")
	public List<EntidadeConta> contasCartaoCredito(){
		return service.listarCartoesCredito(JwtSession.getLogin());
	}
	
	/*@ApiImplicitParams({
	    @ApiImplicitParam(name = "id", value = "ID Conta", required = true, dataType = "int"),
	    @ApiImplicitParam(name = "dataInicio", value = "Data ddMMyy", required = true, dataType = "date")
	 })
	@PreAuthorize(Roles.PRE_USER)
	@GetMapping(value="/extrato/{id}/{dataInicio}")
	public List<EntidadeLancamento> extrato(@PathVariable("id") Integer id, @PathVariable("dataInicio") @DateTimeFormat(pattern = Formatador.DATA_API) Date dataInicio){
		List<EntidadeLancamento> extrato = repository.extrato(id, dataInicio);
		return extrato;
	}*/
	
}
