package br.com.loja.model;

import java.util.Arrays;
import java.util.List;

public enum SituacaoPedido {
    ACEITO,
    PAGO,
    ENVIADO,
    ENTREGUE,
    CANCELADO;

    public List<SituacaoPedido> proximosPermitidos() {
        switch (this) {
            case ACEITO:
                return Arrays.asList(PAGO, CANCELADO);
            case PAGO:
                return Arrays.asList(ENVIADO, CANCELADO);
            case ENVIADO:
                return Arrays.asList(ENTREGUE);
            case ENTREGUE:
            case CANCELADO:
            default:
                return List.of();
        }
    }

    public boolean podeIrPara(SituacaoPedido destino) {
        return proximosPermitidos().contains(destino);
    }
}
