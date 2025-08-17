package me.fodded;

import me.fodded.redis.RedisServer;

public class Main {

    public static void main(String[] args) {
        var launcher = new RedisServer();
        launcher.launch();
    }
}