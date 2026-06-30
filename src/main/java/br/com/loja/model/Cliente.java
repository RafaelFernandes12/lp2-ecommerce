package br.com.loja.model;

import br.com.loja.exception.ValidacaoException;

public abstract class Cliente extends Pessoa {

    private String endereco;

    protected Cliente(int id, String nome, String cpf, String email, String endereco) {
        super(id, nome, cpf, email);
        setEndereco(endereco);
    }

    public abstract double calcularDesconto(double valorCompra);

    @Override
    public String descricaoPapel() {
        return "Cliente";
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        if (endereco == null || endereco.trim().length() < 5) {
            throw new ValidacaoException("Endereço deve ter ao menos 5 caracteres.");
        }
        this.endereco = endereco.trim();
    }
}
