package map;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Test {

	public static void main(String[] args) throws IOException{
	
		MapEditor map = new MapEditor(5, 5, "(0,0) (4,0)", "Test");

		map.listFilesforFolder();
		System.out.println(map.getListofFiles());
		
		System.out.println();
	}
}
