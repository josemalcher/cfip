package open.digytal.util.cfip;

import java.util.List;

import open.digytal.model.Conta;
import open.digytal.model.Lancamento;
import open.digytal.model.Parcela;
import open.digytal.model.TipoMovimento;
import open.digytal.model.Total;

public class CfipUtil {
	public static Total lancamentos(List<Lancamento> lista) {
		Total total = new Total();
        for (Lancamento l : lista) {
        	total.aplicar(l.getTipoMovimento() == TipoMovimento.C, l.getParcelamento().getRestante());
        }
        return total;
    }
	public static Total extrato(List<Lancamento> lista) {
		Total total = new Total();
        for (Lancamento l : lista) {
        	total.aplicar(l.getTipoMovimento() == TipoMovimento.C, l.getValor());
        }
        return total;
    }
	public static Total previsoes(List<Lancamento> lista) {
		Total total = new Total();
        for (Lancamento l : lista) {
        	total.aplicar(l.getTipoMovimento() == TipoMovimento.C, l.getParcelamento().getRestante());
        }
        return total;
    }
	public static Total parcelas(List<Parcela> lista) {
		Total total = new Total();
        for (Parcela p : lista) {
        	total.aplicar(p.getLancamento().getTipoMovimento() == TipoMovimento.C, p.getValor());
        }
        return total;
    }
    public static Double totalContas(List<Conta> lista) {
        Double total = 0.0d;
        for (Conta c : lista) {
            total = total + c.getSaldoAtual();
        }
        return total;
    }

}
