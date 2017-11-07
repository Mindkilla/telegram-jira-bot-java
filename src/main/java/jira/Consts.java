package jira;

import jersey.repackaged.com.google.common.collect.ImmutableList;
import jmbot.Emoji;

import java.util.List;

/**
 * @author Andrey Smirnov
 */
public class Consts
{

    //Jira Api properties
    public static final String JIRA_URL = "";
    public static final String JIRA_USER = "";
    public static final String JIRA_PASS = "";

    //Telegram Bot Credentials
    public static final String BOT_USERNAME = "";
    public static final String BOT_TOKEN = "";

    //Names
    public static final String LEVAS = " LevAS ";
    public static final String SMIANA = " SmiAnA ";
    public static final String ESIES = " EsiES ";
    public static final String KUAAE = " KuaAE ";
    public static final String ZHEEV = " ZheEV ";
    public static final List<String> Users = ImmutableList.of("levas", "smiana", "esies", "kuaae", "zheev");

    public static final String NAME_ALEX = "Саня";
    public static final String NAME_ANDREY = "Андрей";
    public static final String NAME_JOHN = "Женя";
    public static final String NAME_AIDAR = "Айдар";
    public static final String NAME_ZH = "Жека";

    //Сообщения
    public static final String START_MSG = "Этот бот предназначен для сервиса Jira Монитор [bss.kz]" + "\r\n"
            + "Чтобы получить доступ к возможностям бота отправьте свой логин от учетной записи!";
    public static final String REG_OK_MSG = "Успешно! Теперь Вам доступны все возможности бота JiraMonitor Bot!";
    public static final String UNKNOWN_MSG = "Что вы от меня хотите" + Emoji.DISAPPOINTED_BUT_RELIEVED_FACE;
    public static final String ERROR_MSG = "У Вас нет доступа к данному функционалу!" +
            "Чтобы получить доступ к возможностям бота отправьте свой логин от учетной записи!";

    //Commands
    public static final String ACTIVE_CMD = "Кол-во АКТИВНЫХ обращений";
    public static final String CLOSED_CMD = "Кол-во ЗАКРЫТЫХ обращений";
    public static final String TODAY_CMD = "Обращений за сегодня";
    public static final String VACATION_CMD = "Отпуска";
    public static final String INCATIVITY_CMD = "Простои";
    public static final String LIDER_CMD = "Лидер за прошлый месяц";

    //Типы обращений
    public static final String ISSUE_NAME = "Обращений : ";
    public static final String DEFECT_NAME = "Дефектов";
    public static final String CONSULT_NAME = "Консультаций";
    public static final String WHISH_NAME = "Пожеланий";
    public static final String DEFECT = "(10300)";
    public static final String CONSULT = "(10304)";
    public static final String WHISH = "(10405)";
    public static final String NEW = "Новых";
    public static final String CLOSED = "Закрыто";
    public static final String OPENED = "Открытые";

    //JQL-запросы
    //Открытых\Закрытых обращений за сегодня
    public static final String ISSUE_NEW_TODAY = "project = SUPP AND cf[14303]=7 AND createdDate >= -10h";
    public static final String ISSUE_CLOSED_TODAY = "project = SUPP AND cf[14303]=7 AND resolutiondate >= -10h AND Status in (6, 10504)";

    //Кол-во АКТИВНЫХ обращений на данный момент для каждого
    public static final String ISSUE_ACTIVE_NOW = "project=SUPP AND cf[14303]=7 AND status not in(6, 10504, 10503) AND assignee =";

    //Кол-во ЗАКРЫТЫХ обращений (10405) за текущий месяц для каждого
    public static final String ISSUE_CLOSED_MONTH = "project=SUPP AND resolution=10407 and updatedDate>=startOfMonth(1d) AND updateddate<= endOfMonth() AND type not in (10405) AND assignee =";

    //Кол-во ЗАКРЫТЫХ обращений (10405) за прошлый месяц для каждого
    public static final String ISSUE_CLOSED_PRMONTH = "project=SUPP AND resolution=10407 and updatedDate>=" + DateUtils.dataBegin() + " AND updateddate<=" + DateUtils.dataEnd() + " AND type not in (10405) AND assignee =";

    //Все Открытые\Закрытые обращения за месяц
    public static final String ISSUE_OPEN_CURMONTH = "project = SUPP AND cf[14303] = 7 AND type not in (10405) AND resolution = Unresolved";
    public static final String ISSUE_CLOSED_CURMONTH = "project = SUPP AND cf[14303] = 7 AND type not in (10405) AND resolution = 10407 AND updatedDate >= startOfMonth() AND updateddate <= endOfMonth()";

    //Кол-во ЗАКРЫТЫХ дефектов (10300), консультаций(10304), пожеланий (10304) за месяц
    public static final String DEFECT_CLOSED_MONTH = "project = SUPP AND cf[14303] = 7 AND resolution = 10407 AND updatedDate >= startOfMonth() AND updateddate <= endOfMonth() AND type in ";

    //Кол-во ОТКРЫТЫХ дефектов(10300), консультаций(10304)
    public static final String DEFECT_OPENED_NOW = "project = SUPP AND cf[14303] = 7 AND type not in (10405) AND resolution = Unresolved AND type in ";

    public static final String VACATION_CURRENT_YEAR = "project = SUPP_2_VACATION_DEIS AND resolutiondate <=endOfYear()  AND resolutiondate >=startOfYear() AND assignee =";
    public static final String INACTIVITY_CURRENT_YEAR = "project = SUPP_2_INACTIVITY_DEIS AND resolutiondate <=endOfMonth()  AND resolutiondate >=startOfMonth() AND assignee =";

    private Consts()
    {
    }
}
