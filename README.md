# Sistema de Gestão de Estoque (CRUD em Java)

Atividade prática desenvolvida para a disciplina de **Programação Orientada a Objetos (POO)** do curso de **Sistemas e Mídias Digitais (SMD)** da Universidade Federal do Ceará (UFC).

**Questão 4 da lista de exercícios do portfólio D.**

## Informações Acadêmicas

- **Aluno:** Danillo Evangelista Cabral
- **Matrícula:** 387967
- **Disciplina:** Programação Orientada a Objetos (POO)
- **Curso:** Sistemas e Mídias Digitais
- **Professor:** José Gilvan Rodrigues Maia

---

## Sobre o Projeto

A aplicação implementa um sistema simples de **Gestão de Estoque em memória** com operações completas de **CRUD** (*Create*, *Read*, *Update* e *Delete*), aplicando os conceitos de **encapsulamento** e **membros `static`** para gerenciar o ciclo de vida de objetos da classe `Produto`.

### Estrutura em Camadas

```text
GestaoEstoque/
└── src/
    ├── domain/
    │   └── Produto.java             # Entidade de domínio, validações e regras do produto
    ├── repository/
    │   └── ProdutoRepository.java   # Armazenamento em memória e controle de ativos
    ├── service/
    │   └── ProdutoService.java      # Regras de negócio e orquestração do CRUD
    └── application/
        └── Main.java                # Classe principal com simulação de uso do sistema
```

### Conceitos Aplicados

- **Encapsulamento:** os atributos de `Produto` (`id`, `nome`, `preco`, `quantidade`) são privados, com acesso controlado por `getters`, `setters` e métodos de domínio (`atualizarDados`, `adicionarEstoque`, `removerEstoque`), além de validações que lançam `IllegalArgumentException` para impedir estados inválidos. No repositório, `listarTodos()` retorna uma cópia da lista para proteger a coleção interna.
- **`static` e Ciclo de Vida:** a classe `Produto` utiliza `proximoId` para gerar IDs sequenciais únicos e `totalInstanciasCriadas` para contar objetos válidos criados, além das constantes `PRECO_MINIMO` e `QUANTIDADE_MINIMA`. Já `ProdutoRepository` mantém a lista em memória e `totalProdutosAtivos`, que aumenta no cadastro (`Create`) e diminui na remoção (`Delete`).

---

## Como Executar

### Pelo Eclipse
1. Acesse **File → Import... → General → Existing Projects into Workspace**.
2. Selecione a pasta `GestaoEstoque` e finalize a importação.
3. Execute `src/application/Main.java` com **Run As → Java Application**.