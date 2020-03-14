package com.dicycat.kroy.debug;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.Kroy;

import java.util.ArrayList;
import java.util.List;

public class DebugRenderer
{
    private List<DebugDraw> debugObjects; //List of debug items

    public DebugRenderer()
    {
        debugObjects = new ArrayList<DebugDraw>();
    }

    /**
     * Draws all debug objects for one frame
     */
    public void DrawDebug(OrthographicCamera gamecam) {
        for (DebugDraw dObject : debugObjects) {
            dObject.Draw(gamecam.combined);
        }
        debugObjects.clear();
    }

    /**
     * Draw a debug line
     * @param start Start of the line
     * @param end End of the line
     * @param lineWidth Width of the line
     * @param colour Colour of the line
     */
    public void DrawLine(Vector2 start, Vector2 end, int lineWidth, Color colour) {
        if (Kroy.debug) {
            debugObjects.add(new DebugLine(start, end, lineWidth, colour));
        }
    }

    /**
     * Draw a debug circle (outline)
     * @param position Centre of the circle
     * @param radius Radius of the circle
     * @param lineWidth Width of the outline
     * @param colour Colour of the line
     */
    public void DrawCircle(Vector2 position, float radius, int lineWidth, Color colour) {
        if (Kroy.debug) {
            debugObjects.add(new DebugCircle(position, radius, lineWidth, colour));
        }
    }

    /**
     * Draw a debug rectangle (outline)
     * @param bottomLefiretrucks Bottom lefiretrucks point of the rectangle
     * @param dimensions Dimensions of the rectangle (Width, Length)
     * @param lineWidth Width of the outline
     * @param colour Colour of the line
     */
    public void DrawRect(Vector2 bottomLefiretrucks, Vector2 dimensions, int lineWidth, Color colour) {
        if (Kroy.debug) {
            debugObjects.add(new DebugRect(bottomLefiretrucks, dimensions, lineWidth, colour));
        }
    }
}
