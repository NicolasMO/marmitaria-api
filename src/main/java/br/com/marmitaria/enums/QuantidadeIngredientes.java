package br.com.marmitaria.enums;

public enum QuantidadeIngredientes {
	MARMITA_PEQUENA(
            1, 1,
            2, 2),
	MARMITA_GRANDE(2, 2, 3, 4);
	
	private final int produtoId;
	private final int maxProteinas;
	private final int maxCarboidratos;
	private final int maxComplementos;
	
	QuantidadeIngredientes(int produtoId, int maxProteinas, int maxCarboidratos, int maxComplementos) {
		this.produtoId = produtoId;
		this.maxProteinas = maxProteinas;
		this.maxCarboidratos = maxCarboidratos;
		this.maxComplementos = maxComplementos;
	}
	
	public int getProdutoId() {
		return produtoId;
	}
	
	public int getMaxPorCategoria(CategoriaIngrediente categoria) {
        switch (categoria) {
            case PROTEINA:
                return this.maxProteinas;
            case CARBOIDRATO:
                return this.maxCarboidratos;
            case COMPLEMENTO:
                return this.maxComplementos;
            default:
                throw new IllegalArgumentException("Categoria de ingrediente não reconhecida");
        }
    }
	
	public static QuantidadeIngredientes fromProdutoId(int produtoId) {
		for (QuantidadeIngredientes quantidade : QuantidadeIngredientes.values()) {
			if (quantidade.getProdutoId() == produtoId) {
				return quantidade;
			}
		}
		throw new IllegalArgumentException("Id de produto inválido.");
	}
}
