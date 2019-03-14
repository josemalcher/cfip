package open.digytal.client.api;

import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import open.digytal.model.Lancamento;
import open.digytal.model.entity.EntidadeLancamento;
import open.digytal.model.entity.EntidadeParcela;
import open.digytal.service.LancamentoService;
import open.digytal.service.Services;
@Service
//@Profile(Services.API)
public class LancamentoClient extends ClientResource implements LancamentoService {

	@Override
	public void incluir(Lancamento entidade) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<EntidadeLancamento> extrato(Integer contaId, Date dataInicio) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EntidadeLancamento> listarLancamentos(String login, Date inicio, Date fim, Integer conta,
			Integer natureza) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EntidadeLancamento> listarPrevisoes(String login, Date inicio, Date fim, Integer conta,
			Integer natureza) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EntidadeParcela> listarParcelas(String login, Date inicio, Date fim, Integer conta, Integer natureza) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EntidadeParcela> listarFaturas(String login, Date inicio, Date fim, Integer conta, Integer natureza) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void compensarParcela(Date data, EntidadeParcela... parcelas) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public EntidadeParcela buscarParcela(Integer id) {
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
    

}
