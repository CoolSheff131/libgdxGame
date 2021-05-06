package com.mygdx.game.gamescreen;

import com.mygdx.game.gamescreen.cards.CardActor;
import com.mygdx.game.gamescreen.cards.Factory;

import java.util.ArrayList;


public class CraftingSystem {
    private static final int CRAFT_SIZE = 3;
    private static ArrayList<Recipe> recipes = new ArrayList<>();
    static {
        recipes.add(new Recipe(new Items[]{Items.RESOURSE_CARD,Items.WORKER_CARD,Items.SCHEME_CARD}, Items.ENERGY_BUILDING));
        recipes.add(new Recipe(new Items[]{Items.RESOURSE_CARD,Items.RESOURSE_CARD,Items.SCHEME_CARD}, Items.RESOURSE_BUILDING));
        recipes.add(new Recipe(new Items[]{Items.WORKER_CARD,Items.WORKER_CARD,Items.SCHEME_CARD}, Items.WORKER_BUILDING));
        recipes.add(new Recipe(new Items[]{Items.SCHEME_CARD,Items.SCHEME_CARD,Items.SCHEME_CARD}, Items.SCHEME_BUILDING));
    }
    public static CardActor GetRecipeOutput(ArrayList<CardActor> input){
        Items[] items = new Items[CRAFT_SIZE];
        for (int i = 0; i < CRAFT_SIZE && i < input.size(); i++) {
            items[i] = input.get(i).getItemType();
        }
        for (Recipe recipe: recipes) {
            boolean complete = true;
            Items[] craft = recipe.getRecipe();
            for (int i = 0; i < CRAFT_SIZE && i < craft.length && i < items.length; i++) {
                if (craft[i] != items[i]){
                    complete = false;
                }
            }
         if (complete) {
             return Factory.createCard(recipe.getOut());
         }
        }
        return null;
    }



}
