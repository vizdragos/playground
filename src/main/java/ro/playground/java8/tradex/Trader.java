package ro.playground.java8.tradex;

public class Trader {
	private final String name;
	private final String city;

	public Trader(String name, String city) {
		this.name = name;
		this.city = city;
	}

	public String getName() {
		return this.name;
	}

	public String getCity() {
		return this.city;
	}

	public String toString() {
		return "Trader:" + this.name + " in " + this.city;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Trader trader = (Trader) o;

		if (!name.equals(trader.name)) return false;
		return city.equals(trader.city);

	}

	@Override
	public int hashCode() {
		int result = name.hashCode();
		result = 31 * result + city.hashCode();
		return result;
	}
}
