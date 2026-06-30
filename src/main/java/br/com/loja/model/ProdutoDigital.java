package br.com.loja.model;

import br.com.loja.exception.ValidacaoException;

public class ProdutoDigital extends Produto {

    private double tamanhoMb;
    private String urlDownload;

    public ProdutoDigital(int id, String nome, double precoBase, int estoque,
                          double tamanhoMb, String urlDownload) {
        super(id, nome, precoBase, estoque);
        setTamanhoMb(tamanhoMb);
        setUrlDownload(urlDownload);
    }

    @Override
    public double calcularFrete() {
        return 0.0;
    }

    @Override
    public String tipo() {
        return "DIGITAL";
    }

    public double getTamanhoMb() {
        return tamanhoMb;
    }

    public void setTamanhoMb(double tamanhoMb) {
        if (tamanhoMb <= 0) {
            throw new ValidacaoException("Tamanho (MB) deve ser positivo.");
        }
        this.tamanhoMb = tamanhoMb;
    }

    public String getUrlDownload() {
        return urlDownload;
    }

    public void setUrlDownload(String urlDownload) {
        if (urlDownload == null || urlDownload.trim().isEmpty()) {
            throw new ValidacaoException("URL de download é obrigatória.");
        }
        this.urlDownload = urlDownload.trim();
    }
}
