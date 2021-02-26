package br.ce.wcaquino.matchers;

public class CustomMatchers {

    public static DateTodayMatcher isToday() {
        return new DateTodayMatcher();
    }

    public static DateWithDaysDifferenceMatcher isTodayWithDaysDifference(int daysQuantity) {
        return new DateWithDaysDifferenceMatcher(daysQuantity);
    }

}
