package open.digytal.client.api.cfip;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import open.digytal.client.api.ClientResource;
import open.digytal.core.cfip.model.Conta;
import open.digytal.core.cfip.model.Saldo;
import open.digytal.core.cfip.model.api.ContaService;
import open.digytal.core.controle.Controle;
import open.digytal.core.model.Usuario;
import open.digytal.service.CadastroService;
@Service
@Profile(Controle.API)
public class ContaClientResource extends ClientResource implements CadastroService {
    private final String sufixo="contas";
    @Override
    protected ParameterizedTypeReference getListaType() {
        return new ParameterizedTypeReference<List<Conta>>() {};
    }
    protected ParameterizedTypeReference getListaSaldoType() {
        return new ParameterizedTypeReference<List<Saldo>>() {};
    }
    @Override
    protected ParameterizedTypeReference getEntidadeType() {
        return new ParameterizedTypeReference<Conta>() {};
    }

    @Override
    public Conta incluirConta(Conta entidade) {
        return incluir(entidade);
    }

    @Override
    public Object incluirSaldo(Saldo saldo, Conta conta) {
        throw new RuntimeException("API-FAVOR IMPLEMENTAR ESTE METODO");
    }

    @Override
    public List<Conta> listarContas(String usuario) {
        return getLista(getListaType(),sufixo,usuario);
    }

    @Override
    public List<Conta> listarContas(String usuario, String nome) {
        Map<String,String> param = new HashMap<String, String>();
        param.put("nome", nome);
    	return getListaParam(getListaType(),param, sufixo,usuario,"params");
    }

    @Override
    public List<Conta> listarContas(String usuario, Integer id) {
        return getLista(getListaType(),sufixo,usuario,id==null ? 0 : id);
    }

    @Override
    public List<Saldo> listarSaldos(Integer conta) {
        return getLista(getListaSaldoType(),sufixo,"saldos",conta);
    }

    @Override
    public Usuario incluirUsuario(Usuario usuario) {
       return post(usuario,sufixo,"usuarios");
    }


}
