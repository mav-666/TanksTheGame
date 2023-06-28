package com.game.code.EntityBuilding;

public interface ConfigFactory<T extends Config> {

    T get(String configName);

    static <T extends Config> ConfigFactory<T> cache(ConfigFactory<T> configFactory) {
        return new CachedConfigFactory<>(configFactory);
    }
}
