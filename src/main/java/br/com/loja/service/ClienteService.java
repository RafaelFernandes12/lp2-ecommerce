package br.com.loja.service;

import br.com.loja.exception.EntidadeNaoEncontradaException;
import br.com.loja.model.Cliente;
import br.com.loja.persistence.ClienteDAO;

import java.util.ArrayList;
import java.util.List;

public class ClienteService {

    private final ClienteDAO dao = new ClienteDAO();
    private final List<Cliente> clientes = new ArrayList<>();

    public void carregar() {
        clientes.clear();
        clientes.addAll(dao.listarTodos());
    }

    public Cliente cadastrar(Cliente cliente) {
        dao.inserir(cliente);
        clientes.add(cliente);
        return cliente;
    }

    public void alterar(Cliente cliente) {
        buscarPorId(cliente.getId());
        dao.atualizar(cliente);
    }

    public void remover(int id) {
        Cliente c = buscarPorId(id);
        dao.remover(id);
        clientes.remove(c);
    }

    public Cliente buscarPorId(int id) {
        return clientes.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Cliente", id));
    }

    public List<Cliente> listar() {
        return List.copyOf(clientes);
    }

    public void persistir(Cliente cliente) {
        dao.atualizar(cliente);
    }
}
