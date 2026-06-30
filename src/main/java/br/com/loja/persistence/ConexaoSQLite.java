package br.com.loja.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public final class ConexaoSQLite {

    private static final String URL = "jdbc:sqlite:loja.db";
    private static Connection conexao;

    private ConexaoSQLite() {
    }

    public static Connection get() {
        try {
            if (conexao == null || conexao.isClosed()) {
                conexao = DriverManager.getConnection(URL);
                try (Statement st = conexao.createStatement()) {
                    st.execute("PRAGMA foreign_keys = ON");
                }
            }
            return conexao;
        } catch (SQLException e) {
            throw new RuntimeException("Falha ao conectar ao banco: " + e.getMessage(), e);
        }
    }

    public static void inicializar() {
        String[] ddl = {
            "CREATE TABLE IF NOT EXISTS cliente (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tipo TEXT NOT NULL," +
                "nome TEXT NOT NULL," +
                "cpf TEXT NOT NULL," +
                "email TEXT NOT NULL," +
                "endereco TEXT NOT NULL," +
                "primeira_compra INTEGER," +
                "pontos INTEGER)",

            "CREATE TABLE IF NOT EXISTS produto (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tipo TEXT NOT NULL," +
                "nome TEXT NOT NULL," +
                "preco_base REAL NOT NULL," +
                "estoque INTEGER NOT NULL," +
                "peso_kg REAL," +
                "tamanho_mb REAL," +
                "url_download TEXT)",

            "CREATE TABLE IF NOT EXISTS pedido (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "cliente_id INTEGER NOT NULL," +
                "situacao TEXT NOT NULL," +
                "pgto_forma TEXT," +
                "pgto_valor_base REAL," +
                "pgto_parcelas INTEGER," +
                "pgto_chave TEXT," +
                "FOREIGN KEY (cliente_id) REFERENCES cliente(id))",

            "CREATE TABLE IF NOT EXISTS item_pedido (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "pedido_id INTEGER NOT NULL," +
                "produto_id INTEGER NOT NULL," +
                "quantidade INTEGER NOT NULL," +
                "FOREIGN KEY (pedido_id) REFERENCES pedido(id)," +
                "FOREIGN KEY (produto_id) REFERENCES produto(id))"
        };
        try (Statement st = get().createStatement()) {
            for (String sql : ddl) {
                st.execute(sql);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Falha ao criar schema: " + e.getMessage(), e);
        }
    }
}
