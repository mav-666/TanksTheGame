package com.game.code.BattleField;

import com.game.code.utils.box2d.BodyHandler;

public enum BattleFieldBuilders {
    plain((bodyHandler, data) -> new PlainBuilder(bodyHandler, (PlainBuilderData) data)),
    ;

    public Creator creator;

    BattleFieldBuilders(Creator creator) {
        this.creator = creator;
    }
    public interface Creator {
        BattleFieldBuilder create(BodyHandler bodyHandler, BattleFieldBuilderJsonData builderJsonData);
    }
}
