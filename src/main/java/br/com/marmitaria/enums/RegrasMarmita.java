package br.com.marmitaria.enums;

public enum RegrasMarmita {

    MARMITA_PEQUENA(new FaixaLimites(1, 1, 1, 2, 0, 2)),
    MARMITA_GRANDE(new FaixaLimites(1, 2, 1, 3, 0, 4));

    public final FaixaLimites limites;

    RegrasMarmita(FaixaLimites limites) {
        this.limites = limites;
    }

    public static RegrasMarmita of(TipoProduto tipo) {
        return switch (tipo) {
            case MARMITA_PEQUENA -> MARMITA_PEQUENA;
            case MARMITA_GRANDE -> MARMITA_GRANDE;
            default -> throw new IllegalArgumentException("O tipo não é uma marmita");
        };
    }

    public record FaixaLimites(
            int minProteina, int maxProteina,
            int minCarboidrato, int maxCarboidrato,
            int minComplemento, int maxComplemento
    ) {}
}
