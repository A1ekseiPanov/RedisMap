package ru.panov;

import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static ru.panov.util.RedisUtil.jedis;


class RedisMapTest {
    private static final String KEY1 = "key1";
    private static final String KEY2 = "key2";
    private static final String VALUE1 = "value1";
    private static final String VALUE2 = "value2";
    private static final String NOT_FOUND_VALUE = "not found";

    private static final String HASH_TABLE_NAME = "test";
    private static Map<String, String> map;

    @BeforeAll
    static void beforeAll() {
        map = new RedisMap(HASH_TABLE_NAME);
    }

    @BeforeEach
    void setUp() {
        jedis().hset(HASH_TABLE_NAME, KEY1, VALUE1);
        jedis().hset(HASH_TABLE_NAME, KEY2, VALUE2);
    }

    @AfterEach
    void tearDown() {
        jedis().del(HASH_TABLE_NAME);
        jedis().close();
    }

    @Test
    void size() {
        int size = map.size();
        assertEquals(2, size);
    }

    @Test
    @DisplayName("isEmpty(), map не пустая")
    void isEmpty_MapIsNotEmpty() {
        assertFalse(map.isEmpty());
    }

    @Test
    void isEmpty() {
        jedis().del(HASH_TABLE_NAME);

        assertTrue(map.isEmpty());

    }

    @Test
    void containsKey() {
        boolean contains1 = map.containsKey(KEY1);
        boolean contains2 = map.containsKey(KEY2);
        assertAll(
                () -> assertTrue(contains1),
                () -> assertTrue(contains2)
        );
    }

    @Test
    @DisplayName("containsKey(), ключь не найден")
    void containsKey_KeyNotFound() {
        boolean contains1 = map.containsKey(NOT_FOUND_VALUE);

        assertFalse(contains1);
    }

    @Test
    void containsValue() {
        boolean contains1 = map.containsValue(VALUE1);
        boolean contains2 = map.containsValue(VALUE2);
        assertAll(
                () -> assertTrue(contains1),
                () -> assertTrue(contains2)
        );
    }

    @Test
    @DisplayName("containsValue(), значени не найдено")
    void containsValue_ValueNotFound() {
        boolean contains1 = map.containsValue(NOT_FOUND_VALUE);

        assertFalse(contains1);
    }

    @Test
    void get() {
        String s = map.get(KEY1);

        assertAll(
                () -> assertNotNull(s),
                () -> assertEquals(VALUE1, s)
        );
    }

    @Test
    @DisplayName("get(), ключ не найден, возращает null")
    void get_KeyNotFoundReturnNull() {
        String s = map.get(NOT_FOUND_VALUE);

        assertNull(s);
    }

    @Test
    @DisplayName("put(), ключ отсутствует в map, успешно добавлена новая пара")
    void put_KeyNotFound() {
        String newKey = "new key";
        String newValue = "new value";

        String s = map.put(newKey, newValue);

        assertNull(s);
    }

    @Test
    @DisplayName("put(), ключ уже существует в map, обновление value")
    void put_KeyExist() {
        String value = "new value";

        String s = map.put(KEY1, value);
        assertAll(
                () -> assertNotNull(s),
                () -> assertEquals(VALUE1, s)
        );
    }

    @Test
    @DisplayName("put(), ключь равен null, выброшено исключение NullPointerException")
    public void put_NullKey() {
        assertThrows(NullPointerException.class, () -> map.put(null, VALUE1));
    }

    @Test
    @DisplayName("put(), значение равно null, выброшено исключение IllegalArgumentException")
    public void put_NullValue() {
        assertThrows(IllegalArgumentException.class, () -> map.put(KEY1, null));
    }

    @Test
    void remove() {
        String returnValue = map.remove(KEY1);

        assertAll(
                () -> assertNotNull(returnValue),
                () -> assertEquals(VALUE1, returnValue)
        );
    }

    @Test
    @DisplayName("remove(), ключ не найден, возращает null")
    void remove_keyNotFound() {
        String returnValue = map.remove(NOT_FOUND_VALUE);

        assertNull(returnValue);
    }

    @Test
    void putAll() {
        Map<String, String> expectedMap = Map.of("q", "w", "e", "r");

        map.putAll(expectedMap);

        assertAll(() -> assertEquals(4, map.size()),
                () -> assertEquals("w", map.get("q")),
                () -> assertEquals("r", map.get("e"))
        );
    }

    @Test
    void clear() {
        map.clear();

        assertTrue(map.isEmpty());
    }

    @Test
    void keySet() {
        Set<String> keySet = map.keySet();
        assertAll(
                () -> assertEquals(2, keySet.size()),
                () -> assertTrue(keySet.contains(KEY1)),
                () -> assertTrue(keySet.contains(KEY2))
        );
    }

    @Test
    void values() {
        Collection<String> values = map.values();
        assertAll(
                () -> assertEquals(2, values.size()),
                () -> assertTrue(values.containsAll(List.of(VALUE1, VALUE2)))
        );
    }

    @Test
    void entrySet() {
        Set<Map.Entry<String, String>> entries = map.entrySet();
        assertAll(
                () -> assertEquals(2, entries.size()),
                () -> assertTrue(entries.contains(Map.entry(KEY1, VALUE1))),
                () -> assertTrue(entries.contains(Map.entry(KEY2, VALUE2)))
        );
    }
}