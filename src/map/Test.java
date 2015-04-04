package map;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Test {

	public static void main(String[] args) throws IOException{

		LoadFile loading = new LoadFile();
		System.out.println(loading.loadFile(loading.getListofFiles().get(0)));
		System.out.println(loading.getAllMap());
	}
}
