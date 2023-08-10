package com.game.code.utils;

public class MatchTime {

    private int matchDuration;
    private int currentTime;

    public MatchTime(int matchDurationSec) {
        this.matchDuration = currentTime = matchDurationSec;
    }

    public void updateTime() {
        currentTime = Math.max(0, --currentTime);
    }

    public void setMatchDuration(int matchDuration) {
        this.matchDuration = matchDuration;
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
