package ga;

import java.util.ArrayList;

public class GeneticAlgorithm {

	public static final double MUTATION_RATE = 0.1;
	public static final double CROSSOVER_RATE = 0.9;
	public static final int TOURNAMENT_SELECTION_SIZE = 20;
	public static final int POPULATION_SIZE = 100;
	public static final int NUMBER_OF_ELITE_ROUTES = 10;
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
		for (int i = NUMBER_OF_ELITE_ROUTES; i < crossoverPopulation.getRoutes().size(); i += 2) {
			if (Math.random() < CROSSOVER_RATE) {
				Route r1 = selectTournamentPopulation(population).getRoutes().get(0);
				Route r2 = selectTournamentPopulation(population).getRoutes().get(0);
				while (r1.equals(r2))
					r2 = selectTournamentPopulation(population).getRoutes().get(0);

				Route[] crossoverRoutes = crossoverRoute(r1, r2);

				crossoverPopulation.getRoutes().set(i, crossoverRoutes[0]);
				if (i + 1 == crossoverPopulation.getRoutes().size())
					break;
				crossoverPopulation.getRoutes().set(i + 1, crossoverRoutes[1]);
			} else {
				crossoverPopulation.getRoutes().set(i, population.getRoutes().get(i));
				if (i + 1 == crossoverPopulation.getRoutes().size())
					break;
				crossoverPopulation.getRoutes().set(i + 1, population.getRoutes().get(i + 1));
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

	private Route[] crossoverRoute(Route route1, Route route2) {
		Route[] crossoverRoutes = new Route[2];
		crossoverRoutes[0] = new Route(this);
		crossoverRoutes[1] = new Route(this);
		Route tempRoute1 = route1;
		Route tempRoute2 = route2;

		if (Math.random() > 0.5) {
			tempRoute1 = route2;
			tempRoute2 = route1;
		}

		// single point random split
		int splitPoint = (int) (Math.random() * (crossoverRoutes[0].getCities().size() - 2)) + 1;
		for (int i = 0; i < splitPoint / 2; i++) {
			crossoverRoutes[0].getCities().set(i, tempRoute1.getCities().get(i));
			crossoverRoutes[1].getCities().set(i, tempRoute2.getCities().get(i));
		}

		fillNullsInCrossoverRoute(crossoverRoutes[0], tempRoute2);
		fillNullsInCrossoverRoute(crossoverRoutes[1], tempRoute1);
		return crossoverRoutes;
	}

	private void fillNullsInCrossoverRoute(Route crossoverRoute, Route route) {
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
