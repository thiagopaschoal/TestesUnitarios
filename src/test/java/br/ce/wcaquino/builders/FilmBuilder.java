package br.ce.wcaquino.builders;

import br.ce.wcaquino.entidades.Filme;

public class FilmBuilder {

    private Filme filme;

    private FilmBuilder() {};

    public static FilmBuilder oneFilm() {
        FilmBuilder builder = new FilmBuilder();
        builder.filme = new Filme();
        return builder;
    }

    public FilmBuilder withName(String name) {
        this.filme.setNome(name);
        return this;
    }

    public FilmBuilder withInventory(Integer quantity) {
        this.filme.setEstoque(quantity);
        return this;
    }

    public FilmBuilder withPrice(Double price) {
        this.filme.setPrecoLocacao(price);
        return this;
    }

    public Filme build() {
        return this.filme;
    }
}
