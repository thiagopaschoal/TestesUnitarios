package br.ce.wcaquino.filters;

import br.ce.wcaquino.entidades.Invoice;
import br.ce.wcaquino.repositorio.InvoiceRepository;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

@RunWith(Parameterized.class)
public class InvoiceFilterTest {

    @InjectMocks
    private InvoiceFilter filter;

    @Mock
    private InvoiceRepository repository;

    @Parameterized.Parameter
    public List<Invoice> invoices;

    @Parameterized.Parameter(value = 1)
    public int size;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Parameterized.Parameters(name = "Teste: Size {1}")
    public static Collection<Object[]> getParameters() {

        Calendar calendar1993 = Calendar.getInstance();
        calendar1993.set(Calendar.YEAR, 1993);

        Calendar calendarInvalid = Calendar.getInstance();
        calendarInvalid.set(Calendar.YEAR, 2021);

        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(Calendar.YEAR, 1995);

        return Arrays.asList(new Object[][]{
                {
                        Arrays.asList(
                                new Invoice(Calendar.getInstance(), "MacBook", 2000),
                                new Invoice(Calendar.getInstance(), "Surface", 5000),
                                new Invoice(Calendar.getInstance(), "Notebook", 7000)
                        ), 2
                },
                {
                        Arrays.asList(
                                new Invoice(Calendar.getInstance(), "MICROSOFT", 200)
                        ), 1
                },
                {
                        Arrays.asList(
                                new Invoice(Calendar.getInstance(), "MacBook", 200),
                                new Invoice(Calendar.getInstance(), "Windows", 1500),
                                new Invoice(Calendar.getInstance(), "Linux", 300)
                        ), 0
                },
                {
                        Arrays.asList(
                                new Invoice(Calendar.getInstance(), "MICROSOFT", 1500),
                                new Invoice(Calendar.getInstance(), "MICROSOFT", 1000),
                                new Invoice(Calendar.getInstance(), "Apple", 30000),
                                new Invoice(Calendar.getInstance(), "Geladeira", 1990),
                                new Invoice(calendar1993, "Linux", 300)
                        ), 4
                },
                {
                        Arrays.asList(
                                new Invoice(calendar1993, "MacBook", 200),
                                new Invoice(calendarInvalid, "Windows", 1500),
                                new Invoice(calendar1, "Linux", 300)
                        ), 2
                }
        });
    }

    @Test
    public void shouldTestAddInvoiceByFilter() {
        Mockito.when(repository.all()).thenReturn(invoices);
        List<Invoice> filter = new InvoiceFilter(repository).filter();
        Assert.assertThat(filter.size(), CoreMatchers.is(size));
    }

}
