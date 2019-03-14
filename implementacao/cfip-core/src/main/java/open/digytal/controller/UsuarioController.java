package open.digytal.controller;

import java.util.Collections;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

import open.digytal.model.Sessao;
import open.digytal.model.Usuario;
import open.digytal.model.entity.EntidadeConta;
import open.digytal.model.entity.EntidadeNatureza;
import open.digytal.model.entity.EntidadeRole;
import open.digytal.model.entity.EntidadeUsuario;
import open.digytal.model.enums.Categoria;
import open.digytal.model.enums.Roles;
import open.digytal.model.enums.TipoMovimento;
import open.digytal.repository.ContaRepository;
import open.digytal.repository.NaturezaRepository;
import open.digytal.repository.RoleRepository;
import open.digytal.repository.UsuarioRepository;
import open.digytal.service.UsuarioService;

@Controller
public class UsuarioController implements UsuarioService  {
	@Autowired
	private UsuarioRepository repository;
	@Autowired
	private NaturezaRepository naturezaRepository;
	@Autowired
	private ContaRepository contaRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PasswordEncoder encoder;
	@Override
	public Sessao login(String login,String senha) {
		Sessao sessao = new Sessao();
		Optional<EntidadeUsuario> entidade = repository.findById(login);
		if(entidade.isPresent()) {
			if(validarSenha(senha, entidade.get().getSenha())) {
				Usuario usuario = new Usuario();
				BeanUtils.copyProperties(entidade.get(), usuario);
				usuario.setSenha(null);
				sessao=Sessao.newInstance(usuario, usuario.getSenha());
			}
		}
		return sessao;
	}
	private boolean validarSenha(String senhaInformada, String senhaCriptografada) {
		return encoder.matches(senhaInformada, senhaCriptografada);
	}

	public void incluir(Usuario usuario) {
		EntidadeUsuario entidade = new EntidadeUsuario();
		usuario.setSenha(encoder.encode(usuario.getSenha()));
		BeanUtils.copyProperties(usuario, entidade);
		incluir(entidade);
	}

	@Transactional
	public EntidadeUsuario incluir(EntidadeUsuario usuario) {
		for (Roles r : Roles.values()) {
			EntidadeRole role = roleRepository.findByNome(r.name());
			if (role == null) {
				role = new EntidadeRole(r.name());
				role = roleRepository.save(role);
			}
		}
		if (usuario.getRoles().isEmpty())
			usuario.setRoles(Collections.singleton(roleRepository.findByNome(Roles.USER.name())));

		repository.save(usuario);

		EntidadeConta conta = new EntidadeConta();
		conta.setNome("CARTEIRA");
		conta.setSigla("CTR");
		conta.setLogin(usuario.getLogin());
		contaRepository.save(conta);

		conta = new EntidadeConta();
		conta.setNome("CONTA CORRENTE");
		conta.setSigla("CCR");
		conta.setLogin(usuario.getLogin());
		contaRepository.save(conta);

		conta = new EntidadeConta();
		conta.setNome("CONTA POUPANCA");
		conta.setSigla("CPA");
		conta.setLogin(usuario.getLogin());
		contaRepository.save(conta);

		EntidadeNatureza natureza = new EntidadeNatureza();
		natureza.setDescricao("SALDO INICIAL");
		natureza.setNome("SALDO INICIAL");
		natureza.setLogin(usuario.getLogin());
		natureza.setTipoMovimento(TipoMovimento.C);
		natureza.setCategoria(Categoria.R);
		naturezaRepository.save(natureza);

		natureza = new EntidadeNatureza();
		natureza.setDescricao("SALARIO");
		natureza.setNome("SALARIO");
		natureza.setLogin(usuario.getLogin());
		natureza.setTipoMovimento(TipoMovimento.C);
		natureza.setCategoria(Categoria.R);
		naturezaRepository.save(natureza);

		natureza = new EntidadeNatureza();
		natureza.setDescricao("DESPESAS");
		natureza.setNome("DESPESAS");
		natureza.setLogin(usuario.getLogin());
		natureza.setTipoMovimento(TipoMovimento.D);
		natureza.setCategoria(Categoria.D);
		naturezaRepository.save(natureza);

		natureza = new EntidadeNatureza();
		natureza.setDescricao("TRANSFERENCIA");
		natureza.setNome("TRANSFERENCIA");
		natureza.setLogin(usuario.getLogin());
		natureza.setTipoMovimento(TipoMovimento.T);
		natureza.setCategoria(Categoria.T);
		naturezaRepository.save(natureza);

		natureza = new EntidadeNatureza();
		natureza.setDescricao("ESTORNO DA TRANSFERENCIA");
		natureza.setNome("ESTORNO");
		natureza.setLogin(usuario.getLogin());
		natureza.setTipoMovimento(TipoMovimento.T);
		natureza.setCategoria(Categoria.T);
		naturezaRepository.save(natureza);

		return usuario;
	}

	
}
