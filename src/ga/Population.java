package ga;

import java.util.ArrayList;

public class Population {

	private ArrayList<Route> routes = new ArrayList<Route>(GeneticAlgorithm.POPULATION_SIZE);

	public Population(int populationSize, ArrayList<City> cities) {
		for (int i = 0; i < populationSize; i++) {
			routes.add(new Route(cities));
		}
	}

	public Population(int populationSize, GeneticAlgorithm ga) {
		for (int i = 0; i < populationSize; i++) {
			routes.add(new Route(ga.getInitialRoute()));
		}
	}

	public ArrayList<Route> getRoutes() {
		return routes;
	}

	public void sortRoutesByFitness() {
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
