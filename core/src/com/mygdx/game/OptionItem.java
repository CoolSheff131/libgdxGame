package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import static com.mygdx.game.MainMenuScreen.checkBtnStyle;
import static com.mygdx.game.MainMenuScreen.lblfont;

public class OptionItem extends Group {
    private Label label;
    private Button button;

    public Button getButton() {
        return button;
    }

    public OptionItem(String text){
        label = new Label(text,new Label.LabelStyle(lblfont, Color.WHITE));
        button = new Button(checkBtnStyle);
        addActor(label);
        addActor(button);
        label.setBounds(0,0,MainMenuScreen.BUTTON_WIDTH/3*2,MainMenuScreen.BUTTON_HEIGHT);
        button.setBounds(MainMenuScreen.BUTTON_WIDTH/3*2+MainMenuScreen.BUTTON_WIDTH/6,0,MainMenuScreen.BUTTON_WIDTH/6,MainMenuScreen.BUTTON_WIDTH/6);
    }
}
