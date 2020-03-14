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

public class SaveGameScene {
    private SpriteBatch stageBatch;
    public Stage stage;
    public Table table = new Table();
    private NinePatchDrawable background = new NinePatchDrawable(new NinePatch(new Texture("Grey.png"), 3, 3, 3, 3));

    private Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

    public TextButton saveGame1Button = new TextButton("Save Game 1", skin);
    public TextButton saveGame2Button = new TextButton("Save Game 2", skin);
    public TextButton saveGame3Button = new TextButton("Save Game 3", skin);
    public TextButton saveGame4Button = new TextButton("Save Game 4", skin);
    public TextButton saveGame5Button = new TextButton("Save Game 5", skin);
    public TextButton backButton = new TextButton("Back", skin);

    private float width = Gdx.graphics.getWidth();
    private float centre = width* 0.7f;

    public SaveGameScene(Kroy game){
        stageBatch = game.batch;
        Viewport viewport = new ScreenViewport(new OrthographicCamera());
        stage = new Stage(viewport, stageBatch);

        table.setBackground(background);

        table.add(saveGame1Button).width(centre/3.0f);
        table.row();
        table.add(saveGame2Button).width(centre/3.0f);
        table.row();
        table.add(saveGame3Button).width(centre/3.0f);
        table.row();
        table.add(saveGame4Button).width(centre/3.0f);
        table.row();
        table.add(saveGame5Button).width(centre/3.0f);
        table.row();
        table.add(backButton).width(centre/3.0f);
        table.row();

        table.setFillParent(true);
        stage.addActor(table);
    }

    public void visibility(boolean state){ this.table.setVisible(state);}
}
