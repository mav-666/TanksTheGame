//package com.game.code.UI;
//
//import com.badlogic.gdx.math.Interpolation;
//import com.badlogic.gdx.math.Vector2;
//import com.badlogic.gdx.scenes.scene2d.Actor;
//import com.badlogic.gdx.scenes.scene2d.actions.Actions;
//import com.badlogic.gdx.scenes.scene2d.ui.*;
//
//import com.game.code.AssetManagment.AssetProcessor;
//import com.game.code.AssetManagment.AssetRequest;
//
//import com.ray3k.tenpatch.TenPatchDrawable;
//
//public class HealthBar extends IndicationBar implements AssetRequest {
//
//    private final Breakable target;
//
//    public HealthBar(Breakable target) {
//        this.target = target;
//        lastValue = target.getHealth();
//
//        Vector2 pos = target.getBody().getPosition();
//
//        setPosition(PPM.toPixels(pos.x), PPM.toPixels(pos.y));
//        setSize(PPM.toPixels(((Actor) target).getWidth()), PPM.toPixels(((Actor) target).getHeight()/3f));
//
//        getColor().sub(0,0,0,1);
//    }
//
//    @Override
//    public void act(float delta) {
//        super.act(delta);
//
//        setPosition(PPM.toPixels(target.getBody().getPosition().x) - getWidth()/2
//                , PPM.toPixels(target.getBody().getPosition().y) + getHeight()*2);
//
//        if(lastValue == target.getHealth()) {
//            lastValue = target.getHealth();
//            return;
//        }
//
//        lastValue = target.getHealth();
//
//        getActions().clear();
//        addAction(
//                Actions.sequence(
//                        Actions.parallel(
//                                Actions.fadeIn(0.1f),
//                                Actions.run(() -> slider.setValue(target.getHealth()))
//                        ),
//                        Actions.delay(1.5f),
//                        Actions.fadeOut(0.3f)
//                )
//        );
//    }
//
//    @Override
//    public void request(AssetProcessor assetRequestProcessor) {
//        assetRequestProcessor.receiveRequest("skin.json", Skin.class, this);
//    }
//
//    @Override
//    public void passAssets(AssetProcessor assets) {
//        Skin skin = assets.get("skin.json", Skin.class);
//
//        init(skin);
//    }
//
//
//    @Override
//    public void init(Skin skin) {
//        foreground = new Image(skin.get("HealthBar-T", TenPatchDrawable.class));
//
//        slider = new Slider(0, target.getHealth(), 1, false, skin, "healthBar");
//
//        slider.setValue(target.getHealth());
//        slider.setAnimateInterpolation(Interpolation.bounceOut);
//        slider.setAnimateDuration(0.4f);
//
//        addActor(new Container<>(slider).width(getWidth() - 2).pad(2,2, 0, 2).height(foreground.getHeight()));
//        addActor(new Container<>(foreground).width(getWidth()));
//    }
//}
