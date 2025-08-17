package me.fodded.command.impl;

import me.fodded.command.RedisCommand;
import me.fodded.command.argument.CommandArgument;
import me.fodded.command.argument.CommandArgumentType;
import me.fodded.command.content.RedisCommandContent;
import me.fodded.redis.storage.HashStorage;

public class SetCommand implements RedisCommand {

    @Override
    public RedisCommandContent command() {
        return RedisCommandContent.builder()
                .name("SET")
                .addRequiredArgument(new CommandArgument("hash-name", CommandArgumentType.STRING))
                .addRequiredArgument(new CommandArgument("key-name", CommandArgumentType.STRING))
                .addRequiredArgument(new CommandArgument("value", CommandArgumentType.TEXT))
                .execute((content, response) -> {
                    var hash = content.getArgument("hash-name", String.class);
                    var key = content.getArgument("key-name", String.class);
                    var value = content.getArgument("value", String.class);
                    HashStorage.getInstance().set(hash, key, value);
                })
                .build();
    }
}
