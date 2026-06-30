package br.com.loja.exception;

public class EstoqueInsuficienteException extends RuntimeException {

    public EstoqueInsuficienteException(String produto, int solicitado, int disponivel) {
        super("Estoque insuficiente para '" + produto + "': solicitado "
                + solicitado + ", disponível " + disponivel + ".");
    }
}
