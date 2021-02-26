package br.ce.wcaquino.matchers;

import br.ce.wcaquino.utils.DataUtils;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.util.Date;

public class DateTodayMatcher extends TypeSafeMatcher<Date> {

    @Override
    protected boolean matchesSafely(Date item) {
        return DataUtils.isMesmaData(item, new Date());
    }

    @Override
    public void describeTo(Description description) {

    }
}
