package me.fodded.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.java.Log;
import me.fodded.command.content.CommandContent;
import me.fodded.command.parser.CommandParser;
import me.fodded.command.parser.RegularCommandParser;
import me.fodded.command.response.TCPCommandResponse;

@Log
public class TCPChannelHandler extends SimpleChannelInboundHandler<String> {

    private final CommandParser commandParser = new RegularCommandParser();

    @Override
    public void channelActive(ChannelHandlerContext context) {
        String message = String.format("%s Channel active", context.channel().remoteAddress());
        log.info(message);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String message) {
        var parsedCommand = commandParser.parse(message);
        var commandRedisContent = parsedCommand.content();
        var commandExecutor = commandRedisContent.executor();

        var commandContent = new CommandContent(parsedCommand.arguments());
        var commandResponse = new TCPCommandResponse(ctx);
        commandExecutor.execute(commandContent, commandResponse);

        log.info(message + " " + ctx.channel().remoteAddress());
        ctx.channel().writeAndFlush("\n");
    }

    @Override
    public void channelInactive(ChannelHandlerContext context) {
        String message = String.format("%s Channel Inactive", context.channel().remoteAddress());
        log.info(message);
    }
}