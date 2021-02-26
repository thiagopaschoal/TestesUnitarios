package br.ce.wcaquino.builders;

import br.ce.wcaquino.entidades.Address;

public class AddressBuilder {

    private Address address;

    private AddressBuilder() {
    }

    public static AddressBuilder oneAddress() {
        AddressBuilder builder = new AddressBuilder();
        builder.address = new Address();
        return builder;
    }

    public AddressBuilder withCep(String cep) {
        this.address.setCep(cep);
        return this;
    }

    public AddressBuilder withLogradouro(String logradouro) {
        this.address.setLogradouro(logradouro);
        return this;
    }

    public Address build() {
        return this.address;
    }
}
