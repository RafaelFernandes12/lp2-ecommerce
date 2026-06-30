# Sistema de Pedidos (Loja) — Atividade Unidade 03 (LP2)

Aplicação de console em **Java** que simula a gestão de pedidos de uma loja
(e-commerce), demonstrando os conceitos de Programação Orientada a Objetos:
herança, polimorfismo, encapsulamento, tratamento de exceções, regras de negócio,
máquina de estados e persistência em banco de dados (SQLite).

## Pré-requisitos

- **Java 17 ou superior** (desenvolvido e testado com Java 21).
- Nada além disso: as dependências (`sqlite-jdbc` e `slf4j`) já acompanham o
  projeto na pasta `lib/`. **Não é necessário Maven nem acesso à internet.**

Verifique sua versão do Java:

```bash
java -version
```

## Como executar

### Linux / macOS

```bash
./run.sh
```

(se necessário: `chmod +x run.sh`)

### Windows

```bat
run.bat
```

### Manualmente (qualquer SO)

```bash
# compilar
javac -d target/classes -cp "lib/*" $(find src -name '*.java')

# executar (Linux/macOS — use ';' no lugar de ':' no Windows)
java -cp "target/classes:lib/*" br.com.loja.Main
```

Ao iniciar, o sistema cria automaticamente o arquivo de banco `loja.db` na pasta
atual (se ainda não existir) e carrega os dados já salvos. Use o menu de terminal
para cadastrar clientes/produtos e operar os pedidos.

## Onde cada requisito está implementado

| Requisito | Implementação |
|---|---|
| **≥ 11 classes** | 12 classes de domínio em `model/` (sem contar o enum) + DAOs e Services |
| **Encapsulamento** | Atributos `private` com getters/setters validados em todas as entidades de `model/` |
| **Polimorfismo (3 hierarquias)** | `Cliente.calcularDesconto`, `Produto.calcularFrete`, `Pagamento.calcularValorFinal` |
| **Regras de negócio (7)** | Estoque, transições de estado, descontos, frete, validações, pagamento suficiente, pedido pagável |
| **Operação entre múltiplas classes** | `PedidoService.pagar` / `Pedido.pagar` (Pedido↔Item↔Produto↔Cliente↔Pagamento) |
| **Exceções personalizadas** | Pacote `exception/` (5 exceções) |
| **Estado dinâmico + transições** | `Pedido` + `SituacaoPedido` (máquina de estados) |
| **Persistência em arquivo + carregar no início** | `persistence/` (SQLite via JDBC); carga no `Main` |
| **Interação com usuário** | `ui/MenuPrincipal` (menu via terminal) |
| **Diagrama UML** | `docs/diagrama-classes.puml` |

## Estrutura do projeto

```
src/main/java/br/com/loja/
├── Main.java                  # inicializa o banco, carrega dados e abre o menu
├── model/                     # entidades + máquina de estados
│   ├── Pessoa, Cliente, ClienteRegular, ClienteVip
│   ├── Produto, ProdutoFisico, ProdutoDigital
│   ├── Pedido, ItemPedido, SituacaoPedido (enum)
│   └── Pagamento, PagamentoPix, PagamentoCartao
├── exception/                 # 5 exceções personalizadas
├── persistence/               # ConexaoSQLite + ClienteDAO/ProdutoDAO/PedidoDAO
├── service/                   # ClienteService/ProdutoService/PedidoService (regras)
└── ui/                        # MenuPrincipal (terminal)
docs/diagrama-classes.puml     # diagrama de classes (PlantUML)
lib/                           # dependências (sqlite-jdbc, slf4j)
```

## Modelo de domínio — destaques de OOP

- **Herança com membros próprios**: toda subclasse adiciona
  atributos/comportamentos — `ClienteVip` tem `pontosFidelidade`, `ClienteRegular`
  tem `primeiraCompra`; `ProdutoFisico` tem `pesoKg`, `ProdutoDigital` tem `tamanhoMb`
  e `urlDownload`; `PagamentoCartao` tem `parcelas`, `PagamentoPix` tem `chavePix`.
- **Polimorfismo ligado a regra de negócio**:
  - Desconto varia por tipo de cliente (VIP 10% sempre; Regular 5% só na 1ª compra).
  - Frete varia por tipo de produto (físico cobra por peso; digital é grátis).
  - Valor final varia por forma de pagamento (Pix desconta 5%; Cartão acresce juros).
- **Máquina de estados do pedido**: `ACEITO → PAGO → ENVIADO → ENTREGUE`, com
  `CANCELADO` permitido apenas antes do envio. Transições inválidas lançam exceção.
- **Persistência polimórfica**: os DAOs usam uma coluna discriminadora (`tipo`/`forma`)
  para reconstruir a subclasse correta ao carregar do banco.

## Diagrama de classes

O arquivo `docs/diagrama-classes.puml` está em PlantUML. Para gerar a imagem:
cole o conteúdo em https://www.plantuml.com/plantuml ou use a extensão PlantUML
da sua IDE (IntelliJ/VS Code) e exporte como PNG.
# lp2-repo
