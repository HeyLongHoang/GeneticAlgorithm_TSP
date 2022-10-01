package main;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import ga.City;
import ga.GeneticAlgorithm;
import ga.Population;
import ga.Route;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		ArrayList<Route> routes = new ArrayList<Route>();
		String filepath = "data/eil51.tsp.txt";
		int maxSeed = 30;

		for (int s = 0; s < maxSeed; s++) {
			routes.add(runGeneticAlgorithm(s, filepath));
		}
		sortRoutesByFitness(routes);

		Route bestRoute = routes.get(0);
		System.out.println(
				"------------------------------------------------------------------------------------------------------------------");
		System.out.println("Best Route over 30 attempts: " + bestRoute);
		System.out.println("With fitness: " + String.format("%.4f", bestRoute.getFitness()) + " and total distance: "
				+ String.format("%.2f", bestRoute.calculateTotalDistance()));

		double avgFitness = 0.;
		double avgTotalDistance = 0.;

		for (Route route : routes) {
			avgFitness += route.getFitness();
			avgTotalDistance += route.calculateTotalDistance();
		}
		avgFitness /= maxSeed;
		avgTotalDistance /= maxSeed;
		System.out.println("Average fitness: " + String.format("%.4f", avgFitness) + "\nAverage total distance: "
				+ String.format("%.2f", avgTotalDistance));
	}

	public static Route runGeneticAlgorithm(int seedValue, String filepath) throws FileNotFoundException {
		Random r = new Random();
		r.setSeed(seedValue);

		ArrayList<City> cities = Problem.load_input_data(filepath);
		Population population = new Population(GeneticAlgorithm.POPULATION_SIZE, cities);

		population.sortRoutesByFitness();
		GeneticAlgorithm ga = new GeneticAlgorithm(cities);

		int genNum = 0;
//		printHeading(genNum);
//		printPopulation(population);
		genNum++;

		while (genNum < GeneticAlgorithm.NUMBER_OF_GENERATIONS) {
			population = ga.evolve(population);
			population.sortRoutesByFitness();
//			printHeading(genNum);
//			printPopulation(population);
			genNum++;
		}

		Route bestRoute = population.getRoutes().get(0);
		System.out.println("Current best route: " + bestRoute);
		System.out.println("With fitness: " + String.format("%.4f", bestRoute.getFitness()) + " and total distance: "
				+ String.format("%.2f", bestRoute.calculateTotalDistance()));
		return bestRoute;
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
