package com.mygdx.game;

public class Recipe {

    private Items[] recipe;
    private Items out;

    public Recipe(Items[] recipe, Items out) {
        this.recipe = recipe;
        this.out = out;
    }
    public Items[] getRecipe(){
        return recipe;
    }

    public Items getOut() {
        return out;
    }
}
