package com.dicycat.kroy.gamemap;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.dicycat.kroy.Kroy;

/**
 * 
 * 
 * @author Martha Cartwright
 *
 */

public class TiledGameMap{
	
	private TiledMap tiledMap;
	private OrthogonalTiledMapRenderer tiledMapRenderer;
	
	/**
	 * 
	 */
	public TiledGameMap(Kroy game) {
		
		tiledMap = new TmxMapLoader().load("YorkMapOriginal.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, game.batch);
	}


	/** 
	 * Renders the road layer of the map
	 * @param camera Active camera
	 */
	public void renderRoads(OrthographicCamera camera) {
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render(new int[] {1,2});
	}


	/**
	 * Renders the building layer and the window layer of the map
	 * @param camera Active camera
	 */
	public void renderBuildings(OrthographicCamera camera) {
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render(new int[] {3,4});
	}

	public void update(float delta) {}

	/**
	 * Gets the tile type at a row/column for a particular layer
	 * @param layer Layer to check
	 * @param col Column where tile is located
	 * @param row Row where tile is located
	 * @return tileType The type of tile at the given location of the given layer
	 */
	public TileType getTileTypeByCoordinate(int layer, int col, int row) {
		Cell cell = ((TiledMapTileLayer) tiledMap.getLayers().get(layer)).getCell(col,row);
		if (cell != null) {
			TiledMapTile tile = cell.getTile();
			
			if (tile != null) {
				int id = tile.getId();
				return TileType.getTileTypeByID(id);
			}
		}
		return null;
	}
	

	/**
	 * Gets the tile type at a pixel position for a particular layer
	 * @param layer Layer to check
	 * @param x X position
	 * @param y Y position
	 * @return tileType The type of tile at the given location of the given layer
	 */
	public TileType getTileTypeByLocation(int layer, float x, float y) {
		return this.getTileTypeByCoordinate(layer, (int)(x/TileType.TILE_SIZE), (int)(y/TileType.TILE_SIZE));
	}


	/**
	 * Gets the width of the map in tiles
	 * @return width Map tile width
	 */
	public int getWidth() {
		return ((TiledMapTileLayer) tiledMap.getLayers().get(0)).getWidth();
	}


	/**
	 * Gets the height of the map in tiles
	 * @return height Map tile height
	 */
	public int getHeight() {
		return ((TiledMapTileLayer) tiledMap.getLayers().get(0)).getHeight();
	}

	public TiledMap getTiledMap() { return tiledMap; }
	public OrthogonalTiledMapRenderer getTiledMapRenderer() { return tiledMapRenderer; }
	
}
