package br.com.loja.model;

import br.com.loja.exception.PagamentoInsuficienteException;
import br.com.loja.exception.TransicaoInvalidaException;
import br.com.loja.exception.ValidacaoException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pedido {

    private int id;
    private Cliente cliente;
    private final List<ItemPedido> itens = new ArrayList<>();
    private SituacaoPedido situacao;
    private Pagamento pagamento;

    public Pedido(int id, Cliente cliente) {
        this.id = id;
        if (cliente == null) {
            throw new ValidacaoException("Pedido precisa de um cliente.");
        }
        this.cliente = cliente;
        this.situacao = SituacaoPedido.ACEITO;
    }

    public void adicionarItem(ItemPedido item) {
        exigirEstado(SituacaoPedido.ACEITO, "adicionar itens");
        if (item == null) {
            throw new ValidacaoException("Item inválido.");
        }
        itens.add(item);
    }

    public void removerItem(int itemId) {
        exigirEstado(SituacaoPedido.ACEITO, "remover itens");
        itens.removeIf(i -> i.getId() == itemId);
    }

    public double totalBruto() {
        return itens.stream().mapToDouble(ItemPedido::subtotal).sum();
    }

    public double freteTotal() {
        return itens.stream().mapToDouble(ItemPedido::freteTotal).sum();
    }

    public double descontoCliente() {
        return cliente.calcularDesconto(totalBruto());
    }

    public double totalAPagar() {
        return totalBruto() - descontoCliente();
    }

    public void pagar(Pagamento pagamento) {
        exigirTransicao(SituacaoPedido.PAGO);
        if (itens.isEmpty()) {
            throw new ValidacaoException("Não é possível pagar um pedido sem itens.");
        }
        if (pagamento == null) {
            throw new ValidacaoException("Forma de pagamento obrigatória.");
        }
        double devido = totalAPagar();

        double base = pagamento.getValorBase();

        if (base + 0.001 < devido) {
            throw new PagamentoInsuficienteException(base, devido);
        }
        for (ItemPedido item : itens) {
            item.getProduto().baixarEstoque(item.getQuantidade());
        }
        this.pagamento = pagamento;
        this.situacao = SituacaoPedido.PAGO;
    }

    public void enviar() {
        exigirTransicao(SituacaoPedido.ENVIADO);
        this.situacao = SituacaoPedido.ENVIADO;
    }

    public void entregar() {
        exigirTransicao(SituacaoPedido.ENTREGUE);
        this.situacao = SituacaoPedido.ENTREGUE;
    }

    public void cancelar() {
        exigirTransicao(SituacaoPedido.CANCELADO);

        if (situacao == SituacaoPedido.PAGO) {
            for (ItemPedido item : itens) {
                item.getProduto().reporEstoque(item.getQuantidade());
            }
        }
        this.situacao = SituacaoPedido.CANCELADO;
    }

    private void exigirTransicao(SituacaoPedido destino) {
        if (!situacao.podeIrPara(destino)) {
            throw new TransicaoInvalidaException(situacao, destino);
        }
    }

    private void exigirEstado(SituacaoPedido esperado, String acao) {
        if (situacao != esperado) {
            throw new TransicaoInvalidaException(
                    "Não é possível " + acao + " com o pedido em " + situacao + ".");
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public List<ItemPedido> getItens() {
        return Collections.unmodifiableList(itens);
    }

    public SituacaoPedido getSituacao() {
        return situacao;
    }

    public void restaurarSituacao(SituacaoPedido situacao) {
        this.situacao = situacao;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }

    @Override
    public String toString() {
        return String.format("Pedido #%d | %s | %s | itens: %d | total a pagar: R$ %.2f",
                id, cliente.getNome(), situacao, itens.size(), totalAPagar());
    }
}
