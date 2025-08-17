package me.fodded.redis;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.WriteBufferWaterMark;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.java.Log;
import me.fodded.server.TCPChannelInitializer;

@Log
public class RedisServer {

    private static final int SERVER_PORT = 7000;

    public void launch() {
        EventLoopGroup bossGroup;
        EventLoopGroup workerGroup;

        if (Epoll.isAvailable()) {
            bossGroup = new EpollEventLoopGroup();
            workerGroup = new EpollEventLoopGroup();
        } else {
            bossGroup = new NioEventLoopGroup();
            workerGroup = new NioEventLoopGroup();
        }

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(Epoll.isAvailable() ? EpollServerSocketChannel.class : NioServerSocketChannel.class)
                    .childHandler(new TCPChannelInitializer())
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childOption(ChannelOption.SO_SNDBUF, 1 << 20) // 1 MB
                    .childOption(ChannelOption.SO_RCVBUF, 1 << 20) // 1 MB
                    .childOption(ChannelOption.WRITE_BUFFER_WATER_MARK, new WriteBufferWaterMark(32 * 1024, 64 * 1024))
                    .childOption(ChannelOption.WRITE_SPIN_COUNT, 16);

            ChannelFuture future = bootstrap.bind(SERVER_PORT).sync();
            if (future.isSuccess()) {
                log.info("Server started successfully");
            }

            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            log.info("Stopping server");
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
