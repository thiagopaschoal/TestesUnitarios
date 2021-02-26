package br.ce.wcaquino.matchers;

import br.ce.wcaquino.utils.DataUtils;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateWithDaysDifferenceMatcher extends TypeSafeMatcher<Date> {

    private int daysQuantity;

    public DateWithDaysDifferenceMatcher(int daysQuantity) {
        this.daysQuantity = daysQuantity;
    }

    @Override
    protected boolean matchesSafely(Date item) {
        return DataUtils.isMesmaData(item, DataUtils.obterDataComDiferencaDias(this.daysQuantity));
    }

    @Override
    public void describeTo(Description description) {
        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, this.daysQuantity);
        final String displayName = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, new Locale("pt", "BR"));
        description.appendValue(displayName);
    }
}
