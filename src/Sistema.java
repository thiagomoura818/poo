
public class Sistema {
	private Produto[] produtos;
	private int codigo;
	private int qntdProdutos;
	private Venda[] vendas;
	private int qntdVendas;
	
	private static Sistema instance;
	
	private Sistema() {
		this.codigo = 1;
		
		produtos = new Produto[20];
		vendas = new Venda[20];
		
		qntdVendas = 0;
	}

	public static Sistema getInstance() {
		if(instance == null)
			instance = new Sistema();
		
		return instance;
	}
	
	/*A função inserir(Produto) é uma funçao que insere o produto recebido no vetor de produtos da classe
	 * Ela faz a verificação para saber se o tamanho do vetor de classe foi ultrapassado
	 * Caso isso ocorra, ela define um novo vetor com o dobro do anterior e utiliza um mecanismo de mudança de
	 * referencia
	 * Vale ressaltar que ela verifica se já existe algum produto com o nome repetido na lista de produtos 
	 * anteriormente registrada.
	 * */
	boolean inserir(Produto produto) {
		if(produto != null) {
			if(qntdProdutos == produtos.length) {
				Produto[] produtoAuxiliar = new Produto[2*produtos.length];
				for(int i = 0; i < qntdProdutos; i++) 
					produtoAuxiliar[i] = produtos[i];
				produtos = produtoAuxiliar;
			}
			
			if(verificarNomeProduto(produto.getNome())) {
				produto.setCodigo(codigo);
				codigo++;
				produtos[qntdProdutos] = produto;
				qntdProdutos++;
				
				return true;
			}else
				return false;
		
		}else
			return false;
	}
	
	/*Função 2: Excluir produto
	 * A função excluir(int) recebe um codigo de um produto e verifica se esse codigo é valido
	 * Ela percorre o vetor de produtos até encontrar o produto escolhido
	 * Ela da um shift para direita e exclui o produto
	 * */
	boolean excluir(int codigo) {
		if(codigo > 0 && !realizouVenda(codigo)) {
			for(int i = 0; i < qntdProdutos; i++) {
				if(produtos[i] != null && produtos[i].getCodigo() == codigo) {
					for(int j = i; j < produtos.length - 1; j++) {
						produtos[j] = produtos[j+1];
					}
					qntdProdutos--;
					produtos[qntdProdutos] = null;
					return true;
				}
			}
			return false;
		}
		return false;
	}
	
	boolean realizouVenda(int codigo) {
		for(int i = 0; i < qntdVendas; i++) {
			if(vendas[i].getCodigo() == codigo) 
				return true;
		}
		return false;
	}
	
	/*A função editar(Produto) recebe um Produto e faz as verificações, caso haja um produto com o mesmo nome
	 * do produto recebido, a função retorna falso.
	 * Caso nao haja nenhum produto com o mesmo nome, ela vai procurar o produto da lista de produtos com o mesmo
	 * codigo do produto recebido e fazer a alteração.
	 * */
	boolean editar(Produto produtoAlterado, boolean atualizarNome) {
		for(int i = 0; i < qntdProdutos; i++) {
				if(produtoAlterado.getCodigo() == produtos[i].getCodigo()){
					if(atualizarNome && verificarNomeProduto(produtoAlterado.getNome())) {
						produtos[i] = produtoAlterado;
						return true;
					}else if(!atualizarNome) {
						produtos[i] = produtoAlterado;
						return true;
					}
				}
			}
		return false;
	}
	
	/*
	 * A função buscar(codigo) busca um produto através de um código especificado. 
	 * Vale ressaltar que a função cria um produto para que não haja modificação do produto já dentro do vetor de
	 * produtos.
	 * */
	Produto buscar(int codigo) {
		if(codigo > 0) {
			for(int i = 0; i < produtos.length; i++) {
				if(produtos[i].getCodigo() == codigo) {
					Produto paux = new Produto();
					paux.setCodigo(produtos[i].getCodigo());
					paux.setNome(produtos[i].getNome());
					paux.setMarca(produtos[i].getMarca());
					paux.setPreco(produtos[i].getPreco());
					paux.setQuantEmEstoque(produtos[i].getQuantEmEstoque());
					
					return paux;
				}
			}
		}
		
		return null;
		
	}
	
	/* A funçao buscarString(String) recebe uma string nome pela qual deseja se especificar os dados do produto, primeiro faz uma verificação
	 * se o produto existe e logo em seguida usa um for para percorrer o produto e achar a sua posiçao.
	 * */
	
	Produto buscarString(String nome) {
		if(!verificarNomeProduto(nome)) {
			for(int i = 0; i < produtos.length; i++) {
				if(produtos[i].getNome().equalsIgnoreCase(nome)) {
					Produto paux = new Produto();
					paux.setCodigo(produtos[i].getCodigo());
					paux.setNome(produtos[i].getNome());
					paux.setMarca(produtos[i].getMarca());
					paux.setPreco(produtos[i].getPreco());
					paux.setQuantEmEstoque(produtos[i].getQuantEmEstoque());
					
					return paux;
				}
			}
		}
		
		return null;
		
	}
	
	/* A função listarTodos() devolve um vetor de produtos, mas não devolve a referencia do vetor de produtos 
	 * definido na classe, e sim um vetor com a cópia dos seus valores.
	 * */
	
	Produto[] listarTodos() {
		//return produtos;
		
		Produto[] paux = new Produto[qntdProdutos];
		for(int i = 0; i < qntdProdutos; i++) {
			paux[i] = new Produto();
			paux[i].setCodigo(produtos[i].getCodigo());
			paux[i].setNome(produtos[i].getNome());
			paux[i].setMarca(produtos[i].getMarca());
			paux[i].setPreco(produtos[i].getPreco());
			paux[i].setQuantEmEstoque(produtos[i].getQuantEmEstoque());
		}
		
		return paux;
	}
	
	/*A função vericarNomeProduto(String)
	 * verifica se já existe algum produto com o nome especificado já registrado dentro do vetor
	 * de produtos.
	 * */
	
	private boolean verificarNomeProduto(String produtoNome) {
		for(int i = 0; i < qntdProdutos; i++) {
			if(produtos[i].getNome().equalsIgnoreCase(produtoNome)) {
				return false;
			}
		}
		return true;
	}
	
	/*A função vetorOrdenado() retorna o vetor de produtos ordenado pela ordem alfabética
	 * */
	public Produto[] vetorOrdenado() {
        Produto auxP = new Produto();
        Produto[] vetAux = listarTodos();
        for (int i = 0; i < qntdProdutos; i++) {
            for (int j = i + 1; j < qntdProdutos; j++) {
                if ((vetAux[i].getNome()).compareToIgnoreCase(vetAux[j].getNome()) > 0) {
                    auxP = vetAux[i];
                    vetAux[i] = vetAux[j];
                    vetAux[j] = auxP;
                }
            }
        }
        return vetAux;
	}
	
	public Produto getProduto(int codigo) {
		if(codigo > 0) {
			for(int i = 0; i < qntdProdutos; i++) {
				if(produtos[i].getCodigo() == codigo)
					return produtos[i];
			}
		}
		return null;
	}
	
	public void descontarProdutos(int codigo, int quantidade) {
		for(int i = 0; i < qntdProdutos; i++) {
			if(produtos[i].getCodigo() == codigo) {
				produtos[i].setQuantEmEstoque(produtos[i].getQuantEmEstoque() - quantidade);
			}
		}
	}
	
	public boolean addVenda(Venda venda) {
		if(venda != null) {
			vendas[qntdVendas] = venda;
			for(int i = 0; i < venda.getItensVendidos().length; i++) {
				descontarProdutos(venda.getItensVendidos()[i].getProduto().getCodigo(), venda.getItensVendidos()[i].getQuantidade());
			}
			qntdVendas++;
			return true;
		}
		
		return false;
	}
}
