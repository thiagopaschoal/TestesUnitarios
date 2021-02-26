package br.ce.wcaquino.builders;

import br.ce.wcaquino.entidades.Address;
import br.ce.wcaquino.entidades.Usuario;

public class UserBuilder {

    private Usuario usuario;

    private UserBuilder() {}

    public static UserBuilder oneUser() {
        UserBuilder builder = new UserBuilder();
        builder.usuario = new Usuario();
        return builder;
    }

    public UserBuilder withName(String name) {
        this.usuario.setNome(name);
        return this;
    }

    public UserBuilder withAddress(Address address) {
        this.usuario.setAddress(address);
        return this;
    }

    public Usuario build() {
        return this.usuario;
    }
}
