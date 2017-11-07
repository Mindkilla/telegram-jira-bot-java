package jira;

import net.rcarz.jiraclient.BasicCredentials;
import net.rcarz.jiraclient.Issue;
import net.rcarz.jiraclient.JiraClient;
import net.rcarz.jiraclient.JiraException;
import org.apache.log4j.Logger;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Andrey Smirnov
 */
public class JiraApiUtils
{
    private static final Logger log = Logger.getLogger(JiraApiUtils.class);

    private JiraApiUtils()
    {
    }
    private static JiraClient connect;

    //Кол-во АКТИВНЫХ обращений на данный момент для каждого
    public static String getCurrentActive(){
        HashMap<String, String> userJql = new HashMap<>();
        userJql.put(Consts.NAME_ALEX,Consts.ISSUE_ACTIVE_NOW +Consts.LEVAS);
        userJql.put(Consts.NAME_JOHN,Consts.ISSUE_ACTIVE_NOW +Consts.ESIES);
        userJql.put(Consts.NAME_AIDAR,Consts.ISSUE_ACTIVE_NOW +Consts.KUAAE);
        userJql.put(Consts.NAME_ZH,Consts.ISSUE_ACTIVE_NOW +Consts.ZHEEV);
        return issueCount(userJql);
    }

    //Кол-во ЗАКРЫТЫХ обращений за текущий месяц для каждого
    public static String getCurrentClosed(){
        HashMap<String, String> userJql = new HashMap<>();
        userJql.put(Consts.NAME_ALEX, Consts.ISSUE_CLOSED_MONTH + Consts.Users.get(0));
        userJql.put(Consts.NAME_JOHN, Consts.ISSUE_CLOSED_MONTH + Consts.ESIES);
        userJql.put(Consts.NAME_AIDAR, Consts.ISSUE_CLOSED_MONTH + Consts.KUAAE);
        userJql.put(Consts.NAME_ZH, Consts.ISSUE_CLOSED_MONTH + Consts.ZHEEV);
        return issueCount(userJql);
    }

    //Обращений за сегодня
    public static String todayIssues(){
        HashMap<String, String> userJql = new HashMap<>();
        userJql.put(Consts.NEW, Consts.ISSUE_NEW_TODAY);
        userJql.put(Consts.CLOSED, Consts.ISSUE_CLOSED_TODAY);
        return issueCount(userJql);
    }

    //Отпуска
    public static String getVacation(){
        HashMap<String, String> userJql = new HashMap<>();
        userJql.put(Consts.NAME_ALEX, Consts.VACATION_CURRENT_YEAR + Consts.LEVAS);
        userJql.put(Consts.NAME_JOHN, Consts.VACATION_CURRENT_YEAR + Consts.ESIES);
        userJql.put(Consts.NAME_AIDAR, Consts.VACATION_CURRENT_YEAR + Consts.KUAAE);
        userJql.put(Consts.NAME_ZH, Consts.VACATION_CURRENT_YEAR + Consts.ZHEEV);
        return projectTimeCount(userJql, "day");
    }

    //Простои
    public static String getInactivity(){
        HashMap<String, String> userJql = new HashMap<>();
        userJql.put(Consts.NAME_ALEX, Consts.INACTIVITY_CURRENT_YEAR + Consts.LEVAS);
        userJql.put(Consts.NAME_JOHN, Consts.INACTIVITY_CURRENT_YEAR + Consts.ESIES);
        userJql.put(Consts.NAME_AIDAR, Consts.INACTIVITY_CURRENT_YEAR + Consts.KUAAE);
        userJql.put(Consts.NAME_ZH, Consts.INACTIVITY_CURRENT_YEAR + Consts.ZHEEV);
        return projectTimeCount(userJql, "hour");
    }

    //Лидер за прошлый месяц
    public static String getLider(){
        HashMap<String, String> userJql = new HashMap<>();
        userJql.put(Consts.NAME_ALEX, Consts.ISSUE_CLOSED_PRMONTH + Consts.LEVAS);
        userJql.put(Consts.NAME_ZH, Consts.ISSUE_CLOSED_PRMONTH + Consts.ZHEEV);
        userJql.put(Consts.NAME_JOHN, Consts.ISSUE_CLOSED_PRMONTH + Consts.ESIES);
        userJql.put(Consts.NAME_AIDAR, Consts.ISSUE_CLOSED_PRMONTH + Consts.KUAAE);
        return maxCountMonthRest(userJql);
    }

    //Connect to jira rest api
    public static void jiraConnect()
    {
        BasicCredentials creds = new BasicCredentials(Consts.JIRA_USER, Consts.JIRA_PASS);
        connect = new JiraClient(Consts.JIRA_URL, creds);
    }

    //Count of issue
    private static String issueCountStr(Map<String, String> userJql)
    {
        StringBuilder total = new StringBuilder();
        for ( Map.Entry<String, String> entry : userJql.entrySet() )
        {
            try
            {
                Issue.SearchResult result = connect.searchIssues(entry.getValue(), "project");
                total.append(entry.getKey());
                total.append(" : ");
                total.append(result.total);
                total.append("\r\n");
            }
            catch ( JiraException ex )
            {
                if ( ex.getCause() != null )
                {
                    log.error(ex + ex.getCause().getMessage());
                }
            }
        }
        return total.toString();
    }

    //Count of issue to Map
    private static HashMap<String, Integer> issueCountMap(Map<String, String> userJql)
    {
        HashMap<String, Integer> total = new HashMap<>();
        for ( Map.Entry<String, String> entry : userJql.entrySet() )
        {
            try
            {
                Issue.SearchResult result = connect.searchIssues(entry.getValue(), "project");
                total.put(entry.getKey(), result.total);
            }
            catch ( JiraException ex )
            {
                if ( ex.getCause() != null )
                {
                    log.error(ex + ex.getCause().getMessage());
                }
            }
        }
        return total;
    }

    //в секундах , можно форматировать результат при выводе куда-либо
    private static String projectTimeCount(Map<String, String> userJql, String metric)
    {
        StringBuilder time = new StringBuilder();
        for ( Map.Entry<String, String> entry : userJql.entrySet() )
        {
            try
            {
                Issue.SearchResult result = connect.searchIssues(entry.getValue());
                if ( result.issues.isEmpty() )
                {
                    time.append(entry.getKey());
                    time.append(" : ");
                    time.append("0");
                    time.append("\r\n");
                    continue;
                }
                int timeInSec = 0;
                for ( int i = 0; i < result.issues.size(); i++ )
                {
                    timeInSec = timeInSec + result.issues.get(i).getTimeSpent();
                }
                time.append(entry.getKey());
                time.append(" : ");
                if (metric.equals("day")){
                   double days = ((timeInSec / 3600) / 8);
                   time.append(days);
                }
                time.append(timeInSec / 3600);
                time.append("\r\n");
            }
            catch ( JiraException ex )
            {
                if ( ex.getCause() != null )
                {
                    log.error(ex + ex.getCause().getMessage());
                }
            }
        }
        return time.toString();
    }

    private static String issueCount(Map<String, String> userJql)
    {
        return issueCountStr(userJql);
    }

    //Лидер по кол-ву решенных обращений за прошлый месяц
    private static String maxCountMonthRest(Map<String, String> userJql)
    {
        HashMap<String, Integer> arrayOfCounts = issueCountMap(userJql);
        int maxValueInMap = Collections.max(arrayOfCounts.values());
        String maxName = null;

        for ( Map.Entry<String, Integer> entry : arrayOfCounts.entrySet() )
        {
            if ( entry.getValue() == maxValueInMap )
            {
                maxName = entry.getKey();
            }
        }

        StringBuilder maxOfCounts = new StringBuilder();
        maxOfCounts.append(maxName).append(", ").append(maxValueInMap);

        log.info("Максимальное кол-во за прошлый месяц - " + maxOfCounts);

        return maxOfCounts.toString();
    }
}
