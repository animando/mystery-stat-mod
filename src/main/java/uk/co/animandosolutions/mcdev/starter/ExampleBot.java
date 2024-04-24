package uk.co.animandosolutions.mcdev.starter;

import discord4j.common.util.Snowflake;
import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;

public class ExampleBot {
    public static String TOKEN = "MTIzMjM5NTE2OTU5MzAzMjczNQ.GxPBYc.oDpLSx-4vZ6kwSuCr7SAiBeW_wlyDgf7WPxNXA";

    public static void main(String[] args) {
      DiscordClient client = DiscordClient.create(TOKEN);
      GatewayDiscordClient gateway = client.login().block();
    //   System.out.println("Starting");
  
    //   gateway.on(MessageCreateEvent.class).subscribe(event -> {
    //     System.out.println("event");
    //     Message message = event.getMessage();
    //     if ("!ping".equals(message.getContent())) {
    //       MessageChannel channel = message.getChannel().block();
    //       channel.createMessage("Pong!").block();
    //     }
    //   });
  
    //   gateway.onDisconnect().block();
    var channel = client.getChannelById(Snowflake.of(1232394958804095090L));

    var message = channel.createMessage("Hello from bot!");
  }
}