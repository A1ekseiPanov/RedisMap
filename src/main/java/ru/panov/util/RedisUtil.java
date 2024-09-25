package ru.panov.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public final class RedisUtil {
    private final static String HOST = "localhost";
    private final static int PORT = 6379;

    private RedisUtil() {
    }
    private static  Jedis jedis(){
       try(JedisPool jedisPool = new JedisPool(HOST, PORT);
           Jedis jedis= jedisPool.getResource()){
        return jedis;
    }
       }


}
