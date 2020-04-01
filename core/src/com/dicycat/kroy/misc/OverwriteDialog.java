package com.dicycat.kroy.misc;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.dicycat.kroy.screens.GameScreen;

/**
 * Class used to display a message asking the user if the want to ooverwrite the current save
 * Created by Septagon
 */

public class OverwriteDialog extends Dialog
{
    private boolean returnVal;
    private GameScreen gameScreen;
    private int saveNumber;

    public OverwriteDialog(Skin skin, GameScreen gameScreen, int saveNumber)
    {
        super("Overwrite Previous Save?", skin, "dialog");
        this.gameScreen = gameScreen;
        this.saveNumber = saveNumber;
        setup();
    }

    private void setup()
    {
        this.text("There is already a save file here, do you want to overwrite it?");
        this.button("Yes", true); //Will return true from the dialog if the user presses the yes button
        this.button("No", false); //Will return false from the dialog if the user presses the no button
        this.getTitleLabel().setColor(Color.BLACK);
        this.setColor(1.0f, 0.0f, 0.0f, 1.0f);
    }

    public void result(Object obj)
    {
        returnVal = (boolean)obj;

        if(returnVal == true)
        {
            gameScreen.performSave(saveNumber);
            return;
        }else
        {
            System.out.println("Did not want to overwrite");
            return;
        }
    }

    public boolean getReturnVal() { return returnVal; }
    public GameScreen getGameScreen() { return gameScreen; }
    public int getSaveNumber() { return saveNumber; }
}
