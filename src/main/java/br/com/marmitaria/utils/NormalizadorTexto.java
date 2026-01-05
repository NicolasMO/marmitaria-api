package br.com.marmitaria.utils;

public final class NormalizadorTexto {

    private NormalizadorTexto() {}

    public static String normalizarNome(String nome) {
        return nome
                .trim()
                .replaceAll("\\s+", " ")
                .toLowerCase();
    }
}