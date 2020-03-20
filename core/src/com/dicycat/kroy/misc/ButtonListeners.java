package com.dicycat.kroy.misc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.dicycat.kroy.screens.GameScreen;

/**
 * Class added as a result of refractoring the GameScreen class
 * Used to adding all the listener methods to all the buttons in the pause menu
 */
public class ButtonListeners
{
    private GameScreen gameScreen;
    private SaveManager saveManager;

    private Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

    public ButtonListeners(GameScreen gameScreen, SaveManager saveManager)
    {
        this.gameScreen = gameScreen;
        this.saveManager = saveManager;
        addPauseScreenListeners();
        addSaveScreenListners();
    }

    /**
     * Checks the pause buttons
     */
    private void addPauseScreenListeners() {
        //resume button
        gameScreen.getPauseWindow().resume.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameScreen.getPauseWindow().visibility(false);
                gameScreen.resume();
            }
        });

        //exit button
        gameScreen.getPauseWindow().exit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        //menu button
        gameScreen.getPauseWindow().menu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameScreen.getPauseWindow().visibility(false);
                gameScreen.dispose();
                gameScreen.getGame().backToMenu();
                return;
            }
        });

        //Add in functionality for clicking save button [ID: SAVE PRESSED]
        gameScreen.getPauseWindow().save.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameScreen.getSaveWindow().visibility(true);
                gameScreen.setGameState(GameScreen.GameScreenState.SAVE);
                return;
            }
        });
    }

    /***
     * Adds input handlers for all the buttons in the save screen
     * [ID: SAVE HANDLER]
     */
    public void addSaveScreenListners(){
        gameScreen.getSaveWindow().saveGame1Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                clickedSavedButton(0);
                return;
            }
        });
        gameScreen.getSaveWindow().saveGame2Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                clickedSavedButton(1);
                return;
            }
        });
        gameScreen.getSaveWindow().saveGame3Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                clickedSavedButton(2);
                return;
            }
        });
        gameScreen.getSaveWindow().saveGame4Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                clickedSavedButton(3);
                return;
            }
        });
        gameScreen.getSaveWindow().saveGame5Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                clickedSavedButton(4);
                return;
            }
        });

        gameScreen.getSaveWindow().backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameScreen.setGameState(GameScreen.GameScreenState.PAUSE);
                return;
            }
        });
    }

    /**
     * Adds functionality for saving a game into a specific save slot [ID: SAVE SLOT]
     * @param saveNumber The save slot that the current game should be saved into
     */
    private void clickedSavedButton(int saveNumber)
    {
        Gdx.input.setInputProcessor(gameScreen.getPauseWindow().stage);
        //If there is already a save at this location, ask the user if they want to overwrite that save
        if(saveManager.getPreferences().get(saveNumber).getBoolean("hasUsedSave") == true)
        {
            OverwriteDialog overwriteDialog = new OverwriteDialog(skin, gameScreen, 0);
            overwriteDialog.show(gameScreen.getSaveWindow().stage);
        }else
        {
            gameScreen.performSave(saveNumber);
        }
    }


}
