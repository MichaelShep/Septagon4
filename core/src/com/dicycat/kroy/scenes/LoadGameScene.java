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

/**
Added by Septagon

 A Scene in the menu system that allows users to choose which save they want
 to load.

 [ID: LOAD SCENE]

 */

public class LoadGameScene extends Scene {

    public TextButton loadGame1Button = new TextButton("Load Game 1", skin);
    public TextButton loadGame2Button = new TextButton("Load Game 2", skin);
    public TextButton loadGame3Button = new TextButton("Load Game 3", skin);
    public TextButton loadGame4Button = new TextButton("Load Game 4", skin);
    public TextButton loadGame5Button = new TextButton("Load Game 5", skin);

    public TextButton backButton = new TextButton("Back", skin);

    public LoadGameScene(Kroy game){
        super(game);

        super.addRow(loadGame1Button);
        super.addRow(loadGame2Button);
        super.addRow(loadGame3Button);
        super.addRow(loadGame4Button);
        super.addRow(loadGame5Button);
        super.addRow(backButton);

        table.setFillParent(true);
        stage.addActor(table);
    }
}
