package open.digytal.resource.client;

import open.digytal.controle.Controle;
import open.digytal.model.Conta;
import open.digytal.model.api.CfipService;
import open.digytal.model.api.ContaService;
import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile(Controle.API)
public class ContaClientResource  extends ClientResource implements ContaService {
    //Devemos especializar classe de CfipClientResource por classe do modelo
    @Override
    protected ParameterizedTypeReference getListaType() {
        return new ParameterizedTypeReference<List<Conta>>() {};
    }
    @Override
    protected ParameterizedTypeReference getEntidadeType() {
        return new ParameterizedTypeReference<Conta>() {};
    }
    @Override
    public Conta incluirConta(Conta entidade) {
        return post(entidade,"contas");
    }

    @Override
    public List<Conta> listarContas(String usuario) {

        return getLista(getListaType(),"contas",usuario);
    }

    @Override
    public List<Conta> listarContas(String usuario, String nome) {
        return getLista(getListaType(),"contas",usuario,nome);
    }

    @Override
    public List<Conta> listarContas(String usuario, Integer id) {
        return getLista(getListaType(),"contas",usuario,id);
    }


}
