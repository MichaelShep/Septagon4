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

 */

public class LoadGameScene {

    private SpriteBatch stageBatch;
    public Stage stage;
    public Table table = new Table();
    private NinePatchDrawable background = new NinePatchDrawable(new NinePatch(new Texture("Grey.png"), 3, 3, 3, 3));

    private Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

    public TextButton loadGame1Button = new TextButton("Load Game 1", skin);
    public TextButton loadGame2Button = new TextButton("Load Game 2", skin);
    public TextButton loadGame3Button = new TextButton("Load Game 3", skin);
    public TextButton loadGame4Button = new TextButton("Load Game 4", skin);
    public TextButton loadGame5Button = new TextButton("Load Game 5", skin);

    public TextButton backButton = new TextButton("Back", skin);

    private float width = Gdx.graphics.getWidth();
    private float centre = width* 0.7f;

    public LoadGameScene(Kroy game){
        stageBatch = game.batch;
        Viewport viewport = new ScreenViewport(new OrthographicCamera());
        stage = new Stage(viewport, stageBatch);

        table.setBackground(background);

        table.add(loadGame1Button).width(centre/3.0f);
        table.row();
        table.add(loadGame2Button).width(centre/3.0f);
        table.row();
        table.add(loadGame3Button).width(centre/3.0f);
        table.row();
        table.add(loadGame4Button).width(centre/3.0f);
        table.row();
        table.add(loadGame5Button).width(centre/3.0f);
        table.row();
        table.add(backButton).width(centre/3.0f);
        table.row();

        table.setFillParent(true);
        stage.addActor(table);
    }

    public void visibility(boolean state){ this.table.setVisible(state);}
}
