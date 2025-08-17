package me.fodded.command.argument.parser;

import me.fodded.command.argument.CommandArgument;
import me.fodded.command.argument.CommandArgumentType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RegularCommandArgumentParser implements CommandArgumentParser {

    @Override
    public @Nullable <T> ParsedCommandArgument<T> parse(@NotNull CommandArgument requiredArgument, @NotNull String content) {
        CommandArgumentType type = requiredArgument.type();
        T parsedValue = type.parse(content);
        return new ParsedCommandArgument<>(parsedValue, type);
    }
}
