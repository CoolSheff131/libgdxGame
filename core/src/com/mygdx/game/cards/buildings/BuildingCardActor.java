package com.mygdx.game.cards.buildings;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import static com.mygdx.game.MotherBoardCard.skin;

public class BuildingCardActor extends Image {

    public BuildingCardActor( String drawableName){
        super(skin,drawableName);
    }


}
