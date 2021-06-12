package com.mygdx.game.gamescreen;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.MediaPlayer;
import com.mygdx.game.TextureLoader;
import com.mygdx.game.gamescreen.cards.CardActor;
import com.mygdx.game.gamescreen.cards.Factory;
import com.mygdx.game.gamescreen.cells.CellActor;
import com.mygdx.game.gamescreen.cells.CraftingCellActor;
import com.mygdx.game.gamescreen.craft.CraftingSystem;



public class WorkshopCells extends Table {

    private CraftingCellActor[] craftingCells;

    public WorkshopCells(int width, int height, int size){
        craftingCells = new CraftingCellActor[size];
        setWidth(width);
        setHeight(height);
        for (int i = 0; i < craftingCells.length ; i++) {
            craftingCells[i] = new CraftingCellActor(width/3);
            add(craftingCells[i]).grow();
            Singleton.getDragAndDrop().addTarget(craftingCells[i].getTarget());

        }
        setDebug(true);

    }

    public void clearCraftingCells(){
        for (CellActor cell: craftingCells) {
            cell.clearCell();

        }
        Singleton.getCardsInCraftingSlots().clear();
    }

    public boolean craft(){
        CardActor res = CraftingSystem.GetRecipeOutput(Singleton.getCardsInCraftingSlots());
        if(res !=null){
            clearCraftingCells();
            Singleton.addBuildingCard(res);
            return true;
        }else {
            return false;
        }
    }
}
