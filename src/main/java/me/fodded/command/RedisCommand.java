package me.fodded.command;

import me.fodded.command.content.RedisCommandContent;

public interface RedisCommand {
    RedisCommandContent command();
}
