package repository;

import domain.Produto;
import java.util.ArrayList;
import java.util.List;

public class ProdutoRepository {
    private static final List<Produto> produtos = new ArrayList<>();
    private static int totalProdutosAtivos = 0;

    public void salvar(Produto produto) {
        if (produto == null) {
            throw new IllegalArgumentException("Não é possível salvar um produto nulo.");
        }
        if (buscarPorId(produto.getId()) != null) {
            throw new IllegalArgumentException("Já existe um produto salvo com o ID " + produto.getId() + ".");
        }
        produtos.add(produto);
        totalProdutosAtivos++;
    }

    public Produto buscarPorId(int id) {
        for (Produto p : produtos) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    public Produto buscarPorNome(String nome) {
        if (nome == null) {
            return null;
        }
        String nomeBuscado = nome.trim();
        for (Produto p : produtos) {
            if (p.getNome().equalsIgnoreCase(nomeBuscado)) {
                return p;
            }
        }
        return null;
    }

    public List<Produto> listarTodos() {
        // Retorna uma cópia para evitar alterações diretas na lista interna
        return new ArrayList<>(produtos);
    }

    public boolean removerPorId(int id) {
        Produto encontrado = buscarPorId(id);
        if (encontrado != null) {
            produtos.remove(encontrado);
            totalProdutosAtivos--;
            return true;
        }
        return false;
    }

    public static int getTotalProdutosAtivos() {
        return totalProdutosAtivos;
    }
}
