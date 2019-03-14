package open.digytal.client.api;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import open.digytal.model.Usuario;
import open.digytal.model.entity.EntidadeConta;
import open.digytal.model.entity.EntidadeNatureza;
import open.digytal.model.enums.TipoMovimento;
import open.digytal.service.CadastroService;
@Service
//@Profile(Services.API)
public class CadastroClient extends ClientResource implements CadastroService {
	private String contas="cadastros/contas";
	private String corrente=contas+"/correntepoupanca";
	private String cartaocredito=contas+"/cartaocredito";
	private String naturezas="cadastros/naturezas";
	
	@Override
	protected ParameterizedTypeReference getListaType() {
		return new ParameterizedTypeReference<List<EntidadeConta>>() {};
	}

	@Override
	protected ParameterizedTypeReference getEntidadeType() {
		return new ParameterizedTypeReference<EntidadeConta>() {};
	}
	
	protected ParameterizedTypeReference getNaturezaListaType() {
		return new ParameterizedTypeReference<List<EntidadeNatureza>>() {};
	}

	@Override
	public void salvarConta(EntidadeConta entidade) {
		post(entidade, contas);
	}

	@Override
	public void salvarNatureza(EntidadeNatureza entidade) {
		post(entidade, naturezas);
	}

	@Override
	public List<EntidadeConta> listarContas(Integer id) {
		return getLista(getListaType(),contas,id);
	}

	@Override
	public List<EntidadeConta> listarContas(String login, String nome) {
		return getLista(getListaType(),contas,nome);
	}

	@Override
	public List<EntidadeConta> listarCartoesCredito(String login) {
		return getLista(getListaType(),cartaocredito);
	}

	@Override
	public List<EntidadeConta> listarCorrentesPoupanca(String login) {
		return getLista(getListaType(),corrente);
	}

	@Override
	public List<EntidadeNatureza> listarNaturezas(String login, String nome) {
		return getLista(getNaturezaListaType(),naturezas,nome);
	}

	@Override
	public List<EntidadeNatureza> listarNaturezas(String login, TipoMovimento tipo) {
		return getLista(getNaturezaListaType(),naturezas,"tipo",tipo);
	}

	
    

}
