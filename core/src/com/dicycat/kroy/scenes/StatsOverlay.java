package com.dicycat.kroy.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.dicycat.kroy.entities.FireTruck;

import java.util.List;

/**
 * Adds functionality for user to click on a fireTruck and display its stats
 * Added by Septagon [ID: STATS]
 */

public class StatsOverlay
{
    private List<FireTruck> fireTrucks;
    private OrthographicCamera camera;

    private boolean onFireTruck = false;
    private FireTruck currentFireTruck;
    private float currentMouseX, currentMouseY;

    private BitmapFont font;
    private Texture backgroundTexture;

    /**
     * Constructor to set up variable values
     * @param fireTrucks The list of FireTrucks in the game
     * @param camera The game's camera
     */
    public StatsOverlay(List<FireTruck> fireTrucks, OrthographicCamera camera)
    {
        this.fireTrucks = fireTrucks;
        this.camera = camera;

        //Create the font and background image for the display
        font = new BitmapFont();
        backgroundTexture = new Texture("overlayRect.png");
    }

    /**
     * Checks to see if the mouse position is currently on one of the fireTrucks
     */
    public void checkMousePosition()
    {
        //Convert the screen coordinates of the mouse press into world coordinates
        Vector3 mouseWorldPosition = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        float mouseX = mouseWorldPosition.x;
        float mouseY = mouseWorldPosition.y;

        //Check if hovering over any of the fireTrucks, set to be displayed
        for(FireTruck f: fireTrucks)
        {
            if(mouseX >= f.getX() && mouseX <= f.getX() + f.getWidth() && mouseY >= f.getY() && mouseY <= f.getY() + f.getHeight())
            {
                currentMouseX = mouseX;
                currentMouseY = mouseY;
                onFireTruck = true;
                currentFireTruck = f;
                return;
            }
        }

        //If not hovering on any fireTruck, set onFireTruck to false
        onFireTruck = false;
    }

    /**
     * If hovering over a fireTruck, will display the stats of the fireTruck to the screen
     * @param batch The SpriteBatch that is being used to draw the game
     */
    public void render(SpriteBatch batch)
    {
        if(onFireTruck)
        {
            //Draws the black background image
            batch.draw(backgroundTexture, currentMouseX, currentMouseY);

            //Draws all of the text
            font.setColor(Color.WHITE);
            font.draw(batch, "Range: " + currentFireTruck.getRange(), currentMouseX + 10, currentMouseY + 20);
            font.draw(batch, "Speed: " + currentFireTruck.getSpeed(), currentMouseX + 10, currentMouseY + 40);
            font.draw(batch, "Damage: " + currentFireTruck.getFlowRate(), currentMouseX + 10, currentMouseY + 60);
        }
    }

    public List<FireTruck> getFireTrucks() { return fireTrucks; }
    public OrthographicCamera getCamera() { return camera; }
    public Texture getBackgroundTexture() { return backgroundTexture; }
}
