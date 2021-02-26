package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.builders.AddressBuilder.*;

import br.ce.wcaquino.ParallelRunner;
import br.ce.wcaquino.builders.LocacaoBuilder;
import br.ce.wcaquino.builders.UserBuilder;
import br.ce.wcaquino.entidades.Address;
import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeInvalidoException;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.PrecoInvalidoException;
import br.ce.wcaquino.exceptions.UsuarioInvalidoException;
import br.ce.wcaquino.matchers.CustomMatchers;
import br.ce.wcaquino.repositorio.AddressService;
import br.ce.wcaquino.repositorio.EmailService;
import br.ce.wcaquino.repositorio.LocacaoDAO;
import br.ce.wcaquino.repositorio.SPCService;
import br.ce.wcaquino.utils.DataUtils;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.*;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static br.ce.wcaquino.builders.FilmBuilder.oneFilm;
import static br.ce.wcaquino.builders.UserBuilder.oneUser;
import static br.ce.wcaquino.matchers.CustomMatchers.isToday;
import static br.ce.wcaquino.matchers.CustomMatchers.isTodayWithDaysDifference;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

@RunWith(ParallelRunner.class)
public class LocacaoServiceTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private Usuario user;

    private List<Filme> filmes;

    @InjectMocks
    private LocacaoService locacaoService;

    @Mock
    private LocacaoDAO locacaoDAO;

    @Mock
    private AddressService addressService;

    @Mock
    private EmailService emailService;

    @Mock
    private SPCService spcService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.filmes =  List.of(
                oneFilm().withName("Avengers").withInventory(5).withPrice(29.90).build(),
                oneFilm().withName("Justice League").withInventory(2).withPrice(19.90).build()
        );
        this.user = oneUser().withName("thiago.paschoal").build();
    }

    @Test
    public void shouldTestIfReturnDateIsCorrectAfterRent() {
        Assume.assumeFalse(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));
        final  Locacao locacao = locacaoService.alugarFilme(user, filmes);
        assertThat(locacao.getDataRetorno(), isTodayWithDaysDifference(1));
    }

    @Test
    public void shouldTestIfRentDateIsToday() {
        final  Locacao locacao = locacaoService.alugarFilme(user, filmes);
        assertThat(locacao.getDataLocacao(), isToday());
    }

    @Test
    public void shouldTestIfUserIsCorrectAfterRent() {
        final  Locacao locacao = locacaoService.alugarFilme(user, filmes);
        assertThat(locacao.getUsuario(), isA(Usuario.class));
        assertThat(locacao.getUsuario().getNome(), is("thiago.paschoal"));
    }

    @Test(expected = FilmeSemEstoqueException.class)
    public void shouldTestThrowExceptionWhenInventoryFilmIsEmpty() {
        final List<Filme> filmesComUmFilmeSemEstoque = List.of(
                oneFilm().withName("Avengers").withInventory(0).withPrice(29.90).build(),
                oneFilm().withName("Justice League").withInventory(2).withPrice(19.90).build()
        );
        locacaoService.alugarFilme(user, filmesComUmFilmeSemEstoque);
    }

    @Test
    public void shouldTestThrowExceptionWhenFilmIsNull() {
        try {
            locacaoService.alugarFilme(user, null);
        } catch (FilmeInvalidoException e) {
            assertThat(e.getMessage(), is("vc deve possuir pelo menos um filme para alugar"));
        }
    }

    @Test
    public void shouldTestThrowExceptionWhenFilmsIsEmpty() {
        expectedException.expect(FilmeInvalidoException.class);
        expectedException.expectMessage("vc deve possuir pelo menos um filme para alugar");
        locacaoService.alugarFilme(user, List.of());
    }

    @Test(expected = UsuarioInvalidoException.class)
    public void shouldTestThrowExceptionWhenUserIsNull() {
        locacaoService.alugarFilme(null, filmes);
    }

    @Test
    public void shouldTestThrowExceptionWhenPriceEqualZero()  {
        final List<Filme> filmesComUmFilmeSemPreco = List.of(
                oneFilm().withName("Avengers").withInventory(5).withPrice(29.90).build(),
                oneFilm().withName("Justice League").withInventory(2).withPrice(0D).build()
        );
        try {
            locacaoService.alugarFilme(user, filmesComUmFilmeSemPreco);
        } catch (PrecoInvalidoException e) {
            assertThat(e.getMessage(), is("preco nao pode ser menor ou igual a zero."));
        }
    }

    @Test
    public void shouldTestThrowExceptionWhenPriceIsLessThanZero()  {
        final List<Filme> filmesComUmFilmeComPrecoMenorQueZero = List.of(
                oneFilm().withName("Avengers").withInventory(5).withPrice(29.90).build(),
                oneFilm().withName("Justice League").withInventory(2).withPrice(-19.90).build()
        );
        try {
            locacaoService.alugarFilme(user, filmesComUmFilmeComPrecoMenorQueZero);
        } catch (PrecoInvalidoException e) {
            assertThat(e.getMessage(), is("preco nao pode ser menor ou igual a zero."));
        }
    }

    @Test
    public void shouldTestWhenDateIsSaturdayThenReturnDateIsAddedTwoDays() {
        Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));
        final Locacao locacao = locacaoService.alugarFilme(user, filmes);
        assertThat(locacao.getDataRetorno(), isTodayWithDaysDifference(2));
    }

    @Test
    public void shouldTestWhenDateIsSundayThenReturnDateIsAddedTwoDays() {
        Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(), Calendar.SUNDAY));
        final Locacao locacao = locacaoService.alugarFilme(user, filmes);
        assertThat(locacao.getDataRetorno(), isTodayWithDaysDifference(1));
    }

    @Test
    public void shouldTestWhenUserSentCep() {
        final Address expected = new Address("02630010",
                "Rua Aperema",
                "Vila Amelia",
                "Sao Paulo",
                "SP");
        final Address address = oneAddress().withCep("02630010").build();
        final Usuario userWithCep = oneUser().withName("thiago.paschoal").withAddress(address).build();

        Mockito.when(this.addressService.findAddressByCep(userWithCep)).thenReturn(expected);

        final Locacao locacao = locacaoService.alugarFilme(userWithCep, filmes);

        Mockito.verify(addressService).findAddressByCep(userWithCep);
        assertThat(locacao.getUsuario().getAddress(), equalTo(expected));
    }

    @Test
    public void shouldTestWhenReturnDateIsExtended() {

        final Locacao agora = LocacaoBuilder
                .umLocacao()
                .comDataRetorno(DataUtils.obterDataComDiferencaDias(5))
                .agora();

        this.locacaoService.alugarFilmeComDataDeRetornoExtendida(agora, 5);

        ArgumentCaptor<Locacao> locacaoArgumentCaptor = ArgumentCaptor.forClass(Locacao.class);
        Mockito.verify(locacaoDAO).salvar(locacaoArgumentCaptor.capture());
        final Locacao locacaoCapture = locacaoArgumentCaptor.getValue();

        assertThat(locacaoCapture.getDataLocacao(), CustomMatchers.isToday());
        assertThat(locacaoCapture.getDataRetorno(), CustomMatchers.isTodayWithDaysDifference(5));
        assertThat(locacaoCapture.getValor(), is(agora.getValor() * 5));
    }

    @Test
    public void shouldTestEmailFromUserWhoReturnedFilmLate() {
        Usuario usuario = UserBuilder.oneUser().build();;
        Usuario usuario2 = UserBuilder.oneUser().withName("Usuario em dia").build();
        Usuario usuario3 = UserBuilder.oneUser().withName("Outro atrasado").build();
        final List<Locacao> locacaos = Arrays.asList(
                LocacaoBuilder
                        .umLocacao()
                        .comUsuario(usuario2)
                        .agora(),
                LocacaoBuilder
                        .umLocacao()
                        .atrasado()
                        .comUsuario(usuario3)
                        .agora(),
                LocacaoBuilder
                        .umLocacao()
                        .atrasado()
                        .comUsuario(usuario)
                        .agora(),
                LocacaoBuilder
                        .umLocacao()
                        .atrasado()
                        .comUsuario(usuario)
                        .agora()
        );

        Mockito.when(locacaoDAO.obterTodasAsLocacoes(user)).thenReturn(locacaos);
        this.locacaoService.enviarEmailParaUsuariosComDevolutivaPendente(user);

        Mockito.verify(emailService, Mockito.atLeastOnce()).envia(Mockito.any(Usuario.class));
        Mockito.verify(emailService, Mockito.never()).envia(usuario2);
    }

    @Test
    public void shouldTestWhenUserHasNotProblemsWithNameInSPC() {
        Mockito.when(spcService.isNegative(user)).thenReturn(false);
        this.locacaoService.alugarFilme(user, filmes);
        Mockito.verify(spcService).isNegative(user);
    }

    @Test
    public void shouldTestThrowExceptionWhenUserHasProblemsWithNameInSPC() {
        expectedException.expect(UsuarioInvalidoException.class);
        expectedException.expectMessage("usuario está com nome sujo na praca.");
        Mockito.when(spcService.isNegative(user)).thenReturn(true);
        this.locacaoService.alugarFilme(user, filmes);
    }

}