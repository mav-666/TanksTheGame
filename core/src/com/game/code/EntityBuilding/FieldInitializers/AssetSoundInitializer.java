package com.game.code.EntityBuilding.FieldInitializers;

import com.badlogic.gdx.audio.Sound;
import com.game.code.utils.Assets.Assets;
import com.game.code.utils.Assets.SoundType;

public class AssetSoundInitializer extends FieldInitializer<Sound, String> {

    private final Assets assets;

    public AssetSoundInitializer(Assets assets) {
        this.assets = assets;
    }

    @Override
    public Sound getInitBy(String config) {
        return assets.getSound(SoundType.valueOf(config));
    }
}
