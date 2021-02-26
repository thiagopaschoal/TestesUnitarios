package br.ce.wcaquino.servicos;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class RomanNumberServiceTest {

    private RomanNumberService romanNumberService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        this.romanNumberService = new RomanNumberService();
    }

    @Test
    public void shouldTestWhenValueIsAMillion() {
        final String number = this.romanNumberService.getNumberInRoman(1200000);
        Assert.assertThat(number, CoreMatchers.is("MMXV"));
    }

    @Test
    public void shouldTestWhenValueIsAThousand() {
        final String number = this.romanNumberService.getNumberInRoman(2021);
        Assert.assertThat(number, CoreMatchers.is("MMXXI"));
    }

    @Test
    public void shouldTestWhenValueIsAHundreds() {
        final String number = this.romanNumberService.getNumberInRoman(231);
        Assert.assertThat(number, CoreMatchers.is("CCXXXI"));
    }

    @Test
    public void shouldTestWhenValueIsADozen() {
        final String number = this.romanNumberService.getNumberInRoman(27);
        Assert.assertThat(number, CoreMatchers.is("XXVII"));
    }

    @Test
    public void shouldTestWhenValueIsAUnit() {
        final String number = this.romanNumberService.getNumberInRoman(8);
        Assert.assertThat(number, CoreMatchers.is("VIII"));
    }

    @Test
    public void shouldTestWhenNumberIsNegative() {
        final String number = this.romanNumberService.getNumberInRoman(-10);
        Assert.assertThat(number, CoreMatchers.is(""));
    }

}
