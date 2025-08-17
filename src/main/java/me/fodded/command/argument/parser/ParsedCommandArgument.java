package me.fodded.command.argument.parser;

import me.fodded.command.argument.CommandArgumentType;

public record ParsedCommandArgument<T>(T value, CommandArgumentType type) {
}
