package com.game.code.utils.TweenUtils;

import aurelienribon.tweenengine.TweenManager;

public class TweenM {

    private static TweenManager tweenManager;

    public static TweenManager getManager() {
        if(tweenManager == null)
            tweenManager = new TweenManager();
        return tweenManager;
    }
}
