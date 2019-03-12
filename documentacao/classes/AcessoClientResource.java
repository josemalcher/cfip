package open.digytal.resource.client;

import open.digytal.controle.Controle;
import open.digytal.model.Sessao;
import open.digytal.model.acesso.api.AcessoService;
import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile(Controle.API)
public class AcessoClientResource extends ClientResource implements AcessoService {

    @Override
    public Sessao logar(String login, String senha) {
        return get(getEntidadeType(),"logar",login,senha);
    }

    @Override
    protected ParameterizedTypeReference getListaType() {
         return new ParameterizedTypeReference<List<Sessao>>() {};
    }

    @Override
    protected ParameterizedTypeReference getEntidadeType() {
        return new ParameterizedTypeReference<Sessao>() {};
    }
}
