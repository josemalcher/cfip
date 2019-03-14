package open.digytal.client.api;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import open.digytal.model.Sessao;
import open.digytal.model.entity.EntidadeConta;
import open.digytal.model.entity.EntidadeNatureza;
import open.digytal.model.enums.TipoMovimento;
import open.digytal.service.CadastroService;
@Service
//@Profile(Services.API)
public class CadastroClient extends ClientResource implements CadastroService {
	private String contas="contas";
	private String naturezas="naturezas";
	
	@Override
	protected ParameterizedTypeReference getListaType() {
		return new ParameterizedTypeReference<List<EntidadeConta>>() {};
	}

	@Override
	protected ParameterizedTypeReference getEntidadeType() {
		return new ParameterizedTypeReference<EntidadeConta>() {};
	}

	@Override
	protected String getResource() {
		return "cadastros";
	}
	@Override
	public EntidadeConta salvarConta(EntidadeConta entidade) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EntidadeNatureza salvarNatureza(EntidadeNatureza entidade) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EntidadeConta> listarContas(Integer id) {
		return null;
	}

	@Override
	public List<EntidadeConta> listarContas(String login, String nome) {
		return getLista(getListaType(),contas,Sessao.getInstance().getLogin(),nome);
	}

	@Override
	public List<EntidadeConta> listarCartoesCredito(String login) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EntidadeConta> listarCorrentesPoupanca(String login) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EntidadeNatureza> listarNaturezas(String login, String nome) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EntidadeNatureza> listarNaturezas(String login, TipoMovimento tipo) {
		// TODO Auto-generated method stub
		return null;
	}

	
    

}
