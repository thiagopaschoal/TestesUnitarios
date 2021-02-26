package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.builders.UserBuilder.*;
import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.repositorio.AddressService;
import br.ce.wcaquino.repositorio.EmailService;
import br.ce.wcaquino.repositorio.LocacaoDAO;
import br.ce.wcaquino.repositorio.SPCService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static br.ce.wcaquino.builders.FilmBuilder.oneFilm;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class CalculoValorLocacaoTest {

    @Parameterized.Parameter
    public List<Filme> filmes;

    @Parameterized.Parameter(value = 1)
    public Double valorLocacaoComDesconto;

    @Parameterized.Parameters(name = "Teste {index}: {0}")
    public static Collection<Object[]> getParameters() {
        return Arrays.asList(new Object[][] {
            {
                Arrays.asList(
                        oneFilm().withName("Avengers").withInventory(5).withPrice(29.90).build(),
                        oneFilm().withName("Justice League").withInventory(2).withPrice(19.90).build()
                ), 49.8
            },
            {
                Arrays.asList(
                        oneFilm().withName("Avengers").withInventory(5).withPrice(10.0).build(),
                        oneFilm().withName("Justice League").withInventory(2).withPrice(10.0).build(),
                        oneFilm().withName("Teen Titans").withInventory(2).withPrice(10.0).build()
                ), 7.5
            },
            {
                Arrays.asList(
                        oneFilm().withName("Avengers").withInventory(5).withPrice(10.0).build(),
                        oneFilm().withName("Justice League").withInventory(2).withPrice(10.0).build(),
                        oneFilm().withName("Teen Titans").withInventory(5).withPrice(10.0).build(),
                        oneFilm().withName("Jobs").withInventory(2).withPrice(19.90).build()
                ), 12.475
            },
            {
                Arrays.asList(
                        oneFilm().withName("Avengers").withInventory(5).withPrice(10.0).build(),
                        oneFilm().withName("Justice League").withInventory(2).withPrice(10.0).build(),
                        oneFilm().withName("Teen Titans").withInventory(5).withPrice(10.0).build(),
                        oneFilm().withName("Jobs").withInventory(2).withPrice(19.90).build(),
                        oneFilm().withName("Aquaman").withInventory(2).withPrice(19.90).build()
                ), 17.45
            },
            {
                Arrays.asList(
                        oneFilm().withName("Avengers").withInventory(5).withPrice(10.0).build(),
                        oneFilm().withName("Justice League").withInventory(2).withPrice(10.0).build(),
                        oneFilm().withName("Teen Titans").withInventory(5).withPrice(10.0).build(),
                        oneFilm().withName("Jobs").withInventory(2).withPrice(19.90).build(),
                        oneFilm().withName("Aquaman").withInventory(2).withPrice(19.90).build(),
                        oneFilm().withName("Iron man").withInventory(5).withPrice(19.90).build()
                ), 44.8
            }
        });
    }

    private Usuario user;

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
        this.user = oneUser().withName("thiago.paschoal").build();
    }

    @Test
    public void shouldTestWhenDiscountIsAppliedInFilms() {
        final Locacao locacao = locacaoService.alugarFilme(user, filmes);
        assertEquals(valorLocacaoComDesconto, locacao.getValor(), 0.1);
    }
}
