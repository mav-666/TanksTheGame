package com.game.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;

import com.game.code.AssetManagment.AssetRequest;
import com.game.code.AssetManagment.AssetRequestProcessor;
import com.game.code.Entity.Breakable;

import com.ray3k.tenpatch.TenPatchDrawable;

public class HealthBar extends Stack implements AssetRequest {
    float lastHealth;
    Breakable target;
    Slider slider;
    Image foreground;

    public HealthBar(Breakable target) {
        this.target = target;
        lastHealth = target.getHealth();

        Vector2 pos = target.getBody().getPosition();

        setPosition(PPM.toPixels(pos.x), PPM.toPixels(pos.y));
        setSize(PPM.toPixels(((Actor) target).getWidth()), PPM.toPixels(((Actor) target).getHeight()/3f));

        getColor().sub(0,0,0,1);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        setPosition(PPM.toPixels(target.getBody().getPosition().x) - getWidth()/2
                , PPM.toPixels(target.getBody().getPosition().y) + getHeight()*2);

        if(lastHealth == target.getHealth()) {
            lastHealth = target.getHealth();
            return;
        }

        lastHealth = target.getHealth();

        getActions().clear();
        addAction(
                Actions.sequence(
                        Actions.parallel(
                                Actions.fadeIn(0.1f),
                                Actions.run(() -> slider.setValue(target.getHealth()))
                        ),
                        Actions.delay(1.5f),
                        Actions.fadeOut(0.3f)
                )
        );
    }


    @Override
    public void request(AssetRequestProcessor assetRequestProcessor) {
        assetRequestProcessor.receiveRequest("skin.json", Skin.class, this);
    }

    @Override
    public void passAssets(AssetRequestProcessor assets) {
        Skin skin = assets.get("skin.json", Skin.class);
        Slider.SliderStyle sliderStyle = skin.get(Slider.SliderStyle.class);

        foreground = new Image(skin.get("HealthBar", TenPatchDrawable.class));

        slider = new Slider(0, target.getHealth(), 1, false, sliderStyle);

        slider.setValue(target.getHealth());
        slider.setAnimateInterpolation(Interpolation.bounceOut);
        slider.setAnimateDuration(0.4f);

        addActor(new Container<>(slider).width(getWidth() - 2).pad(2,2, 0, 2).height(foreground.getHeight()));
        addActor(new Container<>(foreground).width(getWidth()));
    }
}
