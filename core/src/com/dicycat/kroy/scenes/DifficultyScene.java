package com.dicycat.kroy.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dicycat.kroy.Kroy;

/**
 * Creates menu scene allowing the user to select their difficulty level
 * [ID: SCENE]
 */

public class DifficultyScene extends Scene {

    public TextButton easyButton = new TextButton("Easy",skin);
    public TextButton mediumButton = new TextButton("Medium",skin);
    public TextButton hardButton = new TextButton("Hard",skin);

    public DifficultyScene(Kroy game){
        super(game);

        super.addRow(easyButton);
        super.addRow(mediumButton);
        super.addRow(hardButton);

        table.setFillParent(true);
        stage.addActor(table);
    }
}
