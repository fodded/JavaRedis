package me.fodded.command.content;

import lombok.RequiredArgsConstructor;
import me.fodded.command.argument.parser.ParsedCommandArgument;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
public class CommandContent {

    private final Map<String, ParsedCommandArgument<?>> arguments;

    public @NotNull <T> T getArgument(String argumentName, @NotNull Class<T> argumentType) {
        var parsedCommandArgument = arguments.get(argumentName);
        T argument = argumentType.cast(parsedCommandArgument.value());
        Objects.requireNonNull(argument, "argument can't be null");
        return argument;
    }
}
