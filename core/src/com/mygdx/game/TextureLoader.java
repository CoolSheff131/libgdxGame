package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.mygdx.game.gamescreen.cards.Items;

public class TextureLoader {
    private static Skin skin;
    static{//загрузка текстур
        skin = new Skin();


        skin.add("exit", new Texture("sprites/ButtonHome.png"));
        skin.add("exitPressed", new Texture("sprites/ButtonHomePressed.png"));
        skin.add("restart", new Texture("sprites/ButtonRestart.png"));
        skin.add("restartPressed", new Texture("sprites/ButtonRestartPressed.png"));

        skin.add("pause", new Texture("sprites/ButtonPause.png"));
        skin.add("pausePressed", new Texture("sprites/ButtonPausePressed.png"));

        skin.add("left", new Texture("sprites/ButtonLeftArrow.png"));
        skin.add("leftPressed", new Texture("sprites/ButtonLeftArrowPressed.png"));
        skin.add("right", new Texture("sprites/ButtonRightArrow.png"));
        skin.add("rightPressed", new Texture("sprites/ButtonRightArrowPressed.png"));

        skin.add("craft", new Texture("sprites/ButtonCraft.png"));
        skin.add("craftPressed", new Texture("sprites/ButtonCraftPressed.png"));

        skin.add("power", new Texture("sprites/ButtonPower.png"));
        skin.add("powerPressed", new Texture("sprites/ButtonPowerPressed.png"));
        skin.add("resume", new Texture("sprites/ButtonPlay.png"));
        skin.add("resumePressed", new Texture("sprites/ButtonPlayPressed.png"));
        skin.add("next", new Texture("sprites/ButtonNext.png"));
        skin.add("nextPressed", new Texture("sprites/ButtonNextPressed.png"));
        skin.add("bgn", new Texture("sprites/BgnMother.png"));
        skin.add("bgnInfo", new Texture("sprites/BgnInfo.png"));
        skin.add("craftM", new Texture("sprites/CraftMachine.png"));
        skin.add("wood", new Texture("sprites/Wood.png"));

        skin.add("fieldCell", new Texture("sprites/FieldCellImg.png"));
        skin.add("badlogic", new Texture("heart.png"));
        skin.add("coem", new Texture("sprites/coem.png"));
        skin.add("res", new Texture("sprites/resourceImg.png"));
        skin.add("scheme", new Texture("sprites/schemeImg.png"));
        skin.add("worker", new Texture("sprites/workerImg.png"));

        skin.add("BuildR", new Texture("sprites/BuildRes.png"));
        skin.add("BuildRSch", new Texture("sprites/SchemeRes.png"));
        skin.add("BuildS", new Texture("sprites/BuildScheme.png"));
        skin.add("BuildSSch", new Texture("sprites/SchemeSch.png"));
        skin.add("BuildW", new Texture("sprites/BuildWorker.png"));
        skin.add("BuildWSch", new Texture("sprites/SchemeWork.png"));
        skin.add("BuildEn", new Texture("sprites/BuildEn.png"));
        skin.add("BuildEnSch", new Texture("sprites/SchemeEn.png"));

        skin.add("craftCell", new Texture("sprites/CraftingCell.png"));
        skin.add("quickBuild", new Texture("sprites/quickBuild.png"));
        skin.add("demolishBuild", new Texture("sprites/demolishBuild.png"));
        skin.add("activateBuild", new Texture("sprites/activateBuild.png"));

        skin.add("bookBtn", new Texture("sprites/ButtonBook.png"));
        skin.add("bookBtnPressed", new Texture("sprites/ButtonBookPressed.png"));
    }
    public static Skin getSkin(){
        return skin;
    }

    public static Drawable getDrawable(String drawableName){
        return skin.getDrawable(drawableName);
    }
    public static Drawable getDrawable(Items item){
        switch (item){

            case ENERGY_BUILDING:return skin.getDrawable("BuildEn");
            case WORKER_BUILDING:return skin.getDrawable("BuildW");
            case SCHEME_BUILDING:return skin.getDrawable("BuildS");
            case RESOURSE_BUILDING: return skin.getDrawable("BuildR");

            case RESOURSE_CARD:return skin.getDrawable("res");
            case WORKER_CARD:return skin.getDrawable("worker");
            case SCHEME_CARD:return skin.getDrawable("scheme");

            case QUICKBUILD_UPGRADE:return skin.getDrawable("quickBuild");
            case ACTIVATEBUILD_UPGRADE: return skin.getDrawable("activateBuild");
            case DEMOLISHBUILD_UPGRADE: return skin.getDrawable("demolishBuild");
        }
        return skin.getDrawable("wood");
    }

}
