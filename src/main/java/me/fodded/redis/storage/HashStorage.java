package me.fodded.redis.storage;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class HashStorage {

    @Getter
    private static final HashStorage instance = new HashStorage();

    private static final Map<String, Object> EMPTY_HASH = new HashMap<>();
    private final Map<String, Map<String, Object>> maps = new HashMap<>();

    public void set(String hashName, String key, Object object) {
        Map<String, Object> hash = maps.getOrDefault(hashName, new HashMap<>());
        hash.put(key, object);
        maps.put(hashName, hash);
    }

    public Object get(String hashName, String key) {
        return maps.getOrDefault(hashName, EMPTY_HASH).get(key);
    }
}
