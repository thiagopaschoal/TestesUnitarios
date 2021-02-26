package br.ce.wcaquino.exceptions;

public class FilmeSemEstoqueException extends RuntimeException {

    public FilmeSemEstoqueException() {
        super();
    }

    public FilmeSemEstoqueException(String msg) {
        super(msg);
    }

    public FilmeSemEstoqueException(String msg, Throwable err) {
        super(msg, err);
    }

}
