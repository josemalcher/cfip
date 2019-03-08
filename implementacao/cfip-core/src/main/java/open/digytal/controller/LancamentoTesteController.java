package open.digytal.controller;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Controller;

@Controller
public class LancamentoTesteController {
	@PersistenceContext
	private EntityManager em;
}
