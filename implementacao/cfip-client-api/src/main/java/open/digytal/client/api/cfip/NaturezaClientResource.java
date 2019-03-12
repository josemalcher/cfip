package open.digytal.client.api.cfip;

import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import open.digytal.client.api.ClientResource;
import open.digytal.core.cfip.model.Natureza;
import open.digytal.core.cfip.model.TipoMovimento;
import open.digytal.core.cfip.model.api.NaturezaService;
import open.digytal.core.controle.Controle;
@Service
@Profile(Controle.API)
public class NaturezaClientResource extends ClientResource implements NaturezaService{
    private final String sufixo="naturezas";
    @Override
    protected ParameterizedTypeReference getListaType() {
        return new ParameterizedTypeReference<List<Natureza>>() {};
    }

    @Override
    protected ParameterizedTypeReference getEntidadeType() {
        return new ParameterizedTypeReference<Natureza>() {};
    }


    @Override
    public List<Natureza> listarNaturezas(String usuario, String nome) {
        return getLista(getListaType(),sufixo,usuario,nome);
    }

    @Override
    public List<Natureza> listarNaturezas(String usuario) {
        return getLista(getListaType(),sufixo,usuario);
    }

    @Override
    public List<Natureza> listarNaturezas(String usuario, TipoMovimento tipo) {
        return getLista(getListaType(),sufixo,usuario,"movimento",tipo);
    }
}
