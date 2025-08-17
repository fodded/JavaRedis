package me.fodded.command.impl;

import me.fodded.command.RedisCommand;
import me.fodded.command.content.RedisCommandContent;

public class PingCommand implements RedisCommand {

    @Override
    public RedisCommandContent command() {
        return RedisCommandContent.builder()
                .name("PING")
                .execute((content, response) -> response.reply("PONG"))
                .build();
    }
}
