package open.digytal.client.api;

import java.util.Date;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import open.digytal.model.Lancamento;
import open.digytal.model.entity.EntidadeLancamento;
import open.digytal.model.entity.EntidadeParcela;
import open.digytal.service.LancamentoService;
import open.digytal.util.Formatador;
@Service
public class LancamentoClient extends ClientResource implements LancamentoService {
	private String lancamentos="lancamentos";
	private String previsoes="previsoes";
	private String parcelas="parcelas";
	private String faturas="faturas";
	private String extrato="extrato";
	private ParameterizedTypeReference getListaType() {
		return new ParameterizedTypeReference<List<EntidadeLancamento>>() {};
	}
	private ParameterizedTypeReference getParcelaListaType() {
		return new ParameterizedTypeReference<List<EntidadeParcela>>() {};
	}
	private ParameterizedTypeReference getParcelaType() {
		return new ParameterizedTypeReference<EntidadeParcela>() {};
	}
	@Override
	public void incluir(Lancamento entidade) {
		post(entidade, lancamentos);
	}

	@Override
	public List<EntidadeLancamento> extrato(Integer contaId, Date dataInicio) {
		return getLista(getListaType(),extrato, contaId,Formatador.formatarDataApi(dataInicio));
	}

	@Override
	public List<EntidadeLancamento> listarLancamentos(String login, Date inicio, Date fim, Integer conta,Integer natureza) {
		return getLista(getListaType(),lancamentos, Formatador.formatarDataApi(inicio),Formatador.formatarDataApi(fim),conta,natureza);
	}

	@Override
	public List<EntidadeLancamento> listarPrevisoes(String login, Date inicio, Date fim, Integer conta,Integer natureza) {
		return getLista(getListaType(),previsoes, Formatador.formatarDataApi(inicio),Formatador.formatarDataApi(fim),conta,natureza);
	}

	@Override
	public List<EntidadeParcela> listarParcelas(String login, Date inicio, Date fim, Integer conta, Integer natureza) {
		return getLista(getParcelaListaType(),parcelas, Formatador.formatarDataApi(inicio),Formatador.formatarDataApi(fim),conta,natureza);
	}

	@Override
	public List<EntidadeParcela> listarFaturas(String login, Date inicio, Date fim, Integer conta, Integer natureza) {
		return getLista(getParcelaListaType(),faturas, Formatador.formatarDataApi(inicio),Formatador.formatarDataApi(fim),conta,natureza);
	}

	@Override
	public EntidadeParcela buscarParcela(Integer id) {
		return get(getParcelaType(), parcelas,id);
	}

	@Override
	public void compensarParcela(Date data, EntidadeParcela... parcelas) {
		// TODO Auto-generated method stub
		
	}

	

}
