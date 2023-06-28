package com.game.code.EntityBuilding;

import java.util.HashMap;

public class CachedConfigFactory<T extends Config> implements ConfigFactory<T> {

    private final HashMap<String, T> cache;

    private final ConfigFactory<T> configFactory;

    CachedConfigFactory(ConfigFactory<T> configFactory) {
        this.configFactory = configFactory;

        cache = new HashMap<>();
    }

    @Override
    public T get(String configName) {
        if(!cache.containsKey(configName))
            cache.put(configName, configFactory.get(configName));

        return cache.get(configName);
    }
}
