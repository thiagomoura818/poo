import java.util.Date;
import java.util.Scanner;

public class Main {
	static Scanner sc = new Scanner(System.in);
	public static void main(String[] args) {
		Sistema s = Sistema.getInstance();
		init(s);
		
		int opcao;
		int tamanho = 20;
		do {
			
			System.out.println("\n--------- ESCOLHA O USUARIO ---------- ");
			System.out.println("0- Encerrar a sessão");
			System.out.println("1- Administrador");
			System.out.println("2- Atendente");
			opcao = sc.nextInt();
			switch(opcao) {
				case 1:
					administrador(s, tamanho);
					break;
				case 2:
					atendente(s, tamanho);
					break;
			}

		} while (opcao != 0);

		System.out.println("Fim");
		
	}
	
	static void atendente(Sistema s, int tamanho) {
		int opcao;
		Carrinho carrinho = new Carrinho();
		do {
			System.out.println("\n-------- MÓDULO ATENDENTE -------- ");
			System.out.println("0 - SAIR");
			System.out.println("1 - ADICIONAR ITEM AO CARRINHO");
			System.out.println("2 - REMOVER ITEM DO CARRINHO");
			System.out.println("3 - LISTAR ITENS DO CARRINHO");
			System.out.println("4 - GERAR VENDA");
			System.out.print("\n -> Digite a opção desejada: ");
			opcao = sc.nextInt();
			System.out.println("");
			
			switch(opcao) {
				case 0:
					System.out.println("Encerrando o programa");
					break;
				case 1:
					System.out.println("Selecione, por código, algum dos produtos da lista a seguir: ");
					listarProdutos(s, tamanho);
					int codigo = sc.nextInt();
					
					Produto produto = s.getProduto(codigo);
					
					System.out.println("\nDigite a quantidade dos produtos a serem adicionados no carrinho");
					int quantidade = sc.nextInt();
					
					if(quantidade > produto.getQuantEmEstoque()) {
						System.out.println("Não é possível colocar uma quantidade maior "
								+ "do que a quantidade em estoque");
						break;
					}
					
					Item item = Item.getInstance(quantidade, produto);
					
					if(carrinho.addItem(item))
						System.out.println("Item adicionado ao carrinho com sucesso!");
					else
						System.out.println("Erro ao adicionar o item ao carrinho!");
					
					break;
				case 2:
					
					break;
				case 3:
					listarItensCarrinho(carrinho, tamanho);
					break;
				case 4:
					Item itensVendidos[] = carrinho.getItems();
					System.out.println("Digite o nome do cliente: ");
					String nomeCliente = sc.next();
					Venda venda = Venda.getInstance(new Date(), nomeCliente);
					venda.setItensVendidos(itensVendidos);
					if(s.addVenda(venda)) {
						System.out.println("Venda realizada com sucesso!");
					}else {
						System.out.println("Erro ao finalizar venda!");
					}
					
					carrinho = new Carrinho();
					break;
			}
		}while(opcao != 0);
	}
	
	static void administrador(Sistema s, int tamanho) {
		int opcao = 0;
		do {
			System.out.println("\n------- CONTROLE DE PRODUTOS (Administrador) -------\n");
			System.out.println("0- SAIR");
			System.out.println("1- adicionar produto");
			System.out.println("2- excluir produto");
			System.out.println("3- editar");
			System.out.println("4- listar");
			System.out.println("5- Alterar o tamanho das colunas das listagens");
			
			System.out.print("\n -> Digite a opção desejada: ");
			opcao = sc.nextInt();
			System.out.println("");
			
			switch (opcao) {
			case 0:
				System.out.println("Encerrando o programa");
				break;
			case 1:
				adicionarProduto(s);
				break;
			case 2:
				excluirProduto(s, tamanho);
				break;
			case 3:
				editarProduto(s, tamanho);
				break;
			case 4:
				listarProdutos(s, tamanho);
				
				break;
			case 5:
				System.out.printf("%nDigite o novo tamanho das listagens: ");
				tamanho = sc.nextInt();
				System.out.println("");
				break;
			default:
				break;
			}
			
			} while (opcao != 0);
	}
	
	static void init(Sistema s) {
		Produto produto = new Produto();	
		produto.setCodigo(1);
		produto.setNome("Arroz 5kg");
		produto.setPreco(21.00);
		produto.setMarca("Rivieira");
		produto.setQuantEmEstoque(1000);
		s.inserir(produto);
		
		produto = new Produto();	
		produto.setCodigo(2);
		produto.setNome("Azeite 250ml");
		produto.setPreco(45.00);
		produto.setMarca("Costa Doce");
		produto.setQuantEmEstoque(936);
		s.inserir(produto);
		
		produto = new Produto();	
		produto.setCodigo(3);
		produto.setNome("Margarina");
		produto.setPreco(5.29);
		produto.setMarca("Qualy");
		produto.setQuantEmEstoque(850);
		s.inserir(produto);
		
		produto = new Produto();	
		produto.setCodigo(4);
		produto.setNome("Café");
		produto.setPreco(20.99);
		produto.setMarca("Pilão");
		produto.setQuantEmEstoque(256);
		s.inserir(produto);
		
		produto = new Produto();	
		produto.setCodigo(4);
		produto.setNome("Picanha");
		produto.setPreco(149.98);
		produto.setMarca("Maturatta");
		produto.setQuantEmEstoque(28);
		s.inserir(produto);
	}
	
	static Produto lerProduto(Sistema s) {
		Produto produto = new Produto();
		System.out.println(" - Ler produto - ");
		System.out.print("Nome: ");
		produto.setNome(sc.next());
		System.out.print("Preco: ");
		produto.setPreco(sc.nextDouble());
		System.out.print("Marca: ");
		produto.setMarca(sc.next());
		System.out.print("Quantidade em estoque: ");
		produto.setQuantEmEstoque(sc.nextInt());

		return produto;
	}
	
	static void listarItensCarrinho(Carrinho carrinho, int espaco) {
	    System.out.println();
	    System.out.printf("%-" + espaco + "s%-" + espaco + "s%-" + espaco + "s\n",
	             "NOME", "PREÇO", "QUANTIDADE");
	    
	    Item[] itens = carrinho.getItems();
	    
	    for (Item item : itens) {
	    	if(item != null)
	    		System.out.printf("%-" + espaco + "s%-" + espaco + ".2f%-" + espaco + "d\n",
	   	             item.getProduto().getNome(), (item.getQuantidade() * item.getPreco()), item.getQuantidade());
	        }
	    

}
	
	static void listarProdutos(Sistema s, int tamanho) {
		
		System.out.println("\nSub-menu de listagem de produtos");
		System.out.println("-> 1 - Listar todos por ordem de cadastro: ");
		System.out.println("-> 2 - Listar todos por ordem alfabética: ");
		System.out.println("-> 3 - Listar um único produto: ");
		int opcao = sc.nextInt();
		
		switch(opcao) {
			case 1:
				Produto[] produtos = s.listarTodos();
				exibirTodosProdutos(produtos, tamanho);
				break;
			case 2:
				produtos = s.vetorOrdenado();
				exibirOrdenado(produtos, tamanho);
			case 3:
				System.out.println("\n-> 1 - Buscar por nome ");
				System.out.println("-> 2 - Buscar por código ");
				opcao = sc.nextInt();
				Produto produto = new Produto();
				switch(opcao){
					case 1:
						System.out.println("Digite o nome: ");
						String nome = sc.next();
						produto = s.buscarString(nome);
						break;
					case 2:
						System.out.println("Digite o código: ");
						int codigo = sc.nextInt();
						produto = s.buscar(codigo);
						break;
					default:
						break;
				}
				
				if(produto == null)
					System.out.println("Produto não encontrado!");
				else
					exibirDetalhado(produto, tamanho);
				break;
				
			default:
				break;
		}
	}
	
	static void exibirDetalhado(Produto produto, int espaco) {
		    System.out.println();
		    System.out.printf("%-" + espaco + "s%-" + espaco + "s%-" + espaco + "s%-" + espaco + "s%-" + espaco + "s\n",
		            "CÓDIGO", "NOME", "MARCA", "PREÇO", "QUANTIDADE");

            System.out.printf("%-" + espaco + "d%-" + espaco + "s%-" + espaco + "s%-" + espaco + ".2f%-" + espaco + "d\n",
                    produto.getCodigo(), produto.getNome(), produto.getMarca(),
                    produto.getPreco(), produto.getQuantEmEstoque());

	}
	
	static void listagemCompleta(Produto[] produtos, int espaco) {
	    System.out.println();
	    System.out.printf("%-" + espaco + "s%-" + espaco + "s%-" + espaco + "s%-" + espaco + "s%-" + espaco + "s\n",
	            "CÓDIGO", "NOME", "MARCA", "PREÇO", "QUANTIDADE");

	    for (Produto produto : produtos) {
	    	if(produto != null)
	    		System.out.printf("%-" + espaco + "d%-" + espaco + "s%-" + espaco + "s%-" + espaco + ".2f%-" + espaco + "d\n",
	                    produto.getCodigo(), produto.getNome(), produto.getMarca(),
	                    produto.getPreco(), produto.getQuantEmEstoque());
	        }
	}
	
	static void exibirOrdenado(Produto[] produtos, int espaco) {
	    System.out.println();
	    System.out.printf("%-" + espaco + "s%-" + espaco + "s%-" + espaco + "s\n",
	            "CÓDIGO", "NOME", "PREÇO");

	    for (Produto produto : produtos) {
	        if (produto != null) {
	            System.out.printf("%-" + espaco + "d%-" + espaco + "s%-" + espaco + ".2f\n",
	                    produto.getCodigo(), produto.getNome(),
	                    produto.getPreco());
	        }
	    }
	}
	
	static void exibirTodosProdutos(Produto[] produtos, int espaco) {
	    System.out.println();
	    System.out.printf("%-" + espaco + "s%-" + espaco + "s%-" + espaco + "s%-" + espaco + "s\n",
	            "CÓDIGO", "NOME", "PREÇO", "QUANTIDADE");

	    for (Produto produto : produtos) {
	        if (produto != null) {
	            System.out.printf("%-" + espaco + "d%-" + espaco + "s%-" + espaco + ".2f%-" + espaco + "d\n",
	                    produto.getCodigo(), produto.getNome(),
	                    produto.getPreco(), produto.getQuantEmEstoque());
	        }
	    }
	}


	static void adicionarProduto(Sistema s) {
		Produto produto = lerProduto(s);
		if(s.inserir(produto))
			System.out.println("Produto inserido com sucesso!");
		else
			System.out.println("\nATENÇAO: Falha ao adicionar produto!");
	}
	
	static void excluirProduto(Sistema s, int tamanho) {
		Produto[] produto = s.listarTodos();
		listagemCompleta(produto, tamanho);
		System.out.println("\nDigite o código do produto que você deseja excluir!");
		int codigo = sc.nextInt();
		if(s.excluir(codigo))
			System.out.println("\nProduto excluido com sucesso!");
		else
			System.out.println("ATENÇÃO: Falha ao excluir produto!");
	}
	
	static void editarProduto(Sistema s, int tamanho) {
		listagemCompleta(s.listarTodos(), tamanho);
		System.out.print("\nDigite o código do produto que você deseja editar: ");
		int codigo = sc.nextInt();
		boolean atualizarNome = false;
		Produto produto = s.buscar(codigo);
		
		if(produto == null) {
			System.out.println("Produto inválido!");
		}else {
			System.out.println("\nATENÇÃO -> Produto selecionado com sucesso!\n");
			System.out.println("-> Escolha uma das seguintes opções: \n");
			System.out.println("0- Voltar ao menu anterior");
			System.out.println("1- Editar o nome do produto");
			System.out.println("2- Editar a marca do produto");
			System.out.println("3- Editar o preço do produto");
			System.out.println("4- Editar a quantidade em estoque do produto");
			int opcao = sc.nextInt();
			
			switch(opcao) {
				case 0:
					return;
				case 1:
					System.out.println("Digite o nome do Produto que deseja alterar: ");
					atualizarNome = true;
					produto.setNome(sc.next());
					break;
				case 2:
					System.out.println("Digite o nome da Marca que deseja alterar: ");
					produto.setMarca(sc.next());
					break;
				case 3:
					System.out.println("Digite o preço do produto que deseja alterar: ");
					produto.setPreco(sc.nextDouble());
					break;
				case 4:
					System.out.println("Digite a quantidade de estoque do produto: ");
					produto.setQuantEmEstoque(sc.nextInt());
					break;
				default:
					System.out.println("Opcao invalida");
					break;
			}
			
			if(s.editar(produto, atualizarNome)) 
				System.out.println("\nProduto alterado com sucesso!");
			else 
				System.out.println("ATENÇÃO: Falha ao alterar produto!");
			
		}
	}
}
