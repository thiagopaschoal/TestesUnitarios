package br.ce.wcaquino.exceptions;

public class UsuarioInvalidoException extends RuntimeException {

    public UsuarioInvalidoException() {
        super();
    }

    public UsuarioInvalidoException(String msg) {
        super(msg);
    }

    public UsuarioInvalidoException(String msg, Throwable err) {
        super(msg, err);
    }

}
