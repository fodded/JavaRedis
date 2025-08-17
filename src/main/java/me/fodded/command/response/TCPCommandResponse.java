package me.fodded.command.response;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.Objects;

@RequiredArgsConstructor
public class TCPCommandResponse implements CommandResponse {

    private static final LoadingCache<String, ByteBuf> cache = Caffeine.newBuilder()
            .maximumSize(1_000_000)
            .expireAfterAccess(Duration.ofMinutes(1))
            .build(key -> Unpooled.unreleasableBuffer(Unpooled.copiedBuffer(key, CharsetUtil.US_ASCII)));

    private final ChannelHandlerContext context;

    @Override
    public void reply(@NotNull String content) {
        context.write(Objects.requireNonNull(cache.get(content)).duplicate());
    }
}
