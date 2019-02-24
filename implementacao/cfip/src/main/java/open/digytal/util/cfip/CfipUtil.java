package open.digytal.util.cfip;

import java.util.List;

import open.digytal.model.Conta;
import open.digytal.model.Lancamento;
import open.digytal.model.TipoMovimento;
import open.digytal.model.Total;

public class CfipUtil {
	public static Total lancamentos(List<Lancamento> lista) {
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
    public static Total totais(List<Lancamento> lista) {
        return totais(lista, false);
    }
    @Deprecated
    public static Total totais(List<Lancamento> lista, boolean comTransferencia) {
        Total total = new Total();
        //FIXME:Como vc poderia melhorar este c�digo usando Lambda Java8 ?
        for (Lancamento l : lista) {
            //if (comTransferencia || !l.isTransferencia())
            
        	total.aplicar(l.getTipoMovimento() == TipoMovimento.C, l.getValor());
        }
        return total;
    }

    public static Double contaTotais(List<Conta> lista) {
        Double total = 0.0d;
        //FIXME:Como vc poderia melhorar este c�digo usando Lambda Java8 ?
        for (Conta c : lista) {
            total = total + c.getSaldoInicial();
        }
        return total;
    }

}
