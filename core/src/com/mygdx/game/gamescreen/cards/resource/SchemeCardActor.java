package com.mygdx.game.gamescreen.cards.resource;

import com.mygdx.game.gamescreen.Items;
import com.mygdx.game.gamescreen.cards.CardActor;

public class SchemeCardActor extends CardActor {
    public SchemeCardActor() {
        super("scheme","scheme");
    }

    @Override
    public String getDrawableName() {
        return "scheme";
    }

    @Override
    public Items getItemType() {
        return Items.SCHEME_CARD;
    }
}
