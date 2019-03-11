package open.digytal.service;

import java.util.List;

import open.digytal.model.entity.EntidadeConta;
import open.digytal.model.entity.EntidadeNatureza;
import open.digytal.model.enums.TipoMovimento;

public interface CadastroService {

	EntidadeConta salvarConta(EntidadeConta entidade);

	EntidadeNatureza salvarNatureza(EntidadeNatureza entidade);

	List<EntidadeConta> listarContas(Integer id);

	List<EntidadeConta> listarContas(String login, String nome);

	List<EntidadeConta> listarCartoesCredito(String login);

	List<EntidadeConta> listarCorrentesPoupanca(String login);

	List<EntidadeNatureza> listarNaturezas(String login, String nome);

	List<EntidadeNatureza> listarNaturezas(String login, TipoMovimento tipo);
}
