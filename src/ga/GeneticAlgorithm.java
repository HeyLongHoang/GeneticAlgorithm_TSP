package ga;

import java.util.ArrayList;

public class GeneticAlgorithm {

	public static final double MUTATION_RATE = 0.1;
	public static final double CROSSOVER_RATE = 0.9;
	public static final int TOURNAMENT_SELECTION_SIZE = 15;
	public static final int POPULATION_SIZE = 100;
	public static final int NUMBER_OF_ELITE_ROUTES = 5;
	public static final int NUMBER_OF_GENERATIONS = 500;
	private ArrayList<City> initialRoute = null;

	public GeneticAlgorithm(ArrayList<City> initialRoute) {
		this.initialRoute = initialRoute;
	}

	public ArrayList<City> getInitialRoute() {
		return initialRoute;
	}

	public Population evolve(Population population) {
		return mutatePopulation(crossoverPopulation(population));
	}

	private Population crossoverPopulation(Population population) {
		Population crossoverPopulation = new Population(population.getRoutes().size(), this);
		for (int i = 0; i < NUMBER_OF_ELITE_ROUTES; i++) {
			crossoverPopulation.getRoutes().set(i, population.getRoutes().get(i));
		}
		for (int i = NUMBER_OF_ELITE_ROUTES; i < crossoverPopulation.getRoutes().size(); i++) {
			if (Math.random() < CROSSOVER_RATE) {
				Route r1 = selectTournamentPopulation(population).getRoutes().get(0);
				Route r2 = selectTournamentPopulation(population).getRoutes().get(0);
				crossoverPopulation.getRoutes().set(i, crossoverRoute(r1, r2));
			} else {
				crossoverPopulation.getRoutes().set(i, population.getRoutes().get(i));
			}
		}
		return crossoverPopulation;
	}

	private Population mutatePopulation(Population population) {
		Population mutatePopulation = new Population(population.getRoutes().size(), this);
		for (int i = 0; i < NUMBER_OF_ELITE_ROUTES; i++)
			mutatePopulation.getRoutes().set(i, population.getRoutes().get(i));
		for (int i = NUMBER_OF_ELITE_ROUTES; i < population.getRoutes().size(); i++)
			mutatePopulation.getRoutes().set(i, mutateRoute(population.getRoutes().get(i)));
		return mutatePopulation;
	}

	private Route crossoverRoute(Route route1, Route route2) {
		Route crossoverRoute = new Route(this);
		Route tempRoute1 = route1;
		Route tempRoute2 = route2;

		if (Math.random() > 0.5) {
			tempRoute1 = route2;
			tempRoute2 = route1;
		}

		for (int i = 0; i < crossoverRoute.getCities().size() / 2; i++) {
			crossoverRoute.getCities().set(i, tempRoute1.getCities().get(i));
		}
		return fillNullsInCrossoverRoute(crossoverRoute, tempRoute2);
	}

	private Route fillNullsInCrossoverRoute(Route crossoverRoute, Route route) {
		// Fill nulls in crossoverRoute with cities from Route that are not in
		// crossoverRoute
		route.getCities().stream().filter(x -> !crossoverRoute.getCities().contains(x)).forEach(cityX -> {
			for (int y = 0; y < route.getCities().size(); y++) {
				if (crossoverRoute.getCities().get(y) == null) {
					crossoverRoute.getCities().set(y, cityX);
					break;
				}
			}
		});
		return crossoverRoute;
	}

	private Route mutateRoute(Route route) {
		for (int i = 0; i < route.getCities().size(); i++) {
			if (Math.random() < MUTATION_RATE) {
				int j = (int) (route.getCities().size() * Math.random());
				City anotherCity = route.getCities().get(j);
				route.getCities().set(j, route.getCities().get(i));
				route.getCities().set(i, anotherCity);
			}
		}
		return route;
	}

	private Population selectTournamentPopulation(Population population) {
		Population tournamentPopulation = new Population(TOURNAMENT_SELECTION_SIZE, this);
		for (int i = 0; i < TOURNAMENT_SELECTION_SIZE; i++) {
			tournamentPopulation.getRoutes().set(i,
					population.getRoutes().get((int) (Math.random() * population.getRoutes().size())));
		}
		tournamentPopulation.sortRoutesByFitness();
		return tournamentPopulation;
	}
}
