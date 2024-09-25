package ru.panov;

import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Map<String, String> map = new RedisMap("user2");
        map.put("username", "user");
        map.put("password", "password");
        map.put("name", "name");
        System.out.println(map.get("username"));
        System.out.println(map.keySet());
        System.out.println(map.values());
        System.out.println(map.entrySet());
        map.clear();
    }
}