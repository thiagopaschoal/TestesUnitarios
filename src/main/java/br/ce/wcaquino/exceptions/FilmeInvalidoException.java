package br.ce.wcaquino.exceptions;

public class FilmeInvalidoException extends RuntimeException {

    public FilmeInvalidoException() {
        super();
    }

    public FilmeInvalidoException(String msg) {
        super(msg);
    }

    public FilmeInvalidoException(String msg, Throwable err) {
        super(msg, err);
    }

}
