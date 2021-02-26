package br.ce.wcaquino.repositorio;

import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;

import java.util.List;

public interface LocacaoDAO {
    void salvar(Locacao locacao);
    List<Locacao> obterTodasAsLocacoes(Usuario usuario);
}
