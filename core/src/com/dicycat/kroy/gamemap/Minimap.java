package com.dicycat.kroy.gamemap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.*;
import com.dicycat.kroy.Kroy;

/**
 * Minimap class that is used for displaying a minimap in the bottom left corner of the screen
 * [ID: MINIMAP]
 */

public class Minimap
{
    private OrthographicCamera minimapCamera;
    private ShapeRenderer boxRenderer;

    private FitViewport minimapViewport;

    /**
     * Initialises varaibles for the minimap
     * @param width The width of the minimap on the screen
     * @param height The height of the minimap on the screen
     * @param scale The scale of all the objects on the minimap
     * @param padding The padding around the edge of the minimap to the edge of the screen
     */
    public Minimap(int width, int height, int scale, int padding)
    {
        minimapCamera = new OrthographicCamera(Kroy.width, Kroy.height);
        minimapCamera.zoom = scale;

        boxRenderer = new ShapeRenderer();

        minimapViewport = new FitViewport(width, height, minimapCamera);
        minimapViewport.setScreenBounds(padding, padding, width, height);
    }

    /**
     * Moves the minimap's camera to a desired location
     * @param xPos The x position the camera should move to
     * @param yPos The y position the camera should move to
     */
    public void setCameraPosition(float xPos, float yPos)
    {
        minimapCamera.position.set(xPos, yPos, 0);
    }

    /**
     * Sets up the rendering context ready for objects to draw to the minimap
     * @param gameport The Viewport that the main game uses for rendering
     */
    public void startRender(Viewport gameport, SpriteBatch batch)
    {
        //Draws black box around the edge of the minimap
        gameport.apply();
        Gdx.gl.glLineWidth(3.0f);
        boxRenderer.begin(ShapeRenderer.ShapeType.Line);
        boxRenderer.setColor(Color.BLACK);
        boxRenderer.rect(minimapViewport.getScreenX()-2, minimapViewport.getScreenY()-2, minimapViewport.getScreenWidth()+5, minimapViewport.getScreenHeight()+5);
        boxRenderer.end();

        //Sets up camera and viewport ready to draw the minimap
        minimapCamera.position.set(Kroy.mainGameScreen.getPlayer().getX(), Kroy.mainGameScreen.getPlayer().getY(), 0);
        minimapViewport.apply();
        minimapCamera.update();
        batch.setProjectionMatrix(minimapCamera.combined);
        batch.begin();
    }

    /**
     * Method to finish the minimap rendering process - called when rendering finished
     */
    public OrthographicCamera getMinimapCamera() { return minimapCamera; }
    public FitViewport getMinimapViewport() { return minimapViewport; }

}
