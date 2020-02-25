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

public class DifficultyScene {
    public Stage stage;
    public Table table = new Table();
    private SpriteBatch sb;
    private NinePatchDrawable background = new NinePatchDrawable(new NinePatch(new Texture("Grey.png"), 3, 3, 3, 3));

    private Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

    public TextButton easyButton = new TextButton("Easy",skin);
    public TextButton mediumButton = new TextButton("Medium",skin);
    public TextButton hardButton = new TextButton("Hard",skin);

    private float width = Gdx.graphics.getWidth();
    private float centre = width* 0.7f;

    public DifficultyScene(Kroy game){
        sb = game.batch;
        Viewport viewport = new ScreenViewport(new OrthographicCamera());
        stage = new Stage(viewport, sb);

        table.setBackground(background);
        table.add(easyButton).width(centre/3.0f);
        table.row();
        table.add(mediumButton).width(centre/3.0f);
        table.row();
        table.add(hardButton).width(centre/3.0f);

        table.setFillParent(true);
        stage.addActor(table);
    }
    public void visibility(boolean state){
        this.table.setVisible(state);
    }
}
