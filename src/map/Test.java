package map;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Test {

	public static void main(String[] args) throws IOException{

//		LoadFile loading = new LoadFile();
//		System.out.println(loading.loadFile(loading.getListofFiles().get(0)));
//		System.out.println(loading.getAllMap());	
		
		Map map2 = new Map();
		ArrayList<Integer> points = new ArrayList<Integer> ();
//		(0,2) (3,2) (3,9) (8,9) (8,3) (11,3)
		points.add(0);
		points.add(2);
		points.add(11);
		points.add(3);
		points.add(3);
		points.add(2);
		points.add(3);
		points.add(9);
		points.add(8);
		points.add(9);
		points.add(8);
		points.add(3);
	
		System.out.println(map2.arrangePathPoint(points));
		MapEditor map3 = new MapEditor(12, 12, map2.arrangePathPoint(points));
		map3.writeFile("Test");
		System.out.println(map3);
		
		LoadFile loading = new LoadFile();
		System.out.println(loading.getAllMap());
		
	}
}
