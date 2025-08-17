package me.fodded.command.exception;

public class ParseCommandException extends RuntimeException {

    public ParseCommandException(String commandContent) {
        super(String.format("Received incorrect command which failed parsing %s", commandContent));
    }
}
