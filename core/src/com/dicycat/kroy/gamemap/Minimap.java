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

public class Minimap extends Viewport
{
    private OrthographicCamera minimapCamera;
    private SpriteBatch minimapBatch;
    private ShapeRenderer boxRenderer;

    private FitViewport minimapViewport;

    public Minimap(int width, int height, int scale, int padding)
    {
        minimapCamera = new OrthographicCamera(Kroy.width, Kroy.height);
        minimapCamera.zoom = scale;

        minimapBatch = new SpriteBatch();
        boxRenderer = new ShapeRenderer();

        minimapViewport = new FitViewport(width, height, minimapCamera);
        minimapViewport.setScreenBounds(padding, padding, width, height);
    }

    public void setCameraPosition(float xPos, float yPos)
    {
        minimapCamera.position.set(xPos, yPos, 0);
    }

    public void startRender(Viewport gameport)
    {
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
        minimapBatch.setProjectionMatrix(minimapCamera.combined);
        minimapBatch.begin();
    }

    public void endRender()
    {
        minimapBatch.end();
    }

    public SpriteBatch getMinimapBatch() { return minimapBatch; }
    public OrthographicCamera getMinimapCamera() { return minimapCamera; }
    public FitViewport getMinimapViewport() { return minimapViewport; }

}
