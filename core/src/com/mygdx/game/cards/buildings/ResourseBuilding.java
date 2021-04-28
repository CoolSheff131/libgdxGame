package com.mygdx.game.cards.buildings;

import com.mygdx.game.Items;
import com.mygdx.game.Singleton;
import com.mygdx.game.cards.Factory;

public class ResourseBuilding extends Building{
    public ResourseBuilding() {
        super("BuildR","BuildR");
    }

    @Override
    public String getDrawableName() {
        return "BuildR";
    }

    @Override
    public Items getItemType() {
        return Items.STORAGE_BUILDING;
    }

    @Override
    public void doThing() {
        Singleton.addcraftingcard(Factory.createCard(Items.RESOURSE_CARD));
        //todo добавить действие на завершение хода
    }
}
