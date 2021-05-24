package com.mygdx.game.gamescreen.cards.resource;

import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.mygdx.game.gamescreen.Singleton;
import com.mygdx.game.gamescreen.cards.CardActor;
import com.mygdx.game.gamescreen.cards.CardFamily;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.mygdx.game.gamescreen.GameScreen.CARD_WIDTH;
import static com.mygdx.game.gamescreen.GameScreen.HEIGHT_CRAFTING_HAND;
import static com.mygdx.game.gamescreen.GameScreen.PADDING;
import static com.mygdx.game.gamescreen.GameScreen.WIDTH_BUTTON;

public abstract class Crafting extends CardActor {


    public Crafting(String drawableName, String name) {
        super(drawableName, name);
    }

    @Override
    public CardFamily getFamilyType() {
        return CardFamily.CRAFTING;
    }
}
