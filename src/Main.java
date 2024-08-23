import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {
	static Scanner sc = new Scanner(System.in);
	static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	public static void main(String[] args) throws ParseException {
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
			System.out.println("2 - LISTAR ITENS DO CARRINHO");
			System.out.println("3 - GERAR VENDA");
			System.out.print("\n -> Digite a opção desejada: ");
			opcao = sc.nextInt();
			System.out.println("");
			
			switch(opcao) {
				case 0:
					System.out.println("Encerrando a sessão de atendente!");
					break;
				case 1:
					System.out.println("Selecione, por código, algum dos produtos da lista a seguir: ");
					listarProdutos(s, tamanho);
					int codigo = sc.nextInt();
					
					Produto produto = s.getProduto(codigo);
					
					System.out.println("\nDigite a quantidade dos produtos a serem adicionados no carrinho");
					int quantidade = sc.nextInt();

					int quantidadeSomada = quantidade + carrinho.quantidadeAtual(codigo);
					if(s.verificaQuantidade(codigo, quantidadeSomada)) {
						Item item = Item.getInstance(quantidade, produto);
						
						if(carrinho.addItem(item))
							System.out.println("Item adicionado ao carrinho com sucesso!");
						else
							System.out.println("Erro ao adicionar o item ao carrinho!");
						

					}else 
						System.out.println("Você não pode adicionar mais produtos do que o de estoque");
					
					break;
					
				case 2:
					listarItensCarrinho(carrinho, tamanho);
					break;
				case 3:
					Item itensVendidos[] = carrinho.getItems();

					
					System.out.println("Digite o nome do cliente: ");
					String nomeCliente = sc.next();
					
					if(!nomeCliente.equalsIgnoreCase("") && nomeCliente != null && calculaPrecoTotal(itensVendidos)
							> 0) {
						Venda venda = Venda.getInstance(new Date(), nomeCliente, calculaPrecoTotal(itensVendidos));
						venda.setItensVendidos(itensVendidos);

						if(s.addVenda(venda)) 
							System.out.println("Venda realizada com sucesso!");
						else
							System.out.println("Erro ao finalizar venda!");
					}else {
						System.out.println("Não foi possível concluir a venda!");
					}
					
					carrinho = new Carrinho();
					break;
				default:
					break;
			}
		}while(opcao != 0);
	}
	
	static double calculaPrecoTotal(Item[] itensVendidos) {
		double soma = 0;
		for(int i = 0; i < itensVendidos.length; i++) {
			soma+= itensVendidos[i].getPreco() * itensVendidos[i].getQuantidade();
		}
		
		return soma;
	}
	
	static void administrador(Sistema s, int tamanho) {
		int opcao = 0;
		do {
			System.out.println("\n------- CONTROLE DE PRODUTOS (Administrador) -------\n");
			System.out.println("0- SAIR");
			System.out.println("1- adicionar produto");
			System.out.println("2- excluir produto");
			System.out.println("3- editar produto");
			System.out.println("4- listar produtos");
			System.out.println("5- Alterar o tamanho das colunas das listagens");
			System.out.println("6- Listar vendas");
			
			System.out.print("\n -> Digite a opção desejada: ");
			opcao = sc.nextInt();
			System.out.println("");
			
			switch (opcao) {
			case 0:
				System.out.println("Encerrando a sessão de administrador");
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
			case 6:
				menuListagemVendas(s, tamanho);
				break;
			default:
				break;
			}
			
			} while (opcao != 0);
	}
	
	static void init(Sistema s) throws ParseException {
		Produto produto;	
		Item item;
		Item[] itensVendidos = new Item[2];
		Item[] itensVendidos1 = new Item[3];
		
		produto = new Produto(1, "Arroz 5kg", 21.00, 1000, "Rivieira");
		s.inserir(produto);
		item = Item.getInstance(2, produto);
		itensVendidos[0] = item;
		
		produto = new Produto(2, "Azeite 250ml", 45.00, 936, "Costa Doce");
		s.inserir(produto);
		item = Item.getInstance(3, produto);
		itensVendidos[1] = item;
		
		produto = new Produto(3, "Margarina", 5.29, 850, "Qualy");
		s.inserir(produto);
		item = Item.getInstance(4, produto);
		itensVendidos1[0] = item;
		
		produto = new Produto(4, "Café", 20.99, 256, "Pilão");	
		s.inserir(produto);
		item = Item.getInstance(5, produto);
		itensVendidos1[1] = item;
		
		produto = new Produto(5, "Picanha", 149.98, 28, "Maturatta");	
		s.inserir(produto);
		item = Item.getInstance(6, produto);
		itensVendidos1[2] = item;

		Venda venda = Venda.getInstance(sdf.parse("08/08/2024"), "João", calculaPrecoTotal(itensVendidos));
		venda.setItensVendidos(itensVendidos);
		s.addVenda(venda);
		
		venda = Venda.getInstance(sdf.parse("14/08/2024"), "Joaquim", calculaPrecoTotal(itensVendidos1));
		venda.setItensVendidos(itensVendidos1);
		s.addVenda(venda);
	}
	
	public static void menuListagemVendas(Sistema s, int espaco) {
		System.out.println("\n----- Listagem de Vendas ------");
		System.out.println("1 - Listar todas as vendas realizadas");
		System.out.println("2 - Listar todas as vendas por uma data");
		System.out.println("3 - Pesquisar venda por código");
		System.out.println("Digite alguma das opções acima");
		int codigo = sc.nextInt();
		
		switch(codigo) {
			case 1:
				listarVendas(s, espaco);
				break;
			case 2:
				System.out.println("Digite uma data: (dd/MM/yyyy)");
				String data = sc.next();
				listarVendasPorData(s, espaco, data);
				break;
			case 3:
				System.out.println("Digite o código de uma venda");
				int codigoVenda = sc.nextInt();
				listarVendaPorCodigo(s, espaco, codigoVenda);
				break;
			default:
				break;
		}
	}
	
	static Produto lerProduto(Sistema s) {
		Produto produto;
		System.out.println(" - Ler produto - ");
		System.out.print("Nome: ");
		String nome = sc.next();
		System.out.print("Preco: ");
		double preco = sc.nextDouble();
		System.out.print("Marca: ");
		String marca = sc.next();
		System.out.print("Quantidade em estoque: ");
		int quantidadeEmEstoque = sc.nextInt();
		
		produto = new Produto(0, nome,preco,quantidadeEmEstoque, marca);
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
				Produto produto = null;
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
	
	public static void listarVendas(Sistema s, int espaco) {
		System.out.println();
		System.out.printf("%-" + espaco + "s%-" + espaco + "s%-" + espaco + "s\n",
                "CÓDIGO", "DATA", "VALOR");

	    Venda[] vendas = s.retornaVendas();
	    
	    for (Venda venda : vendas) {
	        if (venda != null) {
	            System.out.printf("%-" + espaco + "d%-" + espaco + "s%-" + espaco + ".2f\n",
	                    venda.getCodigo() + 1, sdf.format(venda.getData()), venda.getPrecoTotal());
	        }
	    }
	}
	
	public static void listarVendasPorData(Sistema s, int espaco, String data) {
		System.out.println();
		System.out.printf("%-" + espaco + "s%-" + espaco + "s%-" + espaco + "s\n",
                "CÓDIGO", "DATA", "VALOR");

	    Venda[] vendas = s.retornaVendas();
	    
	    for (Venda venda : vendas) {
	        if (venda != null && sdf.format(venda.getData()).equalsIgnoreCase(data)) {
	            System.out.printf("%-" + espaco + "d%-" + espaco + "s%-" + espaco + ".2f\n",
	                    venda.getCodigo() + 1, sdf.format(venda.getData()), venda.getPrecoTotal());
	        }
	    }
	}
	
	public static void listarVendaPorCodigo(Sistema s, int espaco, int codigo) {
		Venda venda = s.retornaVendaPorCodigo(codigo);
		
		System.out.println("\n\n");
		System.out.printf("%-" + espaco + "s%-" + espaco + "s%-" + espaco + "s\n",
                "CÓDIGO", "DATA", "NOME CLIENTE");
		
		if(venda != null) {
			System.out.printf("%-" + espaco + "d%-" + espaco + "s%-" + espaco + "s\n", venda.getCodigo() + 1,
					sdf.format(venda.getData()), venda.getNomeCliente());
		
			System.out.println();
			System.out.printf("%-" + espaco + "s%-" + espaco + "s%-"+ espaco + "s\n",
	                "PRODUTOS", "QUANTIDADE", "PRECO UN");
			
			Item[] itensVendidos = venda.getItensVendidos();
			for(int i = 0; i < itensVendidos.length; i++) {
				if(itensVendidos[i] != null)
					System.out.printf("%-" + espaco + "s%-" + espaco + "d%-" + espaco + ".2f\n",
			                itensVendidos[i].getProduto().getNome(), itensVendidos[i].getQuantidade(),
			                itensVendidos[i].getPreco());
			}
			
			System.out.printf("\nPreco total: %.2f", venda.getPrecoTotal());
		}
	}
	
}
