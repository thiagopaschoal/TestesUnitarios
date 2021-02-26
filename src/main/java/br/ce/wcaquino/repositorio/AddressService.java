package br.ce.wcaquino.repositorio;

import br.ce.wcaquino.entidades.Address;
import br.ce.wcaquino.entidades.Usuario;

public interface AddressService {

    public Address findAddressByCep(Usuario usuario);

}
