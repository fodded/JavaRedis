package me.fodded.command.parser;

import me.fodded.command.argument.parser.ParsedCommandArgument;
import me.fodded.command.content.RedisCommandContent;

import java.util.Map;

public record ParsedCommand(RedisCommandContent content, Map<String, ParsedCommandArgument<?>> arguments) {
}
