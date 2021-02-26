package br.ce.wcaquino.servicos;

import br.ce.wcaquino.entidades.Address;
import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeInvalidoException;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.PrecoInvalidoException;
import br.ce.wcaquino.exceptions.UsuarioInvalidoException;
import br.ce.wcaquino.repositorio.AddressService;
import br.ce.wcaquino.repositorio.EmailService;
import br.ce.wcaquino.repositorio.LocacaoDAO;
import br.ce.wcaquino.repositorio.SPCService;
import br.ce.wcaquino.utils.DataUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static br.ce.wcaquino.utils.DataUtils.adicionarDias;

public class LocacaoService {

    private LocacaoDAO locacaoDAO;

    private AddressService addressService;

    private EmailService emailService;

    private SPCService spcService;

    public Locacao alugarFilme(Usuario usuario, List<Filme> filmes) {

        if (filmes == null || filmes.isEmpty())
            throw new FilmeInvalidoException("vc deve possuir pelo menos um filme para alugar");

        if (usuario == null)
            throw new UsuarioInvalidoException();

        filmes.forEach(filme -> {
			if (filme.getEstoque() == 0)
			    throw new FilmeSemEstoqueException();
		});

        filmes.forEach(filme -> {
			if (filme.getPrecoLocacao() <= 0)
			    throw new PrecoInvalidoException("preco nao pode ser menor ou igual a zero.");
		});

        if (spcService.isNegative(usuario))
            throw  new UsuarioInvalidoException("usuario está com nome sujo na praca.");

        final Address address = this.addressService.findAddressByCep(usuario);
        usuario.setAddress(address);

        Locacao locacao = new Locacao();
        locacao.setFilmes(filmes);
        locacao.setUsuario(usuario);
        locacao.setDataLocacao(new Date());

		double precoTotal = filmes.stream()
				.mapToDouble(filme -> filme.getPrecoLocacao())
				.sum();

		if (filmes.size() >= 3 && filmes.size() < 6)
		    precoTotal = precoTotal * 0.25;
		else if (filmes.size() >= 6 && filmes.size() < 9)
		    precoTotal = precoTotal * 0.50;

		locacao.setValor(precoTotal);

        //Entrega no dia seguinte
        Date dataEntrega = new Date();
        if (DataUtils.verificarDiaSemana(dataEntrega, Calendar.SATURDAY)) {
            dataEntrega = adicionarDias(dataEntrega, 2);
        } else if (DataUtils.verificarDiaSemana(dataEntrega, Calendar.SUNDAY)) {
            dataEntrega = adicionarDias(dataEntrega, 1);
        } else {
            dataEntrega = adicionarDias(dataEntrega, 1);
        }
        locacao.setDataRetorno(dataEntrega);

        //Salvando a locacao...
        locacaoDAO.salvar(locacao);

        return locacao;
    }

    public void enviarEmailParaUsuariosComDevolutivaPendente(Usuario usuario) {
        final List<Locacao> locacaos = this.locacaoDAO.obterTodasAsLocacoes(usuario);
        locacaos.forEach(locacao -> {
            if (locacao.getDataRetorno().before(new Date())) {
                emailService.envia(locacao.getUsuario());
            }
        });
    }

    public void alugarFilmeComDataDeRetornoExtendida(Locacao locacao, int dias) {
        final Locacao novaLocacao = new Locacao();
        novaLocacao.setUsuario(locacao.getUsuario());
        novaLocacao.setFilmes(locacao.getFilmes());
        novaLocacao.setDataLocacao(new Date());
        novaLocacao.setDataRetorno(DataUtils.obterDataComDiferencaDias(dias));
        novaLocacao.setValor(locacao.getValor() * dias);
        this.locacaoDAO.salvar(novaLocacao);
    }

}