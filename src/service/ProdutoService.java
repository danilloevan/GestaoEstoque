package service;

import domain.Produto;
import java.util.List;
import repository.ProdutoRepository;

public class ProdutoService {
    private final ProdutoRepository repository = new ProdutoRepository();

    public Produto cadastrarProduto(String nome, double preco, int quantidade) {
        if (nome != null && repository.buscarPorNome(nome) != null) {
            System.out.println(">> [CREATE] Falha: Já existe um produto cadastrado com o nome \"" + nome.trim() + "\".");
            return null;
        }

        try {
            Produto novo = new Produto(nome, preco, quantidade);
            repository.salvar(novo);
            System.out.println(">> [CREATE] Produto cadastrado: " + novo.getNome() + " (ID: " + novo.getId() + ")");
            return novo;
        } catch (IllegalArgumentException e) {
            System.out.println(">> [CREATE] Falha de validação: " + e.getMessage());
            return null;
        }
    }

    public Produto buscarPorId(int id) {
        return repository.buscarPorId(id);
    }

    public List<Produto> listarTodos() {
        return repository.listarTodos();
    }

    public boolean atualizarProduto(int id, String novoNome, double novoPreco, int novaQuantidade) {
        Produto produto = repository.buscarPorId(id);
        if (produto == null) {
            System.out.println(">> [UPDATE] Falha: Produto ID " + id + " não encontrado.");
            return false;
        }

        if (novoNome != null) {
            Produto existente = repository.buscarPorNome(novoNome);
            if (existente != null && existente.getId() != id) {
                System.out.println(
                    ">> [UPDATE] Falha: Já existe outro produto com o nome \"" + novoNome.trim() + "\"."
                );
                return false;
            }
        }

        try {
            produto.atualizarDados(novoNome, novoPreco, novaQuantidade);
            System.out.println(">> [UPDATE] Produto ID " + id + " atualizado com sucesso.");
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println(">> [UPDATE] Falha de validação (dados anteriores mantidos): " + e.getMessage());
            return false;
        }
    }

    public boolean movimentarEstoque(int id, int quantidade, boolean entrada) {
        Produto produto = repository.buscarPorId(id);
        if (produto == null) {
            System.out.println(">> [ESTOQUE] Falha: Produto ID " + id + " não encontrado.");
            return false;
        }

        try {
            if (entrada) {
                produto.adicionarEstoque(quantidade);
                System.out.println(
                    ">> [ESTOQUE] Entrada de " + quantidade + " unidade(s) no produto ID "
                        + id + ". Estoque atual: " + produto.getQuantidade()
                );
            } else {
                produto.removerEstoque(quantidade);
                System.out.println(
                    ">> [ESTOQUE] Saída de " + quantidade + " unidade(s) no produto ID "
                        + id + ". Estoque atual: " + produto.getQuantidade()
                );
            }
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println(">> [ESTOQUE] Falha na movimentação: " + e.getMessage());
            return false;
        }
    }

    public boolean removerProduto(int id) {
        Produto produto = repository.buscarPorId(id);
        if (produto == null) {
            System.out.println(">> [DELETE] Falha: Produto ID " + id + " não encontrado.");
            return false;
        }

        boolean removido = repository.removerPorId(id);
        if (removido) {
            System.out.println(">> [DELETE] Produto ID " + id + " (" + produto.getNome() + ") removido.");
        }
        return removido;
    }

    public double calcularPatrimonioTotal() {
        double total = 0.0;
        for (Produto p : repository.listarTodos()) {
            total += p.calcularValorTotalEstoque();
        }
        return total;
    }

    public void exibirResumoCicloDeVida() {
        System.out.println("--- Resumo do Estoque ---");
        System.out.println("Total de produtos criados: " + Produto.getTotalInstanciasCriadas());
        System.out.println("Próximo ID disponível: " + Produto.getProximoId());
        System.out.println("Produtos ativos no estoque: " + ProdutoRepository.getTotalProdutosAtivos());
        System.out.println(String.format("Patrimônio total em estoque: R$ %.2f", calcularPatrimonioTotal()));
    }
}
