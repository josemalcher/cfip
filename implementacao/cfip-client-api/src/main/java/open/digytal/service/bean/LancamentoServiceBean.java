package open.digytal.service.bean;

import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import open.digytal.client.api.ClientResource;
import open.digytal.model.Lancamento;
import open.digytal.model.Lancamentos;
import open.digytal.model.Parcelas;
import open.digytal.model.entity.EntidadeParcela;
import open.digytal.service.LancamentoService;
import open.digytal.util.Formatador;
import open.digytal.util.config.Configuracao;
@Service
@Profile(Configuracao.PROFILE_API)
public class LancamentoServiceBean extends ClientResource implements LancamentoService {
	private String lancamentos="lancamentos";
	private String previsoes="previsoes";
	private String parcelas="parcelas";
	private String faturas="faturas";
	private String extrato="extrato";
	
	private ParameterizedTypeReference getLancamentosListaType() {
		return new ParameterizedTypeReference<List<Lancamentos>>() {};
	}
	private ParameterizedTypeReference getParcelaListaType() {
		return new ParameterizedTypeReference<List<Parcelas>>() {};
	}
	private ParameterizedTypeReference getParcelaType() {
		return new ParameterizedTypeReference<EntidadeParcela>() {};
	}
	@Override
	public void incluir(Lancamento entidade) {
		post(entidade, lancamentos);
	}

	@Override
	public List<Lancamentos> extrato(Integer contaId, Date dataInicio) {
		return getLista(getLancamentosListaType(),extrato, contaId,Formatador.formatarDataApi(dataInicio));
	}

	@Override
	public List<Lancamentos> listarLancamentos(String login, Date inicio, Date fim, Integer conta,Integer natureza) {
		return getLista(getLancamentosListaType(),lancamentos, Formatador.formatarDataApi(inicio),Formatador.formatarDataApi(fim),conta,natureza);
	}

	@Override
	public List<Lancamentos> listarPrevisoes(String login, Date inicio, Date fim, Integer conta,Integer natureza) {
		return getLista(getLancamentosListaType(),previsoes, Formatador.formatarDataApi(inicio),Formatador.formatarDataApi(fim),conta,natureza);
	}

	@Override
	public List<Parcelas> listarParcelas(String login, Date inicio, Date fim, Integer conta, Integer natureza) {
		return getLista(getParcelaListaType(), parcelas, Formatador.formatarDataApi(inicio),
				Formatador.formatarDataApi(fim), conta, natureza);
	}

	@Override
	public List<Parcelas> listarFaturas(String login, Date inicio, Date fim, Integer conta, Integer natureza) {
		return getLista(getParcelaListaType(),faturas, Formatador.formatarDataApi(inicio),Formatador.formatarDataApi(fim),conta,natureza);
	}
	@Override
	public void compensarParcela(Date data, Parcelas... parcelas) {
		post(parcelas, lancamentos,"compensacao",Formatador.formatarDataApi(data));
		
	}
	
	

}
