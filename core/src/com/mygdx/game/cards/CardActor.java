package com.mygdx.game.cards;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.Items;
import com.mygdx.game.MotherBoardCard;

public abstract class CardActor extends Image {
    public CardActor(String drawableName) {
        super(MotherBoardCard.skin, drawableName);

    }
    public abstract String getDrawableName();

    public abstract Items getItemType();
}
