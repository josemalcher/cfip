package open.digytal.resource.client;

import open.digytal.controle.Controle;
import open.digytal.model.*;
import open.digytal.model.api.CfipService;
import open.digytal.util.TipoOperacao;
import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile(Controle.API)
public class CfipClientResource extends ClientResource implements CfipService {
    //Devemos especializar classe de CfipClientResource por classe do modelo
    @Override
    protected ParameterizedTypeReference getListaType() {
        return null;
    }
    @Override
    protected ParameterizedTypeReference getEntidadeType() {
        return null;
    }
    protected ParameterizedTypeReference getContaType() {
        return new ParameterizedTypeReference<List<Conta>>() {};
    }
    protected ParameterizedTypeReference getNaturezaType() {
        return new ParameterizedTypeReference<List<Natureza>>() {};
    }

    @Override
    public Object incluirSaldo(Saldo saldo, Conta conta) {
        return null;
    }

    @Override
    public void gravar(TipoOperacao operacao, Object entidade) {

    }

    @Override
    public List<Natureza> listarNaturezas(String usuario, String nome) {
        return getLista(getNaturezaType(),"naturezas",usuario,nome);
    }

    @Override
    public List<Natureza> listarNaturezas(String usuario) {
        return getLista(getNaturezaType(),"naturezas",usuario);
    }

    @Override
    public List<Natureza> listarNaturezas(String usuario, TipoMovimento tipo) {
        return null;
    }

    @Override
    public List<Saldo> listarSaldos(Integer conta) {
        return null;
    }

    @Override
    public Usuario incluirUsuario(Usuario usuario) {
        return null;
    }
}
