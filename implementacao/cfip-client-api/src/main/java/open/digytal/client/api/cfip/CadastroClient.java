package open.digytal.client.api.cfip;

import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import open.digytal.model.entity.EntidadeConta;
import open.digytal.model.entity.EntidadeNatureza;
import open.digytal.model.enums.TipoMovimento;
import open.digytal.service.CadastroService;
import open.digytal.service.Services;
@Service
@Profile(Services.API)
public class CadastroClient extends ClientResource implements CadastroService {

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EntidadeConta> listarContas(String login, String nome) {
		// TODO Auto-generated method stub
		return null;
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

	@Override
	protected ParameterizedTypeReference getListaType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ParameterizedTypeReference getEntidadeType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getResource() {
		// TODO Auto-generated method stub
		return null;
	}
    

}
