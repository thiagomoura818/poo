
public class Item {
	private double preco;
	private int quantidade;
	private Produto produto;
	
	
	
	private Item(double preco, int quantidade, Produto produto) {
		this.preco = preco;
		this.quantidade = quantidade;
		this.produto = produto;
	}
	
	public static Item getInstance(int quantidade, Produto produto) {
		if(quantidade > 0 && produto != null)
			return new Item(produto.getPreco(), quantidade, produto);
		else 
			return null;
	}

	public double getPreco() {
		return preco;
	}
	
	public void setPreco(double preco) {
		this.preco = preco;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public Produto getProduto() {
		return produto;
	}
	
}
