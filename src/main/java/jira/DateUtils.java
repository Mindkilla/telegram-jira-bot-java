package jira;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import java.util.Locale;

/**
 * @author Andrey Smirnov
 */
public class DateUtils
{
    private DateUtils()
    {
    }

    private static int getPrevMonth()
    {
        DateTime date = new DateTime();
        if ( date.getMonthOfYear() - 1 == 0 )
        {
            return 12;
        }
        return date.getMonthOfYear() - 1;
    }

    public static String getMonthName()
    {
        LocalDate aDate = new LocalDate(getYear(), getPrevMonth(), 1);
        return aDate.monthOfYear().getAsText(Locale.getDefault());
    }

    private static int getYear()
    {
        DateTime date = new DateTime();
        if ( getPrevMonth() == 12 )
        {
            return date.getYear() - 1;
        }
        return date.getYear();
    }

    private static int getLastDayOfMonth()
    {
        int lastDay = 0;
        if ( (getPrevMonth() >= 1) && (getPrevMonth() <= 12) )
        {
            LocalDate aDate = new LocalDate(getYear(), getPrevMonth(), 1);
            lastDay = aDate.dayOfMonth().getMaximumValue();
        }
        return lastDay;
    }

    public static String dataEnd()
    {
        return Integer.toString(getYear()) + "-" + Integer.toString(getPrevMonth()) + "-" + Integer.toString(getLastDayOfMonth());
    }

    public static String dataBegin()
    {
        return Integer.toString(getYear()) + "-" + Integer.toString(getPrevMonth()) + "-" + Integer.toString(1);
    }
}
