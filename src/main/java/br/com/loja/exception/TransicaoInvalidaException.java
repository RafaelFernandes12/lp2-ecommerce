package br.com.loja.exception;

import br.com.loja.model.SituacaoPedido;

public class TransicaoInvalidaException extends RuntimeException {

    public TransicaoInvalidaException(SituacaoPedido atual, SituacaoPedido destino) {
        super("Transição inválida de " + atual + " para " + destino + ".");
    }

    public TransicaoInvalidaException(String mensagem) {
        super(mensagem);
    }
}
