
public class Produto {
	private int codigo;
	private String nome;
	private double preco;
	private int quantEmEstoque;
	private String marca;
	
	public Produto() {
		
	}
		
	public Produto (int codigo, String nome, double preco, int quantEmEstoque, String marca) {
		this.codigo = codigo;
		this.nome = nome;
		this.preco = preco;
		this.quantEmEstoque = quantEmEstoque;
		this.marca = marca;
	}
	
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public double getPreco() {
		return preco;
	}
	public void setPreco(double preco) {
		this.preco = preco;
	}
	public int getQuantEmEstoque() {
		return quantEmEstoque;
	}
	public void setQuantEmEstoque(int quantEmEstoque) {
		this.quantEmEstoque = quantEmEstoque;
	} 
	
	public String getMarca() {
		return marca;
	}
	
	public void setMarca(String marca) {
		this.marca = marca;
	}
}
