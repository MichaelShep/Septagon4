package com.dicycat.kroy.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dicycat.kroy.Kroy;

public class LoadGameScene {

    private SpriteBatch stageBatch;
    public Stage stage;
    public Table table = new Table();
    private NinePatchDrawable background = new NinePatchDrawable(new NinePatch(new Texture("Grey.png"), 3, 3, 3, 3));

    public LoadGameScene(Kroy game){
        stageBatch = game.batch;
        Viewport viewport = new ScreenViewport(new OrthographicCamera());
        stage = new Stage(viewport, stageBatch);

        table.setBackground(background);
        table.setFillParent(true);
        stage.addActor(table);


    }

    public void visibility(boolean state){ this.table.setVisible(state);}
}
