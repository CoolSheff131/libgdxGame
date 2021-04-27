package com.mygdx.game;

import com.mygdx.game.cards.CardActor;
import com.mygdx.game.cards.Factory;

import java.util.ArrayList;


public class CraftingSystem {
    private static final int CRAFT_SIZE = 3;
    private static ArrayList<Recipe> recipes = new ArrayList<>();
    static {
        recipes.add(new Recipe(new Items[]{Items.RESOURSE_CARD,Items.RESOURSE_CARD,Items.RESOURSE_CARD}, Items.ENERGY_BUILDING));
        recipes.add(new Recipe(new Items[]{Items.RESOURSE_CARD,Items.WORKER_CARD,Items.SCHEME_CARD}, Items.STORAGE_BUILDING));
        recipes.add(new Recipe(new Items[]{Items.RESOURSE_CARD}, Items.STORAGE_BUILDING));

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
