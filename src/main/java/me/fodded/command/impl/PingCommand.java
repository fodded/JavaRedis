package me.fodded.command.impl;

import me.fodded.command.RedisCommand;
import me.fodded.command.argument.CommandArgument;
import me.fodded.command.argument.CommandArgumentType;
import me.fodded.command.content.RedisCommandContent;

public class PingCommand implements RedisCommand {

    @Override
    public RedisCommandContent command() {
        return RedisCommandContent.builder()
                .name("PING")
                .addRequiredArgument(new CommandArgument("text", CommandArgumentType.TEXT))
                .execute((content, response) -> {
                    String text = content.getArgument("text", String.class);
                    System.out.println(text);
                })
                .addSubCommand(pingMyselfSubCommand())
                .build();
    }

    private RedisCommandContent pingMyselfSubCommand() {
        return RedisCommandContent.builder()
                .name("MYSELF")
                .execute((content, response) -> {
                    String text = content.getArgument("text", String.class);
                    System.out.println(text);
                })
                .build();
    }
}
