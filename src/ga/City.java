package ga;

public class City {

	private int name;
	private int latitude;
	private int longitude;

	public City(int name, int latitude, int longitude) {
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public int getName() {
		return name;
	}

	public int getLatitude() {
		return latitude;
	}

	public int getLongitude() {
		return longitude;
	}

	@Override
	public String toString() {
		return Integer.toString(name);
	}

	public double measureDistance(City otherCity) {
		double dLatitude = this.getLatitude() - otherCity.getLatitude();
		double dLongitude = this.getLongitude() - otherCity.getLongitude();
		return Math.sqrt(Math.pow(dLatitude, 2) + Math.pow(dLongitude, 2));
	}
}
