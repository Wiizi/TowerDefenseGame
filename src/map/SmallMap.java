package map;

import grid.PathTile;

import java.util.Queue;

public class SmallMap extends Map {
	
	private static final int width = 10;
	private static final int height = 8;
	private static final String userInput = "(0,2) (4,2) (4,5) (8,5) (8,3) (9,3)";

	public SmallMap() {
		super();
		super.setMapSize(width, height);
		super.setInputCorner(userInput);

		super.initializeMap();

		Queue<PathTile> path = super.multipleCoordinatesSplit(userInput);
		super.buildPath(path);
		
		Queue<PathTile> corner = super.multipleCoordinatesSplit(userInput);
		super.cornerArray(corner);
		
		super.convertToBinaryMap(this);
	}
}
