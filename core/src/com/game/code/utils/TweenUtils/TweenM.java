package com.game.code.utils.TweenUtils;

import aurelienribon.tweenengine.TweenManager;

public class TweenM {

    private final TweenManager tweenManager;

    private static TweenM tween;

    private TweenM() {
        tweenManager = new TweenManager();
    }

    public static TweenM getInstance() {
        if(tween == null)
            tween = new TweenM();
        return tween;
    }

    public TweenManager getManager() {
        return tweenManager;
    }
}
