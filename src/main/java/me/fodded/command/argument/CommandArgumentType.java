package me.fodded.command.argument;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public enum CommandArgumentType {
    TEXT {
        @SuppressWarnings("unchecked")
        public <T> T parse(@NotNull String content) {
            return (T) content;
        }
    },
    STRING {
        @SuppressWarnings("unchecked")
        public <T> T parse(@NotNull String content) {
            return (T) content;
        }
    },
    INT {
        @SuppressWarnings("unchecked")
        public <T> T parse(@NotNull String content) {
            return (T) Integer.valueOf(content);
        }
    };

    public abstract <T> T parse(@NotNull String content);
}
