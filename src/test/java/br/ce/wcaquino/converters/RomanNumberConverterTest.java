package br.ce.wcaquino.converters;

import br.ce.wcaquino.converters.RomanNumberConverter;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class RomanNumberConverterTest {

    private RomanNumberConverter converter;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        this.converter = new RomanNumberConverter();
    }

    @Test
    public void shouldTestWhenValueIsAMillion() {
        final String number = this.converter.toRoman(1200000);
        Assert.assertThat(number, CoreMatchers.is("MMXV"));
    }

    @Test
    public void shouldTestWhenValueIsAThousand() {
        final String number = this.converter.toRoman(2021);
        Assert.assertThat(number, CoreMatchers.is("MMXXI"));
    }

    @Test
    public void shouldTestWhenValueIsAHundreds() {
        final String number = this.converter.toRoman(231);
        Assert.assertThat(number, CoreMatchers.is("CCXXXI"));
    }

    @Test
    public void shouldTestWhenValueIsADozen() {
        final String number = this.converter.toRoman(27);
        Assert.assertThat(number, CoreMatchers.is("XXVII"));
    }

    @Test
    public void shouldTestWhenValueIsAUnit() {
        final String number = this.converter.toRoman(8);
        Assert.assertThat(number, CoreMatchers.is("VIII"));
    }

    @Test
    public void shouldTestWhenNumberIsNegative() {
        final String number = this.converter.toRoman(-10);
        Assert.assertThat(number, CoreMatchers.is(""));
    }

}
