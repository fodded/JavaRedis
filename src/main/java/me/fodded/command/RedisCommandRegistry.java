package me.fodded.command;

import me.fodded.command.content.RedisCommandContent;
import me.fodded.command.impl.PingCommand;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class RedisCommandRegistry {

    private final Map<String, RedisCommandContent> commands = new HashMap<>();

    public RedisCommandRegistry() {
        registerDefaults();
    }

    private void registerDefaults() {
        register(new PingCommand());
    }

    public void register(@NotNull RedisCommand command) {
        RedisCommandContent content = command.command();
        String name = content.name();

        commands.put(name, content);
    }

    @Nullable
    public RedisCommandContent get(@NotNull String commandName) {
        return commands.get(commandName);
    }
}
