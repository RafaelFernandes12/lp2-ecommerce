package br.com.loja.exception;

public class PagamentoInsuficienteException extends RuntimeException {

    public PagamentoInsuficienteException(double pago, double devido) {
        super(String.format("Pagamento insuficiente: pago R$ %.2f, devido R$ %.2f.", pago, devido));
    }
}
