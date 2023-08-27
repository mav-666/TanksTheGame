package com.game.code.utils.Assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.ObjectMap;

public class SoundEffectAssetLoader {

    private static final String SOUND_PATH = "Sound/";

    private final ObjectMap<SoundType, Sound> loadedSounds = new ObjectMap<>();
    private final AssetManager assetManager;

    SoundEffectAssetLoader(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public void loadSounds() {
        for (SoundType soundType : SoundType.values()) {
            assetManager.load(SOUND_PATH + soundType.name() + ".mp3", Sound.class);
        }
    }

    public Sound getSoundEffect(SoundType name) {
        if(!loadedSounds.containsKey(name))
            addSoundEffect(name, assetManager.get(SOUND_PATH + name + ".mp3", Sound.class));
        return loadedSounds.get(name);
    }

    public void addSoundEffect(SoundType name, Sound sound) {
        loadedSounds.put(name, sound);
    }
}
