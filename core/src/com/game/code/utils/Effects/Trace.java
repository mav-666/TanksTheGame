package com.game.code.utils.Effects;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.ParticleEffectActor;

public class Trace extends Actor {
    private final ParticleEffectActor traceEffect;

    public Trace(ParticleEffectActor traceEffect) {
        this.traceEffect = traceEffect;
    }

    @Override
    protected void setParent(Group parent) {
        super.setParent(parent);

        apply();
    }

    private void apply() {
        traceEffect.setScale(1 / (42f * 4));
        traceEffect.setSize(getWidth(), getHeight());
        traceEffect.setPosition(getX(), getY());

        traceEffect.getEffect().getEmitters().first().getAngle().setHigh(getRotation());
        traceEffect.start();
        getParent().addActorBefore(this, traceEffect);
    }
}
