package demo;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.linecorp.bot.client.LineMessagingService;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.response.BotApiResponse;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

@SpringBootApplication
@LineMessageHandler // ----- ココを追加
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Autowired
	private LineMessagingService lineMessagingService;

	@EventMapping
	public void handleTextMessageEvent(MessageEvent<TextMessageContent> event) throws Exception {
		System.out.println("event: " + event);
		final BotApiResponse apiResponse = lineMessagingService.replyMessage(new ReplyMessage(event.getReplyToken(),
				Collections.singletonList(new TextMessage(event.getSource().getUserId())))).execute().body();
		System.out.println("Sent messages: " + apiResponse);
	}

	@EventMapping
	public void defaultMessageEvent(Event event) {
		System.out.println("event: " + event);
	}

}
