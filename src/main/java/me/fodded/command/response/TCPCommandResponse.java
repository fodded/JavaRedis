package me.fodded.command.response;

import io.netty.channel.ChannelHandlerContext;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class TCPCommandResponse implements CommandResponse {

    private final ChannelHandlerContext context;

    @Override
    public void reply(@NotNull String content) {
        context.channel().writeAndFlush(content);
    }
}
