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
 * Class used to hold all basic basic behaviour and attributes that all scenes will use
 * Added as a result of refactoring
 * [ID: SCENE]
 */
public abstract class Scene
{
    //Attributes needed for setting up and displaying the scene
    protected SpriteBatch stageBatch;
    public Stage stage;
    protected Table table = new Table();
    protected NinePatchDrawable background = new NinePatchDrawable(new NinePatch(new Texture("Grey.png"), 3, 3, 3, 3));

    //Used to create the ui design
    protected Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

    //Positioning variables
    protected float width = Gdx.graphics.getWidth();
    protected float centre = width* 0.7f;

    /**
     * Basic constructor for setting up required varaibles with initial values
     * @param game The current game manager context
     */
    protected Scene(Kroy game)
    {
        stageBatch = game.batch;
        Viewport viewport = new ScreenViewport(new OrthographicCamera());
        stage = new Stage(viewport, stageBatch);
        table.setBackground(background);
    }

    /***
     * Used to add a row to the table consisting of a single button
     * @param buttonToAdd The button that the row should contain
     */
    protected void addRow(TextButton buttonToAdd)
    {
        table.add(buttonToAdd).width(centre/3.0f);
        table.row();
    }

    /**
     * Toggles whether the menu is visible or not
     * @param state
     */
    public void visibility(boolean state){ this.table.setVisible(state);}
}
