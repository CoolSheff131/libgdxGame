package com.mygdx.game.gamescreen.cards.upgrades;

import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.mygdx.game.gamescreen.Singleton;
import com.mygdx.game.gamescreen.cards.CardActor;
import com.mygdx.game.gamescreen.cards.CardFamily;
import com.mygdx.game.gamescreen.cards.Items;
import com.mygdx.game.gamescreen.cards.buildings.Building;



public abstract class Upgrade extends CardActor {



    public Upgrade(String drawableName, String name) {
        super(drawableName, name);
    }

    @Override
    public String getDrawableName() {
        return null;
    }

    public abstract void dropDo(Building building);

    public abstract boolean canDrop(Building building);

    @Override
    public CardFamily getFamilyType() {
        return CardFamily.UPGRAGE;
    }
}
