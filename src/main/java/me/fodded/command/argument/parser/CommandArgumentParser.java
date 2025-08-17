package me.fodded.command.argument.parser;

import me.fodded.command.argument.CommandArgument;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface CommandArgumentParser {

    /**
     * Parses argument from a string
     *
     * @param requiredArgument Expected Required Command Argument
     * @param content String user input
     * @return Parsed Command Argument from user input
     * @param <T> Command Argument Type
     */
    @Nullable <T> ParsedCommandArgument<T> parse(@NotNull CommandArgument requiredArgument, @NotNull String content);
}