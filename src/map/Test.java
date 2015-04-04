package map;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Test {

	public static void main(String[] args) throws IOException{
	
		MapEditor map = new MapEditor(5, 5, "(0,0) (4,0)", "Test");
		
/*		final String folderName = "mapSaves";
		final File directory = new File(folderName);
		ArrayList<String> files = new ArrayList<String>();
		
		File[] listOfFiles = directory.listFiles();
		for (int i = 0; i < listOfFiles.length; i++){
			if (listOfFiles[i].isFile()) {
				String name = listOfFiles[i].getName();
				final int lastPeriodPos = name.lastIndexOf('.');
				System.out.println(listOfFiles[i].getName().substring(0, lastPeriodPos));
				files.add(listOfFiles[i].getName().substring(0, lastPeriodPos).toString());
			} else if (listOfFiles[i].isDirectory()) {
				System.out.println("This is not a file.");
			}
		}*/

		map.listFilesforFolder();
		System.out.println(map.getListofFiles());
		
		System.out.println();
	}
}
