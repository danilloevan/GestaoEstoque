package application;

import domain.Produto;
import java.util.List;
import repository.ProdutoRepository;
import service.ProdutoService;

public class Main {
    public static void main(String[] args) {
        ProdutoService service = new ProdutoService();

        System.out.println("=== SISTEMA DE GESTÃO DE ESTOQUE ===");
        System.out.println("Produtos ativos inicialmente: " + ProdutoRepository.getTotalProdutosAtivos());
        System.out.println("Total de produtos criados: " + Produto.getTotalInstanciasCriadas());
        System.out.println();

        // 1. CREATE
        System.out.println("--- 1. Operação CREATE ---");
        service.cadastrarProduto("Teclado Mecânico", 250.00, 15);
        service.cadastrarProduto("Mouse Sem Fio", 120.00, 30);
        service.cadastrarProduto("Monitor 24 Pol", 890.00, 8);

        System.out.println("Tentando cadastrar produto com dados inválidos:");
        service.cadastrarProduto("   ", -10.00, 5);
        System.out.println("Tentando cadastrar produto duplicado:");
        service.cadastrarProduto("Mouse Sem Fio", 120.00, 10);

        System.out.println("Produtos ativos após CREATE: " + ProdutoRepository.getTotalProdutosAtivos());
        System.out.println("Total de produtos criados: " + Produto.getTotalInstanciasCriadas());
        System.out.println();

        // 2. READ
        System.out.println("--- 2. Operação READ ---");
        imprimirProdutos(service.listarTodos());
        System.out.println("\nBuscando produto com ID 2:");
        Produto consultado = service.buscarPorId(2);
        System.out.println("Encontrado: " + consultado);
        System.out.println();

        // 3. UPDATE
        System.out.println("--- 3. Operação UPDATE e Movimentação de Estoque ---");
        System.out.println("Atualizando dados do Mouse (ID 2):");
        service.atualizarProduto(2, "Mouse Sem Fio Gamer", 145.00, 25);

        System.out.println("Registrando entrada de 5 unidades e tentativa de saída inválida:");
        service.movimentarEstoque(2, 5, true);
        service.movimentarEstoque(2, 100, false);

        System.out.println("Tentando atribuir preço negativo:");
        service.atualizarProduto(2, "Mouse Sem Fio Gamer Pro", -50.00, 25);
        System.out.println("Estado do produto após tentativa inválida: " + service.buscarPorId(2));
        System.out.println();

        // 4. DELETE
        System.out.println("--- 4. Operação DELETE ---");
        System.out.println("Removendo produto ID 1 (Teclado Mecânico):");
        service.removerProduto(1);
        System.out.println("Total de produtos ativos após exclusão: " + ProdutoRepository.getTotalProdutosAtivos());
        System.out.println();

        // 5. Novo cadastro após exclusão
        System.out.println("--- 5. Novo CREATE após DELETE ---");
        service.cadastrarProduto("Headset USB", 199.90, 12);
        System.out.println();

        // 6. Estado final
        System.out.println("--- 6. Estado Final do Estoque ---");
        imprimirProdutos(service.listarTodos());
        System.out.println();
        service.exibirResumoCicloDeVida();
    }

    private static void imprimirProdutos(List<Produto> produtos) {
        System.out.println("--- Lista de Produtos Cadastrados ---");
        if (produtos.isEmpty()) {
            System.out.println("(Nenhum produto cadastrado)");
            return;
        }
        for (Produto p : produtos) {
            System.out.println(p);
        }
    }
}
