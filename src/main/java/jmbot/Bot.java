package jmbot;

import jira.Consts;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

/**
 * @author Andrey Smirnov
 */
public class Bot extends TelegramLongPollingBot
{

    public void onUpdateReceived(Update update)
    {
        Message message = update.getMessage();
        if ( message != null && message.hasText() )
        {
            SendMessage answer = BotController.doSomeAction(message);
            if ( answer != null )
            {
                try
                {
                    sendMessage(answer);
                }
                catch ( TelegramApiException e )
                {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getBotUsername()
    {
        return Consts.BOT_USERNAME;
    }

    public String getBotToken()
    {
        return Consts.BOT_TOKEN;
    }}
