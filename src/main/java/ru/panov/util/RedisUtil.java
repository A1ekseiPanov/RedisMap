package ru.panov.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import static ru.panov.util.PropertiesUtil.get;

public final class RedisUtil {
    private final static String HOST = get("redis.host");
    private final static int PORT = Integer.parseInt(get("redis.port"));

    private RedisUtil() {
    }

    public static Jedis jedis() {
        try (JedisPool jedisPool = new JedisPool(HOST, PORT)) {
            return jedisPool.getResource();
        }
    }
}