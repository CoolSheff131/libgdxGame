package com.mygdx.game.gamescreen;

import com.mygdx.game.gamescreen.cards.CardActor;
import com.mygdx.game.gamescreen.cards.Factory;
import com.mygdx.game.gamescreen.cards.Items;

import java.util.ArrayList;


public class CraftingSystem {
    private static final int CRAFT_SIZE = 3;
    private static ArrayList<Recipe> recipes = new ArrayList<>();
    static {
        recipes.add(new Recipe(new com.mygdx.game.gamescreen.cards.Items[]{com.mygdx.game.gamescreen.cards.Items.RESOURSE_CARD, com.mygdx.game.gamescreen.cards.Items.WORKER_CARD, com.mygdx.game.gamescreen.cards.Items.SCHEME_CARD}, com.mygdx.game.gamescreen.cards.Items.ENERGY_BUILDING));
        recipes.add(new Recipe(new com.mygdx.game.gamescreen.cards.Items[]{com.mygdx.game.gamescreen.cards.Items.RESOURSE_CARD, com.mygdx.game.gamescreen.cards.Items.RESOURSE_CARD, com.mygdx.game.gamescreen.cards.Items.SCHEME_CARD}, com.mygdx.game.gamescreen.cards.Items.RESOURSE_BUILDING));
        recipes.add(new Recipe(new com.mygdx.game.gamescreen.cards.Items[]{com.mygdx.game.gamescreen.cards.Items.WORKER_CARD, com.mygdx.game.gamescreen.cards.Items.WORKER_CARD, com.mygdx.game.gamescreen.cards.Items.SCHEME_CARD}, com.mygdx.game.gamescreen.cards.Items.WORKER_BUILDING));
        recipes.add(new Recipe(new com.mygdx.game.gamescreen.cards.Items[]{com.mygdx.game.gamescreen.cards.Items.SCHEME_CARD, com.mygdx.game.gamescreen.cards.Items.SCHEME_CARD, com.mygdx.game.gamescreen.cards.Items.SCHEME_CARD}, com.mygdx.game.gamescreen.cards.Items.SCHEME_BUILDING));
    }
    public static CardActor GetRecipeOutput(ArrayList<CardActor> input){
        com.mygdx.game.gamescreen.cards.Items[] items = new com.mygdx.game.gamescreen.cards.Items[CRAFT_SIZE];
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
