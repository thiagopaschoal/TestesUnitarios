package br.ce.wcaquino.utils;

import static br.ce.wcaquino.matchers.CustomMatchers.*;
import static org.junit.Assert.*;

import br.ce.wcaquino.ParallelRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;
import java.util.Date;

@RunWith(ParallelRunner.class)
public class DataUtilsTest {

    @Test
    public void shouldTestAddTwoDaysIntoCurrentDate() {
        final Date dateAddedTwoDays = DataUtils.obterDataComDiferencaDias(2);
        assertThat(dateAddedTwoDays, isTodayWithDaysDifference(2));
    }

    @Test
    public void shouldTestWhenDateAreNotTheSame() {
        final Date data1 = DataUtils.obterData(2021, 2, 21);
        final Date data2 = DataUtils.obterData(2021, 2, 22);
        Assert.assertFalse(DataUtils.isMesmaData(data1, data2));
    }

    @Test
    public void shouldTestWhenDateAreTheSame() {
        final Date data1 = DataUtils.obterData(2021, 2, 22);
        final Date data2 = DataUtils.obterData(2021, 2, 22);
        Assert.assertTrue(DataUtils.isMesmaData(data1, data2));
    }

    @Test
    public void shouldTestIfTheDayOfWeekIsMonday() {
        assertTrue(DataUtils.verificarDiaSemana(new Date(), Calendar.MONDAY));
    }
}
