package domain;

public class Produto {
    public static final double PRECO_MINIMO = 0.01;
    public static final int QUANTIDADE_MINIMA = 0;

    private static int proximoId = 1;
    private static int totalInstanciasCriadas = 0;

    private final int id;
    private String nome;
    private double preco;
    private int quantidade;

    public Produto(String nome, double preco, int quantidade) {
        // Valida os dados antes de gerar o ID e incrementar o contador
        String nomeValidado = validarNome(nome);
        double precoValidado = validarPreco(preco);
        int quantidadeValidada = validarQuantidade(quantidade);

        this.id = Produto.proximoId++;
        Produto.totalInstanciasCriadas++;
        this.nome = nomeValidado;
        this.preco = precoValidado;
        this.quantidade = quantidadeValidada;
    }

    public static int getProximoId() {
        return proximoId;
    }

    public static int getTotalInstanciasCriadas() {
        return totalInstanciasCriadas;
    }

    public static String validarNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do produto não pode ser nulo ou vazio.");
        }
        return nome.trim();
    }

    public static double validarPreco(double preco) {
        if (preco < PRECO_MINIMO) {
            throw new IllegalArgumentException(
                String.format("O preço deve ser de no mínimo R$ %.2f.", PRECO_MINIMO)
            );
        }
        return preco;
    }

    public static int validarQuantidade(int quantidade) {
        if (quantidade < QUANTIDADE_MINIMA) {
            throw new IllegalArgumentException(
                "A quantidade em estoque não pode ser menor que " + QUANTIDADE_MINIMA + "."
            );
        }
        return quantidade;
    }

    public int getId() {
        return this.id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = validarNome(nome);
    }

    public double getPreco() {
        return this.preco;
    }

    public void setPreco(double preco) {
        this.preco = validarPreco(preco);
    }

    public int getQuantidade() {
        return this.quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = validarQuantidade(quantidade);
    }

    public void atualizarDados(String novoNome, double novoPreco, int novaQuantidade) {
        String nomeValidado = validarNome(novoNome);
        double precoValidado = validarPreco(novoPreco);
        int quantidadeValidada = validarQuantidade(novaQuantidade);

        this.nome = nomeValidado;
        this.preco = precoValidado;
        this.quantidade = quantidadeValidada;
    }

    public void adicionarEstoque(int qtd) {
        if (qtd <= 0) {
            throw new IllegalArgumentException("A quantidade de entrada deve ser maior que zero.");
        }
        this.quantidade += qtd;
    }

    public void removerEstoque(int qtd) {
        if (qtd <= 0) {
            throw new IllegalArgumentException("A quantidade de saída deve ser maior que zero.");
        }
        if (qtd > this.quantidade) {
            throw new IllegalArgumentException(
                "Estoque insuficiente. Disponível: " + this.quantidade + ", solicitado: " + qtd + "."
            );
        }
        this.quantidade -= qtd;
    }

    public double calcularValorTotalEstoque() {
        return this.preco * this.quantidade;
    }

    @Override
    public String toString() {
        return String.format(
            "Produto [ID=%d, Nome=%s, Preço=R$ %.2f, Estoque=%d, ValorTotal=R$ %.2f]",
            id,
            nome,
            preco,
            quantidade,
            calcularValorTotalEstoque()
        );
    }
}
