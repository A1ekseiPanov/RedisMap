package ru.panov;

import redis.clients.jedis.Jedis;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import static ru.panov.util.RedisUtil.jedis;

public class RedisMap implements Map<String, String> {
    private final String hashTableName;

    public RedisMap(String hashTableName) {
        this.hashTableName = hashTableName;
    }

    @Override
    public int size() {
        try (Jedis jedis = jedis()) {
            return (int) jedis.hlen(hashTableName);
        }
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        try (Jedis jedis = jedis()) {
            return jedis.hexists(hashTableName, key.toString());
        }
    }

    @Override
    public boolean containsValue(Object value) {
        try (Jedis jedis = jedis()) {
            return jedis.hvals(hashTableName).contains(value);
        }
    }

    @Override
    public String get(Object key) {
        try (Jedis jedis = jedis()) {
            return jedis.hget(hashTableName, key.toString());
        }
    }

    @Override
    public String put(String key, String value) {
        try (Jedis jedis = jedis()) {
            jedis.hset(hashTableName, key, value);
            return value;
        }
    }

    @Override
    public String remove(Object key) {
        try (Jedis jedis = jedis()) {
            String k = get(key);
            jedis.hdel(hashTableName, key.toString());
            return k;
        }
    }

    @Override
    public void putAll(Map<? extends String, ? extends String> m) {
        m.forEach(this::put);
    }

    @Override
    public void clear() {
        try (Jedis jedis = jedis()) {
            jedis.del(hashTableName);
        }
    }

    @Override
    public Set<String> keySet() {
        try (Jedis jedis = jedis()) {
            return jedis.hkeys(hashTableName);
        }
    }

    @Override
    public Collection<String> values() {
        try (Jedis jedis = jedis()) {
            return jedis.hvals(hashTableName);
        }
    }

    @Override
    public Set<Entry<String, String>> entrySet() {
        try (Jedis jedis = jedis()) {
            Map<String, String> map = jedis.hgetAll(hashTableName);
            return map.entrySet();
        }
    }
}