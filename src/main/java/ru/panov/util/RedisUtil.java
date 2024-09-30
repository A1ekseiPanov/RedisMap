package ru.panov.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import static ru.panov.util.PropertiesUtil.get;

/**
 * Утилитный класс для работы с Redis с использованием библиотеки Jedis.
 * <p>
 * Класс позволяет получить соединение с Redis, используя параметры конфигурации,
 * загруженные из файла свойств (`application.properties`).
 * <p>
 * Параметры подключения (хост и порт) загружаются при инициализации из файла свойств.
 */
public final class RedisUtil {
    private final static String HOST = get("redis.host");
    private final static int PORT = Integer.parseInt(get("redis.port"));

    private RedisUtil() {
    }

    /**
     * Предоставляет экземпляр {@link Jedis}, представляющий соединение с Redis.
     * <p>
     * Соединение управляется через {@link JedisPool}, который автоматически
     * создает пул соединений к Redis, используя хост и порт из файла свойств.
     * <p>
     *
     * @return экземпляр {@link Jedis} для взаимодействия с Redis
     */
    public static Jedis jedis() {
        try (JedisPool jedisPool = new JedisPool(HOST, PORT)) {
            return jedisPool.getResource();
        }
    }
}