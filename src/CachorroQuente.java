import java.util.List;

class CachorroQuente {
	private String proteina;
	private String queijo;
	private List<String> ingredientesAdicionais;
	private String bebida;
	private double preco;

	public CachorroQuente(String proteina, String queijo, List<String> ingredientesAdicionais, String bebida) {
		this.proteina = proteina;
		this.queijo = queijo;
		this.ingredientesAdicionais = ingredientesAdicionais;
		this.bebida = bebida;
		this.preco = calcularPreco(proteina);
	}

	private double calcularPreco(String proteina) {
		double precoBase = 0.0;
		int quantidadeCachorros = ingredientesAdicionais.size() > 0 ? ingredientesAdicionais.size() : 1;

		if (proteina.equalsIgnoreCase("salsicha")) {
			precoBase = 2.0;
		} else if (proteina.equalsIgnoreCase("linguiça")) {
			precoBase = 3.0;
		} else if (proteina.equalsIgnoreCase("frango")) {
			precoBase = 2.5;
		} else if (proteina.equalsIgnoreCase("bacon")) {
			precoBase = 3.5;
		}

		if ((proteina.equalsIgnoreCase("salsicha") && quantidadeCachorros > 2)
				|| (proteina.equalsIgnoreCase("linguiça") && quantidadeCachorros > 2)
				|| (proteina.equalsIgnoreCase("frango") && quantidadeCachorros > 3)
				|| (proteina.equalsIgnoreCase("bacon") && quantidadeCachorros > 4)) {
			return precoBase * quantidadeCachorros * 0.86;
		}

		return precoBase * quantidadeCachorros;
	}

	public double getPreco() {
		return preco;
	}

	public String getProteina() {
		return proteina;
	}

	public String getQueijo() {
		return queijo;
	}

	public List<String> getIngredientesAdicionais() {
		return ingredientesAdicionais;
	}

	public String getBebida() {
		return bebida;
	}
}