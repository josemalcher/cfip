package open.digytal.client.api.cfip;

import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import open.digytal.client.api.ClientResource;
import open.digytal.core.cfip.model.Lancamento;
import open.digytal.core.cfip.model.api.LancamentoService;
import open.digytal.core.cfip.model.filter.CfipFiltro;
import open.digytal.core.controle.Controle;
import open.digytal.util.Formatador;
@Service
@Profile(Controle.API)
public class LancamentoClientResource extends ClientResource implements LancamentoService {
    //https://viralpatel.net/blogs/spring-4-mvc-rest-example-json/
    private final String sufixo="lancamentos";
    @Override
    protected ParameterizedTypeReference getListaType() {
        return new ParameterizedTypeReference<List<Lancamento>>() {};
    }

    @Override
    protected ParameterizedTypeReference getEntidadeType() {
        return new ParameterizedTypeReference<Lancamento>() {};
    }

    @Override
    public void incluirLancamento(Lancamento entidade) {
        incluir(entidade);
    }

    @Override
    public void compensarLancamento(Integer id, Date quitacao) {
        get(getEntidadeType(),sufixo,"compensar",id,Formatador.formataDataApi(quitacao));
    }
    @Override
    public void compensarLancamento(Date quitacao, Integer... ids) {
        get(getEntidadeType(),sufixo,"compensacao",Formatador.formataDataApi(quitacao),ids);
    }
    @Override
    public void amortizarLancamento(Integer id, Date quitacao, Double amortizado) {
        get(getEntidadeType(),sufixo,"amortizar",id,Formatador.formataDataApi(quitacao),amortizado);
    }

    @Override
    public List<Lancamento> listarLancamentos(CfipFiltro filtro) {
        return filter(getListaType(), filtro, sufixo, "filtro");
    }

    @Override
    public List<Lancamento> listarPrevisoes(CfipFiltro filtro) {
    	return filter(getListaType(), filtro, sufixo, "previsoes", "filtro");
    }

    @Override
    public List<Lancamento> listarContaLancamentos(String usuario, Date inicio, Date fim, Integer conta) {
        return getLista(getListaType(),sufixo,"extrato",usuario, Formatador.formataDataApi(inicio),Formatador.formataDataApi(fim),conta);
    }

}
