package com.game.code.EntityBuilding.FieldInitializers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.game.code.utils.Assets.Assets;

public class AssetTextureInitializer extends FieldInitializer<TextureRegion, String> {

    private final Assets assets;

    private final ObjectMap<String, Array<TextureAtlas.AtlasRegion>> foundRegions = new ObjectMap<>();

    public AssetTextureInitializer(Assets assets) {
        this.assets = assets;
    }

    @Override
    public TextureRegion getInitBy(String config) {
        if(!foundRegions.containsKey(config)) {
            foundRegions.put(config, assets.getTextureAtlas().findRegions(config));
        }

        Array<TextureAtlas.AtlasRegion> regions = foundRegions.get(config);

        try {
            return regions.get((int) (Math.random() * regions.size));
        } catch (Exception e) {
            Gdx.app.log("Error", e.toString());
            Gdx.app.log("Error" ,"Texture not found: " + config);
            return assets.getTextureAtlas().findRegion("cab");
        }
    }
}
