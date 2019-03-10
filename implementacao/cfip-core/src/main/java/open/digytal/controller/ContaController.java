package open.digytal.controller;

import org.springframework.stereotype.Controller;

import open.digytal.model.Conta;
import open.digytal.repository.persistence.Controllers;
import open.digytal.service.ContaService;

@Controller
public class ContaController extends Controllers<Conta> implements ContaService {
	
}
