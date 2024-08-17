import java.text.SimpleDateFormat;
import java.util.Date;

public class Venda {
	private int codigo;
	private Date data;
	private Item itensVendidos[];
	private String nomeCliente;
	
	private double precoTotal;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	private Venda(Date data, String nomeCliente, double precoTotal) {
		this.nomeCliente = nomeCliente;
		this.data = data;
		this.precoTotal = precoTotal;

	}
	
	public static Venda getInstance(Date data, String nomeCliente, double precoTotal) {
		if(nomeCliente == null && precoTotal <= 0)
			return null;
		return new Venda(data, nomeCliente, precoTotal);
	}
	
	public int getCodigo() {
		return this.codigo;
	}
	
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	
	public Date getData() {
		return this.data;
	}
	
	public String getNomeCliente() {
		return this.nomeCliente;
	}
	
	public void setItensVendidos(Item[] itensVendidos) { 
		this.itensVendidos = itensVendidos;
	}
	
	public Item[] getItensVendidos() {
		return itensVendidos;
	}
	
	public double getPrecoTotal() {
		return this.precoTotal;
	}
	
	
}
