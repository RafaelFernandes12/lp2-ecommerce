package br.com.loja.model;

import br.com.loja.exception.ValidacaoException;

public class ProdutoFisico extends Produto {

    private static final double FRETE_FIXO = 5.0;
    private static final double FRETE_POR_KG = 2.5;

    private double pesoKg;

    public ProdutoFisico(int id, String nome, double precoBase, int estoque, double pesoKg) {
        super(id, nome, precoBase, estoque);
        setPesoKg(pesoKg);
    }

    @Override
    public double calcularFrete() {
        return FRETE_FIXO + pesoKg * FRETE_POR_KG;
    }

    @Override
    public String tipo() {
        return "FISICO";
    }

    public double getPesoKg() {
        return pesoKg;
    }

    public void setPesoKg(double pesoKg) {
        if (pesoKg <= 0) {
            throw new ValidacaoException("Peso deve ser positivo.");
        }
        this.pesoKg = pesoKg;
    }
}
