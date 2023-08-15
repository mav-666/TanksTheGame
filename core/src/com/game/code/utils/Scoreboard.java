package com.game.code.utils;

import com.badlogic.gdx.graphics.Color;
import com.github.tommyettinger.textra.TypingLabel;

import java.util.ArrayList;
import java.util.List;

public class Scoreboard {
    private final List<Score> playerScore = new ArrayList<>();
    private final StringBuilder labelText = new StringBuilder();
    private final TypingLabel label;
    private final Color textColor;

    public Scoreboard(TypingLabel label, Color textColor) {
        this.label = label;
        this.textColor = textColor;
    }

    public void addScore(Score score) {
        playerScore.add(score);

        sort();
    }

    public void sort() {
        playerScore.sort((score1, score2) -> (int) (Math.signum(score2.scoreValue() - score1.scoreValue())));
    }

    public void clear() {
        playerScore.clear();
    }

    public void prettyPrint() {
        labelText.delete(0, labelText.length());
        labelText.append("[#").append(textColor).append("]Scoreboard\n[%75][#ffffffff]{RAINBOW=5.0;0.25;1.0;0.5}1.")
                .append(playerScore.get(0).playerName())
                .append(":")
                .append(playerScore.get(0).scoreValue())
                .append("{RESET}\n[%50][#").append(textColor).append("]");

        for(int i = 1; i < playerScore.size(); i++)
            labelText.append(i+1)
                    .append(".")
                    .append(playerScore.get(i).playerName())
                    .append(":")
                    .append(playerScore.get(i).scoreValue());

        label.setText(labelText.toString());
    }
}

