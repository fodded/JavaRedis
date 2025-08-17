package me.fodded.command.executor;

import me.fodded.command.content.CommandContent;
import me.fodded.command.response.CommandResponse;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface CommandExecutor {

    CommandExecutor EMPTY = (content, response) -> {};

    void execute(@NotNull CommandContent content, @NotNull CommandResponse response);
}
