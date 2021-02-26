package br.ce.wcaquino.entidades;

import java.util.Objects;

public class Address {

    private String cep;
    private String logradouro;
    private String bairro;
    private String localidade;
    private String uf;

    public Address() {
    }

    public Address(String cep,
                   String logradouro,
                   String bairro,
                   String localidade,
                   String uf) {
        this.cep = cep;
        this.logradouro = logradouro;
        this.localidade = localidade;
        this.uf = uf;
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(cep, address.cep) &&
                Objects.equals(logradouro, address.logradouro) &&
                Objects.equals(bairro, address.bairro) &&
                Objects.equals(localidade, address.localidade) &&
                Objects.equals(uf, address.uf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cep, logradouro, bairro, localidade, uf);
    }
}
