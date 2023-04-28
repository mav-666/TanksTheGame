package com.game.code.UI;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.game.code.AssetManagment.AssetProcessor;
import com.game.code.AssetManagment.AssetRequest;
import com.game.code.Entity.Breakable;

public class HealthBarApplier extends Group implements AssetRequest {

    public void applyHealthBar(Breakable target) {
        addActor(new HealthBar(target));
    }

    @Override
    public void request(AssetProcessor assetRequestProcessor) {
        getChildren().forEach((healthBar) -> ((AssetRequest) healthBar).request(assetRequestProcessor));
    }

    @Override
    public void passAssets(AssetProcessor assets) {}
}
