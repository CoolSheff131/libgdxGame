package com.mygdx.game.gamescreen;


import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.gamescreen.cells.FieldCellActor;

public class FieldCells extends Table {
    private static FieldCellActor[][] fieldCells;
    public FieldCells(int width, int height, int str, int col) {
        setWidth(width);
        fieldCells = new FieldCellActor[str][col];
        int CELL_WIDTH = 100;
        for (int i = 0; i < fieldCells.length; i++) {
            for (int j = 0; j < fieldCells[i].length; j++) {
                fieldCells[i][j] = new FieldCellActor(fieldCells, height / str, i, j);
                fieldCells[i][j].setSize(CELL_WIDTH, CELL_WIDTH);
                add(fieldCells[i][j]).size(height / str, height / str);
                Singleton.getDragAndDrop().addTarget(fieldCells[i][j].getTarget());
            }
            row();
        }

    }
    public void clearField(){
        for(FieldCellActor fieldRow[]: fieldCells)
            for(FieldCellActor fieldCell: fieldRow)
                fieldCell.clearCell();
    }
    public void makePower(){
        for (FieldCellActor[] fieldRow: fieldCells) {
            for(FieldCellActor fieldCell: fieldRow){
                fieldCell.doEndTurnThing();
            }
        }
    }

    public FieldCellActor[][] getFieldCells() {
        return fieldCells;
    }
}
