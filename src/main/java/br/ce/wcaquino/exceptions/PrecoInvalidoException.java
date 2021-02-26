package br.ce.wcaquino.exceptions;

public class PrecoInvalidoException extends RuntimeException {

    public PrecoInvalidoException() {
        super();
    }

    public PrecoInvalidoException(String msg) {
        super(msg);
    }

    public PrecoInvalidoException(String msg, Throwable err) {
        super(msg, err);
    }

}
