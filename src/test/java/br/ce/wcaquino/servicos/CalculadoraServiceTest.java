package br.ce.wcaquino.servicos;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import br.ce.wcaquino.ParallelRunner;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

@RunWith(ParallelRunner.class)
public class CalculadoraServiceTest {

    private CalculadoraService calculadoraService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        this.calculadoraService = new CalculadoraService();
    }

    @Test
    public void shouldSumTwoNumbersCorrectly() {
        assertThat(this.calculadoraService.sum(1,2), is(3D));
    }

    @Test
    public void shouldSubTwoNumbersCorrectly() {
        assertThat(this.calculadoraService.sub(5,3), is(2D));
    }

    @Test
    public void shouldMultiplyTwoNumbersCorrectly() {
        assertThat(this.calculadoraService.multiply(1,2), is(2D));
    }

    @Test
    public void shouldDivideTwoNumbersCorrectly() {
        assertThat(this.calculadoraService.divide(1,2), is(0.5));
    }

    @Test
    public void shouldTestThrowExceptionWhenDivideByZero() {
        expectedException.expectMessage("Infinity");
        assertThat(this.calculadoraService.divide(1,0), is(0.5));
    }
}
