package br.com.loja.model;

import br.com.loja.exception.ValidacaoException;

public abstract class Pagamento {

    private int id;
    private double valorBase;

    protected Pagamento(int id, double valorBase) {
        this.id = id;
        setValorBase(valorBase);
    }

    public abstract double calcularValorFinal();

    public abstract String forma();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getValorBase() {
        return valorBase;
    }

    public void setValorBase(double valorBase) {
        if (valorBase < 0) {
            throw new ValidacaoException("Valor base do pagamento não pode ser negativo.");
        }
        this.valorBase = valorBase;
    }

    @Override
    public String toString() {
        return String.format("%s: base R$ %.2f -> cobrado R$ %.2f",
                forma(), valorBase, calcularValorFinal());
    }
}
