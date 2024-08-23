
public class Carrinho {
	private Item itens[];
	private int posAtual;
	
	public Carrinho() {
		posAtual = 0;
		itens = new Item[25];
	}
	
	public boolean addItem(Item item) {
		if(item == null)
			return false;
		int pos = localizaItem(item);
		if(pos != -1) {
			itens[pos].setQuantidade(itens[pos].getQuantidade() + item.getQuantidade());
			return true;
		}else
			itens[posAtual] = item;
		
		posAtual++;
		return true;
	}
	
	public Item[] getItems() {
		Item itens[] = new Item[posAtual];
		for(int i = 0; i < posAtual; i++) {
			itens[i] = this.itens[i];
		}
		
		return itens;
	}
	
	public int localizaItem(Item item) {
		for(int i = 0; i < posAtual; i++) {
			if(itens[i].getProduto().getCodigo() == item.getProduto().getCodigo())
				return i;
		}
		return -1;
	}
	
	public int quantidadeAtual(int codigoProduto) {
		for(int i = 0; i < itens.length; i++) {
			if(codigoProduto > 0 && itens[i]!= null && itens[i].getProduto().getCodigo() == codigoProduto)
				return itens[i].getQuantidade();
		}
		
		return -1;
	}
}
