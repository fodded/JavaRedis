package me.fodded.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import me.fodded.command.content.CommandContent;
import me.fodded.command.parser.CommandParser;
import me.fodded.command.parser.RegularCommandParser;
import me.fodded.command.response.TCPCommandResponse;

import java.util.concurrent.atomic.LongAdder;

public class TCPChannelHandler extends SimpleChannelInboundHandler<String> {

    private static final ByteBuf RESPONSE = Unpooled.unreleasableBuffer(
            Unpooled.copiedBuffer("\n", CharsetUtil.US_ASCII)
    );

    private final LongAdder requests = new LongAdder();
    private final CommandParser commandParser = new RegularCommandParser();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String message) {
        var parsedCommand = commandParser.parse(message);
        var commandRedisContent = parsedCommand.content();
        var commandExecutor = commandRedisContent.executor();

        var commandContent = new CommandContent(parsedCommand.arguments());
        var commandResponse = new TCPCommandResponse(ctx);
        commandExecutor.execute(commandContent, commandResponse);

        requests.increment();
        ctx.write(RESPONSE.duplicate());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }
}