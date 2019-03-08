package open.digytal.controller;

import java.util.Collections;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

import open.digytal.model.Categoria;
import open.digytal.model.EntidadeConta;
import open.digytal.model.EntidadeNatureza;
import open.digytal.model.TipoMovimento;
import open.digytal.model.EntidadeUsuario;
import open.digytal.model.acesso.Role;
import open.digytal.model.acesso.Roles;
import open.digytal.repository.ContaRepository;
import open.digytal.repository.NaturezaRepository;
import open.digytal.repository.RoleRepository;
import open.digytal.repository.UsuarioRepository;

@Controller
public class UsuarioController {
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
	public EntidadeUsuario findByLogin(String login){
		return repository.findByLogin(login); 
	}
	public boolean validarSenha(String senhaInformada, String senhaCriptografada) {
		return encoder.matches(senhaInformada, senhaCriptografada);
	}
	@Transactional
    public EntidadeUsuario incluir(EntidadeUsuario usuario) {
		Role roleUser = null;
		for(Roles r: Roles.values()) {
        	Role role = roleRepository.findByNome(r.name());
        	if(role==null) {
    			role = new Role(r.name());
    			role=roleRepository.save(role);
    			if(r==Roles.USER)
    				roleUser=role;
        	}
        }
    	if(usuario.getRoles().isEmpty())
        	usuario.setRoles(Collections.singleton(roleUser));
        
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