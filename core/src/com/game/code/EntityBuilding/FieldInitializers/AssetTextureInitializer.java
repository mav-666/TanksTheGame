package com.game.code.EntityBuilding.FieldInitializers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.game.code.utils.Assets;

public class AssetTextureInitializer extends FieldInitializer<TextureRegion, String> {

    private final Assets assets;

    public AssetTextureInitializer(Assets assets) {
        this.assets = assets;
    }

    @Override
    public TextureRegion getInitBy(String config) {
        Array<TextureAtlas.AtlasRegion> regions = assets.getTextureAtlas().findRegions(config);

        try {
            return regions.get((int) (Math.random() * regions.size));
        } catch (Exception e) {
            Gdx.app.log("Error", e.toString());
            Gdx.app.log("Error" ,"Texture not found: " + config);
            return assets.getTextureAtlas().findRegion("cab");
        }
    }
}
