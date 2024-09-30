package ru.panov;

import redis.clients.jedis.Jedis;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import static ru.panov.util.RedisUtil.jedis;

/**
 * Реализация интерфейса {@link java.util.Map}, основанная на Redis.
 * <p>
 * Этот класс использует хеш Redis для хранения пар ключ-значение и предоставляет
 * стандартные операции, такие как put, get, remove и другие.
 * <p>
 * Класс работает с Redis через клиент Jedis и подключается к Redis с помощью
 * утилиты {@link ru.panov.util.RedisUtil#jedis()}.
 */
public class RedisMap implements Map<String, String> {
    private final String hashTableName;

    /**
     * Создает RedisMap, который взаимодействует с указанным хешем Redis.
     *
     * @param hashTableName имя хеша Redis для хранения пар ключ-значение
     */
    public RedisMap(String hashTableName) {
        this.hashTableName = hashTableName;
    }

    /**
     * Возвращает количество пар ключ-значение в хеше Redis.
     *
     * @return количество записей в хеше Redis
     */
    @Override
    public int size() {
        try (Jedis jedis = jedis()) {
            return (int) jedis.hlen(hashTableName);
        }
    }

    /**
     * Проверяет, пустой ли хеш Redis.
     *
     * @return true, если хеш пуст, иначе false
     */
    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Проверяет, содержит ли хеш Redis указанный ключ.
     *
     * @param key ключ для поиска
     * @return true, если ключ присутствует, иначе false
     */
    @Override
    public boolean containsKey(Object key) {
        try (Jedis jedis = jedis()) {
            return jedis.hexists(hashTableName, key.toString());
        }
    }

    /**
     * Проверяет, содержит ли хеш Redis указанное значение.
     *
     * @param value значение для поиска
     * @return true, если значение присутствует, иначе false
     */
    @Override
    public boolean containsValue(Object value) {
        try (Jedis jedis = jedis()) {
            return jedis.hvals(hashTableName).contains(value);
        }
    }

    /**
     * Возвращает значение, связанное с указанным ключом.
     *
     * @param key ключ для поиска значения
     * @return значение, связанное с ключом, или null, если ключ отсутствует
     */
    @Override
    public String get(Object key) {
        try (Jedis jedis = jedis()) {
            return jedis.hget(hashTableName, key.toString());
        }
    }

    /**
     * Добавляет пару ключ-значение в хеш Redis.
     * <p>
     * Если ключ уже существует, старое значение будет заменено.
     *
     * @param key   ключ, который необходимо добавить
     * @param value значение, которое нужно связать с ключом
     * @return предыдущее значение, связанное с ключом, или null, если ключ отсутствовал
     */
    @Override
    public String put(String key, String value) {
        try (Jedis jedis = jedis()) {
            String v = get(key);
            jedis.hset(hashTableName, key, value);
            return v;
        }
    }

    /**
     * Удаляет пару ключ-значение из хеша Redis.
     *
     * @param key ключ, который нужно удалить
     * @return значение, связанное с удалённым ключом, или null, если ключ отсутствовал
     */
    @Override
    public String remove(Object key) {
        try (Jedis jedis = jedis()) {
            String k = get(key);
            jedis.hdel(hashTableName, key.toString());
            return k;
        }
    }

    /**
     * Добавляет все пары ключ-значение из указанной карты в хеш Redis.
     *
     * @param m карта с парами ключ-значение для добавления
     */
    @Override
    public void putAll(Map<? extends String, ? extends String> m) {
        m.forEach(this::put);
    }

    /**
     * Очищает хеш Redis, удаляя все ключи и значения.
     */
    @Override
    public void clear() {
        try (Jedis jedis = jedis()) {
            jedis.del(hashTableName);
        }
    }

    /**
     * Возвращает набор всех ключей, хранящихся в хеше Redis.
     *
     * @return набор ключей
     */
    @Override
    public Set<String> keySet() {
        try (Jedis jedis = jedis()) {
            return jedis.hkeys(hashTableName);
        }
    }

    /**
     * Возвращает коллекцию всех значений, хранящихся в хеше Redis.
     *
     * @return коллекция значений
     */
    @Override
    public Collection<String> values() {
        try (Jedis jedis = jedis()) {
            return jedis.hvals(hashTableName);
        }
    }

    /**
     * Возвращает набор всех пар ключ-значение, хранящихся в хеше Redis.
     *
     * @return набор пар ключ-значение
     */
    @Override
    public Set<Entry<String, String>> entrySet() {
        try (Jedis jedis = jedis()) {
            Map<String, String> map = jedis.hgetAll(hashTableName);
            return map.entrySet();
        }
    }
}