package com.dicycat.kroy.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dicycat.kroy.Kroy;
import com.dicycat.kroy.misc.OverwriteDialog;

import java.awt.*;

/***
 * Class for managing the save game menu screen
 * [ID: SAVE SCENE]
 */

public class SaveGameScene extends Scene {

    public TextButton saveGame1Button = new TextButton("Save Game 1", skin);
    public TextButton saveGame2Button = new TextButton("Save Game 2", skin);
    public TextButton saveGame3Button = new TextButton("Save Game 3", skin);
    public TextButton saveGame4Button = new TextButton("Save Game 4", skin);
    public TextButton saveGame5Button = new TextButton("Save Game 5", skin);
    public TextButton backButton = new TextButton("Back", skin);

    public SaveGameScene(Kroy game){
        super(game);

        super.addRow(saveGame1Button);
        super.addRow(saveGame2Button);
        super.addRow(saveGame3Button);
        super.addRow(saveGame4Button);
        super.addRow(saveGame5Button);
        super.addRow(backButton);

        table.setFillParent(true);
        stage.addActor(table);
    }
}
