package utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import app.exception.ResourceNotAvailableException;


public class TextFileUtility {

	public static String[] readText(String fileName)
			throws ResourceNotAvailableException {
		String[] data = null;
		List<String> dataList = new ArrayList<String>();
		File file = new File(fileName);
		Scanner sc = null;
		try {
			sc = new Scanner(file);
			while (sc.hasNextLine()) {
				dataList.add(sc.nextLine());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			sc.close();
		}
		data = (String[]) dataList.toArray();
		return data;
	}
	
}
