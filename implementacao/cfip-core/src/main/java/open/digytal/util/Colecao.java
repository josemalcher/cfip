package open.digytal.util;

import java.util.Collection;
import java.util.List;

public class Colecao {
    public static boolean vazia(Collection colecao) {
        return (colecao == null || colecao.isEmpty());
    }
    public static <T> T primeiroElemento(Collection colecao){
        if(vazia(colecao))
            return null;
        else {
            if(colecao instanceof List)
                return (T) ((List)colecao).get(0);
            else
                return null;
        }
    }
    
}
