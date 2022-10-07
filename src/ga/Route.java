package ga;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Route {

	private boolean isFitnessChanged = true;
	private double fitness = 0;
	private ArrayList<City> cities = new ArrayList<City>();

	public Route(ArrayList<City> cities) {
		this.cities.addAll(cities);
		Collections.shuffle(this.cities);
	}

	public Route(GeneticAlgorithm ga) {
		ga.getInitialRoute().forEach(x -> cities.add(null));
	}

	public double getFitness() {
		if (isFitnessChanged) {
			fitness = 1. / calculateTotalDistance() * 10000;
			isFitnessChanged = false;
		}
		return fitness;
	}

	public double calculateTotalDistance() {
		int citiesSize = cities.size();
		double distanceSum = 0.;
		for (int i = 1; i < citiesSize; i++) {
			distanceSum += cities.get(i - 1).measureDistance(cities.get(i));
		}
		distanceSum += cities.get(citiesSize - 1).measureDistance(cities.get(0));
		return distanceSum;
	}

	public ArrayList<City> getCities() {
		// it is possible for fitness to be changed when this function is called
		isFitnessChanged = true;
		return cities;
	}

	@Override
	public String toString() {
		return Arrays.toString(cities.toArray());
	}

	@Override
	public boolean equals(Object route) {
		if (!(route instanceof Route))
			return false;
		if (this.getCities().size() != ((Route) route).getCities().size())
			return false;
		for (int i = 0; i < this.getCities().size(); i++) {
			City otherCity = ((Route) route).getCities().get(i);
			if (!this.getCities().get(i).equals(otherCity))
				return false;
		}
		return true;
	}
}
