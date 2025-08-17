package me.fodded.command.response;

import org.jetbrains.annotations.NotNull;

public interface CommandResponse {

    /**
     * Writes a response back to the user after completing the command
     *
     * @param content String message
     */
    void reply(@NotNull String content);
}
