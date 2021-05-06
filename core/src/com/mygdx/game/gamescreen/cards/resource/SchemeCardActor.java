package com.mygdx.game.gamescreen.cards.resource;

import com.mygdx.game.gamescreen.cards.Items;

public class SchemeCardActor extends Crafting {
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
