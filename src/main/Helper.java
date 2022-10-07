package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import ga.City;
import ga.Population;
import ga.Route;

public class Helper {

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

	public static void printPopulation(Population population) {
		population.getRoutes().forEach(x -> {
			System.out.println(Arrays.toString(x.getCities().toArray()) + " | " + String.format("%.4f", x.getFitness())
					+ " | " + String.format("%.2f", x.calculateTotalDistance()));
		});
		System.out.println("");
	}

	public static void printHeading(int genNum) {
		System.out.println("> Generation # " + genNum);
		System.out.println("Route   |   Fitness   |   Distance");
	}

	public static void sortRoutesByFitness(ArrayList<Route> routes) {
		routes.sort((route1, route2) -> {
			int flag = 0;
			if (route1.getFitness() > route2.getFitness())
				flag = -1;
			else if (route1.getFitness() < route2.getFitness())
				flag = 1;
			return flag;
		});
	}
}
