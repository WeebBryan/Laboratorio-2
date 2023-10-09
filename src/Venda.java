import java.util.ArrayList;
import java.util.List;

class Venda {
	private Cliente cliente;
	private List<CachorroQuente> cachorrosQuentes;
	private double desconto;

	public Venda(Cliente cliente) {
		this.cliente = cliente;
		this.cachorrosQuentes = new ArrayList<>();
	}

	public void adicionarCachorroQuente(CachorroQuente cachorroQuente) {
		cachorrosQuentes.add(cachorroQuente);
	}

	public Cliente getCliente() {
		return cliente;
	}

	public List<CachorroQuente> getCachorrosQuentes() {
		return cachorrosQuentes;
	}

	public double calcularTotal() {
		double total = 0.0;
		for (CachorroQuente cq : cachorrosQuentes) {
			total += cq.getPreco();
		}
		return total;
	}

	public double aplicarDesconto() {
		double total = calcularTotal();
		int quantidadeCachorros = cachorrosQuentes.size();

		if ((cliente.getIdentificacao().startsWith("S") || cliente.getIdentificacao().startsWith("s"))
				&& quantidadeCachorros > 2) {
			desconto = total * 0.1;
		} else if (quantidadeCachorros > 2) {
			desconto = total * 0.12;
		}

		return desconto;
	}

	public double getDesconto() {
		return desconto;
	}
}