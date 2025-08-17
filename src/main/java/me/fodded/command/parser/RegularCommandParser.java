package me.fodded.command.parser;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import me.fodded.command.RedisCommandRegistry;
import me.fodded.command.argument.CommandArgument;
import me.fodded.command.argument.CommandArgumentType;
import me.fodded.command.argument.parser.CommandArgumentParser;
import me.fodded.command.argument.parser.ParsedCommandArgument;
import me.fodded.command.argument.parser.RegularCommandArgumentParser;
import me.fodded.command.content.RedisCommandContent;
import me.fodded.command.exception.ParseCommandException;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class RegularCommandParser implements CommandParser {

    private final LoadingCache<String, ParsedCommand> CACHE = Caffeine.newBuilder()
            .expireAfterAccess(Duration.ofMinutes(15))
            .build(this::parseCommand);

    private final RedisCommandRegistry commandRegistry = new RedisCommandRegistry();
    private final CommandArgumentParser argumentParser = new RegularCommandArgumentParser();

    @Override
    public @NotNull ParsedCommand parse(@NotNull String content) {
        return Objects.requireNonNull(CACHE.get(content));
    }

    private @NotNull ParsedCommand parseCommand(@NotNull String content) {
        RedisCommandContent parsedCommand = null;
        int iteratedWordsLength = 0;
        for (String word : content.split(" ")) {
            iteratedWordsLength += word.length();

            String commandName = content.substring(0, iteratedWordsLength);
            RedisCommandContent commandContent = commandRegistry.get(commandName);

            if (commandContent == null) {
                iteratedWordsLength -= word.length();
                break;
            }

            parsedCommand = commandContent;
        }

        if (parsedCommand == null) {
            throw new ParseCommandException(content);
        }

        List<CommandArgument> requiredArguments = parsedCommand.arguments();
        Map<String, ParsedCommandArgument<?>> arguments = new HashMap<>();

        int index = 0;
        String serializedArguments = content.substring(iteratedWordsLength);
        iteratedWordsLength = 0;
        for (String argument : serializedArguments.split(" ")) {
            if (index + 1 > requiredArguments.size()) break;
            iteratedWordsLength += argument.length() + " ".length();

            CommandArgument requiredArgument = requiredArguments.get(index++);
            if (requiredArgument.type() == CommandArgumentType.TEXT) {
                ParsedCommandArgument<?> parsedArgument = argumentParser.parse(requiredArgument, serializedArguments.substring(iteratedWordsLength));
                arguments.put(requiredArgument.name(), parsedArgument);
                break;
            }

            ParsedCommandArgument<?> parsedArgument = argumentParser.parse(requiredArgument, argument);
            if (parsedArgument == null) continue;

            arguments.put(requiredArgument.name(), parsedArgument);
        }

        return new ParsedCommand(parsedCommand, arguments);
    }
}
