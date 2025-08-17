package me.fodded.command.content;

import me.fodded.command.argument.CommandArgument;
import me.fodded.command.executor.CommandExecutor;

import java.util.ArrayList;
import java.util.List;

public record RedisCommandContent(String name, List<CommandArgument> arguments, List<RedisCommandContent> subCommands, CommandExecutor executor) {

    public static RedisCommandContent.Builder builder() {
        return new RedisCommandContent.Builder();
    }

    public static class Builder {

        private String name;
        private CommandExecutor executor = CommandExecutor.EMPTY;

        private final List<CommandArgument> arguments = new ArrayList<>();
        private final List<RedisCommandContent> subCommands = new ArrayList<>();

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder addRequiredArgument(CommandArgument argument) {
            this.arguments.add(argument);
            return this;
        }

        public Builder addSubCommand(RedisCommandContent command) {
            this.subCommands.add(command);
            return this;
        }

        public Builder execute(CommandExecutor executor) {
            this.executor = executor;
            return this;
        }

        public RedisCommandContent build() {
            return new RedisCommandContent(name, arguments, subCommands, executor);
        }
    }
}
