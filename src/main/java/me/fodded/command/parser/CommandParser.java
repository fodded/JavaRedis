package me.fodded.command.parser;

import org.jetbrains.annotations.NotNull;

public interface CommandParser {

    /**
     * Parses the command from user input and returns it as ready to use CommandContent
     *
     * @param content String as user input
     * @return ParsedCommand containing commands content
     */
    @NotNull ParsedCommand parse(@NotNull String content);
}
