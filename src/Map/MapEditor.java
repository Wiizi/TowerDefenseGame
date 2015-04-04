package map;

import grid.PathTile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;

/**
 * @author		Wei Wang
 * @version		1.0
 * @since		2015-03-23
 */

public class MapEditor {

	private Map map;
	private int[][] mapArray;
	private int[][] cornerArray;

	private int width;
	private int height;
	private String userInput = "";
	private String mapInfo;
	
	private static final String folderName = "mapSaves";
	private static final File directory = new File(folderName);
	private ArrayList<String> files = new ArrayList<String>();

	public MapEditor(int width, int height, String userInput, String mapName){
		map = new Map();
		map.setMapSize(width, height);
		map.setInputCorner(userInput);

		map.initializeMap();

		Queue<PathTile> path = map.multipleCoordinatesSplit(userInput);
		map.buildPath(path);
		
		Queue<PathTile> corner = map.multipleCoordinatesSplit(userInput);
		map.cornerArray(corner);
		
		mapArray = map.convertToBinaryMap(map);
		
		try {
			writeFile(mapName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void createMap(){
		Map loadedMap = new Map();
		map.setMapSize(getWidthFromFile(), getHeightFromFile());
		map.setInputCorner(getUserInput());
		map.initializeMap();
		
		Queue<PathTile> path = map.multipleCoordinatesSplit(getUserInput());
		map.buildPath(path);
		
		Queue<PathTile> corner = map.multipleCoordinatesSplit(getUserInput());
		map.cornerArray(corner);
		
	}
	
	/**
	 * Retrieve the Map
	 * 
	 * @return	Map
	 */
	public Map getMap(){
		return map;
	}
	
	/**
	 * Write the Map information into a text file
	 * 
	 * @param map
	 * @throws IOException
	 */
	public void writeFile(String name) throws IOException{
		File file = new File(folderName + "/" + name + ".txt");
		FileOutputStream fout = new FileOutputStream(file);

		StringBuffer results = new StringBuffer();
		String data = "";
		String nextLine = System.getProperty("line.separator");

		if (!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		data+= map.getWidthOfMap() + nextLine;
		data+= map.getHeightOfMap() + nextLine;
		data+= map.getInputCorner() + nextLine;

		for (int i = 0; i < mapArray.length; ++i){
			for (int j = 0; j < mapArray[i].length; ++j){
				results.append(mapArray[i][j]).append(" ");
			}
			results.append(nextLine);
		}
		data+= results;

		try {
			fout.write(data.getBytes());
			fout.close();
			System.out.println("File Written Sucessfully!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Read and create a map from a text file
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public Map loadFile(String name) throws IOException{
		File file = new File(folderName + "/" + name + ".txt");
		FileReader fr = new FileReader(file);
		BufferedReader br =  new BufferedReader(fr);
		int count = 0;
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				count++;
				if (count == 1){
					width = Integer.valueOf(line);
				}
				else if (count == 2){
					height = Integer.valueOf(line);
				}
				else if (count == 3){
					userInput = line.toString();
				}				
				else {
					sb.append(line);
					sb.append(System.lineSeparator());
				}

				line = br.readLine();
			}
			mapInfo = sb.toString();
		} finally {
			br.close();
			createMap();
			System.out.println("File Read Sucessfully!");
		}	
		
		return map;
	}
	
	public void listFilesforFolder(){
		
		File[] listOfFiles = directory.listFiles();
		System.out.println("Entrer Folder");
		for (int i = 0; i < listOfFiles.length; i++){
			if (listOfFiles[i].isFile()) {
				String name = listOfFiles[i].getName();
				System.out.println("Get the File");
				final int lastPeriodPos = name.lastIndexOf('.');
				System.out.println(listOfFiles[i].getName().substring(0, lastPeriodPos));
				files.add(listOfFiles[i].getName().substring(0, lastPeriodPos).toString());
			} else if (listOfFiles[i].isDirectory()) {
				System.out.println("This is not a file.");
			}
		}
		
	}

	/**
	 * 
	 * @return width of customized map
	 */
	public int getWidthFromFile(){
		return width;
	}
	
	/**
	 * 
	 * @return height of customized map
	 */
	public int getHeightFromFile(){
		return height;
	}
	
	public String getUserInput(){
		return userInput;
	}
	/**
	 * 
	 * @return user's input 
	 */
	public int[][] getUserInputFromFile(){
		return map.getCornersList();
	}
	
	public ArrayList<String> getListofFiles(){
		return files;
	}
	
	/**
	 * Print
	 */
	public String toString(){
		return map.toString();
	}
}