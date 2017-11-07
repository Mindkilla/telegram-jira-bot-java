package jmbot;

import jira.JiraApiUtils;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

/**
 * @author Andrey Smirnov
 */
public class Main
{
    public static void main(String[] args)
    {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        JiraApiUtils.jiraConnect();
        try
        {
            telegramBotsApi.registerBot(new Bot());
        }catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
