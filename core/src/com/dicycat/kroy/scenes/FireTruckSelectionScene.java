package com.dicycat.kroy.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dicycat.kroy.Kroy;

/**
 * Window for selecting FireTruck type
 * 
 * @author Luke Taylor
 *
 */
public class FireTruckSelectionScene extends Scene {
    
    //Buttons initialised, labelled and stylised
    public TextButton truckButton1 = new TextButton("Speed", skin);
    public TextButton truckButton2 = new TextButton("Speed + Damage", skin); 
    public TextButton truckButton3 = new TextButton("Damage", skin);
    public TextButton truckButton4 = new TextButton("Capacity + Range", skin); 
    public TextButton truckButton5 = new TextButton("Capacity", skin);
    public TextButton truckButton6 = new TextButton("Range", skin);

	/**
	 * @param game		Kroy instance
	 */
	public FireTruckSelectionScene(Kroy game) {
		super(game);
		
		// Images + Buttons added to the screen
		table.add(new Image(new Texture("fireTruck1.png")));
		table.add(new Image(new Texture("fireTruck2.png")));
		table.add(new Image(new Texture("fireTruck3.png")));
		table.row();
		table.add(truckButton1).width(centre/3.0f);
	    table.add(truckButton2).width(centre/3.0f);
	    table.add(truckButton3).width(centre/3.0f);
		
	    table.row();

		table.add(new Image(new Texture("fireTruck4.png")));
		table.add(new Image(new Texture("fireTruck5.png")));
		table.add(new Image(new Texture("fireTruck6.png")));
	    table.row();
	    table.add(truckButton4).width(centre/3.0f);
	    table.add(truckButton5).width(centre/3.0f);
	    table.add(truckButton6).width(centre/3.0f);
	    
		table.setFillParent(true);
	    stage.addActor(table);
	}
}
