package open.digytal.util.cfip;

import java.util.List;

import open.digytal.model.entity.EntidadeConta;
import open.digytal.model.entity.EntidadeLancamento;
import open.digytal.model.entity.EntidadeParcela;
import open.digytal.model.entity.Total;
import open.digytal.model.enums.TipoMovimento;

public class CfipUtil {
	public static Total lancamentos(List<EntidadeLancamento> lista) {
		Total total = new Total();
        for (EntidadeLancamento l : lista) {
        	total.aplicar(l.getTipoMovimento() == TipoMovimento.C, l.getParcelamento().getRestante());
        }
        return total;
    }
	public static Total extrato(List<EntidadeLancamento> lista) {
		Total total = new Total();
        for (EntidadeLancamento l : lista) {
        	total.aplicar(l.getTipoMovimento() == TipoMovimento.C, l.getValor());
        }
        return total;
    }
	public static Total previsoes(List<EntidadeLancamento> lista) {
		Total total = new Total();
        for (EntidadeLancamento l : lista) {
        	total.aplicar(l.getTipoMovimento() == TipoMovimento.C, l.getParcelamento().getRestante());
        }
        return total;
    }
	public static Total parcelas(List<EntidadeParcela> lista) {
		Total total = new Total();
        for (EntidadeParcela p : lista) {
        	total.aplicar(p.getLancamento().getTipoMovimento() == TipoMovimento.C, p.getValor());
        }
        return total;
    }
    public static Double totalContas(List<EntidadeConta> lista) {
        Double total = 0.0d;
        for (EntidadeConta c : lista) {
            total = total + c.getSaldoAtual();
        }
        return total;
    }

}
