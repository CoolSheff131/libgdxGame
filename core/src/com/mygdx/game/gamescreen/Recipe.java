package com.mygdx.game.gamescreen;

import com.mygdx.game.gamescreen.cards.Items;

public class Recipe {

    private com.mygdx.game.gamescreen.cards.Items[] recipe;
    private com.mygdx.game.gamescreen.cards.Items out;

    public Recipe(com.mygdx.game.gamescreen.cards.Items[] recipe, com.mygdx.game.gamescreen.cards.Items out) {
        this.recipe = recipe;
        this.out = out;
    }
    public com.mygdx.game.gamescreen.cards.Items[] getRecipe(){
        return recipe;
    }

    public Items getOut() {
        return out;
    }
}
