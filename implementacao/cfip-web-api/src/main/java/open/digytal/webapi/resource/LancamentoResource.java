package open.digytal.webapi.resource;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import open.digytal.model.Lancamento;
import open.digytal.model.entity.EntidadeLancamento;
import open.digytal.model.entity.EntidadeParcela;
import open.digytal.model.enums.Roles;
import open.digytal.service.LancamentoService;
import open.digytal.util.Formatador;
import open.digytal.webapi.secutiry.JwtSession;

@RestController
@RequestMapping
public class LancamentoResource {
	@Autowired
	private LancamentoService service;

	@PostMapping("/lancamentos")
	public void incluir(@RequestBody Lancamento objeto) {
		service.incluir(objeto);
	}
	
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "inicio", value = "Inicio ddMMyy", required = true, dataType = "date"),
		@ApiImplicitParam(name = "fim", value = "Fim ddMMyy", required = true, dataType = "date"),
		@ApiImplicitParam(name = "conta", value = "Conta", required = false, dataType = "string"),
		@ApiImplicitParam(name = "natureza", value = "Natureza", required = false, dataType = "string")
	})
	@PreAuthorize(Roles.PRE_USER_ADMIN)
	@GetMapping(value = { "/lancamentos/{inicio}/{fim}", 
						  "/lancamentos/{inicio}/{fim}/{conta}",
						  "/lancamentos/{inicio}/{fim}/{conta}/{natureza}"})
	public List<EntidadeLancamento> listarContaLancamentos(
			@PathVariable() @DateTimeFormat(pattern = Formatador.DATA_API) Date inicio,
			@PathVariable() @DateTimeFormat(pattern = Formatador.DATA_API) Date fim,
			@PathVariable(required = false) Integer conta, 
			@PathVariable(required = false) Integer natureza) {
		return service.listarLancamentos(JwtSession.getLogin(), inicio, fim, conta, natureza);
	}
	
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "inicio", value = "Inicio ddMMyy", required = true, dataType = "date"),
		@ApiImplicitParam(name = "fim", value = "Fim ddMMyy", required = true, dataType = "date"),
		@ApiImplicitParam(name = "conta", value = "Conta", required = false, dataType = "string"),
		@ApiImplicitParam(name = "natureza", value = "Natureza", required = false, dataType = "string")
	})
	@PreAuthorize(Roles.PRE_USER_ADMIN)
	@GetMapping(value = { "/previsoes/{inicio}/{fim}", 
						  "/previsoes/{inicio}/{fim}/{conta}",
						  "/previsoes/{inicio}/{fim}/{conta}/{natureza}"})
	public List<EntidadeLancamento> listarPrevisoes(
			@PathVariable() @DateTimeFormat(pattern = Formatador.DATA_API) Date inicio,
			@PathVariable() @DateTimeFormat(pattern = Formatador.DATA_API) Date fim,
			@PathVariable(required = false) Integer conta, 
			@PathVariable(required = false) Integer natureza) {
		return service.listarPrevisoes(JwtSession.getLogin(), inicio, fim, conta, natureza);
	}

	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "inicio", value = "Inicio ddMMyy", required = true, dataType = "date"),
		@ApiImplicitParam(name = "fim", value = "Fim ddMMyy", required = true, dataType = "date"),
		@ApiImplicitParam(name = "conta", value = "Conta", required = false, dataType = "string"),
		@ApiImplicitParam(name = "natureza", value = "Natureza", required = false, dataType = "string")
	})
	@PreAuthorize(Roles.PRE_USER_ADMIN)
	@GetMapping(value = { "/faturas/{inicio}/{fim}", 
						  "/faturas/{inicio}/{fim}/{conta}",
						  "/faturas/{inicio}/{fim}/{conta}/{natureza}"})
	public List<EntidadeParcela> listarFaturas(
			@PathVariable() @DateTimeFormat(pattern = Formatador.DATA_API) Date inicio,
			@PathVariable() @DateTimeFormat(pattern = Formatador.DATA_API) Date fim,
			@PathVariable(required = false) Integer conta, 
			@PathVariable(required = false) Integer natureza) {
		return service.listarFaturas(JwtSession.getLogin(), inicio, fim, conta, natureza);
	}
	
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "inicio", value = "Inicio ddMMyy", required = true, dataType = "date"),
		@ApiImplicitParam(name = "fim", value = "Fim ddMMyy", required = true, dataType = "date"),
		@ApiImplicitParam(name = "conta", value = "Conta", required = false, dataType = "string"),
		@ApiImplicitParam(name = "natureza", value = "Natureza", required = false, dataType = "string")
	})
	@PreAuthorize(Roles.PRE_USER_ADMIN)
	@GetMapping(value = { "/parcelas/{inicio}/{fim}", 
						  "/parcelas/{inicio}/{fim}/{conta}",
						  "/parcelas/{inicio}/{fim}/{conta}/{natureza}"})
	public List<EntidadeParcela> listarParcelas(
			@PathVariable() @DateTimeFormat(pattern = Formatador.DATA_API) Date inicio,
			@PathVariable() @DateTimeFormat(pattern = Formatador.DATA_API) Date fim,
			@PathVariable(required = false) Integer conta, 
			@PathVariable(required = false) Integer natureza) {
		return service.listarFaturas(JwtSession.getLogin(), inicio, fim, conta, natureza);
	}
	
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "id", value = "ID Conta", required = true, dataType = "int")
	})
	@PreAuthorize(Roles.PRE_USER_ADMIN)
	@GetMapping(value = "/parcelas/{id}")
	public EntidadeParcela buscarParcela(@PathVariable("id") Integer id) {
		EntidadeParcela entidade = service.buscarParcela(id);
		return entidade;
	}
	
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "id", value = "ID Conta", required = true, dataType = "int"),
		@ApiImplicitParam(name = "dataInicio", value = "Data ddMMyy", required = true, dataType = "date")
	})
	@PreAuthorize(Roles.PRE_USER_ADMIN)
	@GetMapping(value = "/extrato/{id}/{dataInicio}")
	public List<EntidadeLancamento> extrato(@PathVariable("id") Integer id,@PathVariable("dataInicio") @DateTimeFormat(pattern = Formatador.DATA_API) Date dataInicio) {
		List<EntidadeLancamento> extrato = service.extrato(id, dataInicio);
		return extrato;
	}
	
}
