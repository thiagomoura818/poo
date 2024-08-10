import java.util.Date;

public class Venda {
	private static int codigo;
	private Date data;
	private Item itensVendidos[];
	private String nomeCliente;
	
	
	private Venda(Date data, String nomeCliente) {
		this.nomeCliente = nomeCliente;
		this.data = data;
		
		codigo++;

	}
	
	public static Venda getInstance(Date data, String nomeCliente) {
		if(nomeCliente == null)
			return null;
		return new Venda(data, nomeCliente);
	}
	
	public int getCodigo() {
		return this.codigo;
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
	
}
