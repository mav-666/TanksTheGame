package com.game.code.utils;

import com.badlogic.ashley.signals.Signal;

public class MatchTime {

    private final Signal<Boolean> timeEndSignal = new Signal<>();
    private int currentTime;

    public MatchTime(int matchDurationSec) {
        this.currentTime = matchDurationSec;
    }

    public void updateTime() {
        currentTime = Math.max(0, --currentTime);
        if(currentTime == 0) timeEndSignal.dispatch(true);
    }

    public Signal<Boolean> getTimeEndSignal() {
        return timeEndSignal;
    }

    @Override
    public String toString() {
        int minutes = (int) (currentTime/60f);
        int seconds = (int) ((currentTime/60f - minutes) * 60);

        String minutesStr = (String.valueOf(minutes).split("").length > 1? "": "0") + minutes;
        String secondsStr = (String.valueOf(seconds).split("").length > 1? "": "0") + seconds;

        return minutesStr + ":" + secondsStr;
    }
}
