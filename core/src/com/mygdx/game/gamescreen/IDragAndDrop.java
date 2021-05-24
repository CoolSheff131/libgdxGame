package com.mygdx.game.gamescreen;

import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;

public interface IDragAndDrop {
    public abstract DragAndDrop.Payload dragStart();
    public abstract void dragStop(DragAndDrop.Payload payload);
    public abstract boolean canDrop();
    public abstract void drop();

}
