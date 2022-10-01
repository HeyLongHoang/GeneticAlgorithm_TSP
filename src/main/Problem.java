package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import ga.City;

public class Problem {

	public static ArrayList<City> load_input_data(String filepath) throws FileNotFoundException {
		ArrayList<City> cities = new ArrayList<City>();
		Scanner scanner = new Scanner(new File(filepath));

		// scan the first 6 lines in file
		for (int i = 0; i < 6; i++)
			scanner.nextLine();

		String str;
		while (scanner.hasNext()) {
			str = scanner.nextLine();
			if (str.equals("EOF"))
				break;
//			System.out.println(str);
			String[] numbers = str.split(" ");
			cities.add(
					new City(Integer.parseInt(numbers[0]), Integer.parseInt(numbers[1]), Integer.parseInt(numbers[2])));
		}
		return cities;
	}

}
