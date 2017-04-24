package ro.playground.java8.tradex;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TradingExercise {

	public static void main(String[] args) {

		Trader raoul = new Trader("Raoul", "Cambridge");
		Trader mario = new Trader("Mario", "Milan");
		Trader alan = new Trader("Alan", "Cambridge");
		Trader brian = new Trader("Brian", "Cambridge");

		List<Transaction> transactions = Arrays.asList(
				new Transaction(brian, 2011, 300),
				new Transaction(raoul, 2012, 1000),
				new Transaction(raoul, 2011, 400),
				new Transaction(mario, 2012, 710),
				new Transaction(mario, 2012, 700),
				new Transaction(alan, 2012, 950)
		);

		System.out.println("Transactions by year: " + findTxByYear(transactions, 2011));
		System.out.println("Unique Cities of Traders: " + uniqueCitiesOfTraders(Arrays.asList(raoul, mario, alan, brian)));
		System.out.println("findTradersByCityAndSortByName(): " + findTradersByCityAndSortByName(Arrays.asList(raoul, mario, alan, brian), "Cambridge"));
		System.out.println("Any Traders in Milan ? " + areTradersInCity(Arrays.asList(raoul, mario, alan, brian), "Milan"));
		System.out.println("findTxOfTradersLivingInCity() " + findTxOfTradersLivingInCity(transactions, Arrays.asList(raoul, mario, alan, brian), "Milan"));
	}

	private static List<Transaction> findTxByYear(List<Transaction> transactions, int year) {
		return transactions.stream()
				.filter(tx -> tx.getYear() == year)
				.sorted(Comparator.comparing(Transaction::getValue))
				.collect(Collectors.toList());
	}

	private static Set<String> uniqueCitiesOfTraders(List<Trader> traders) {
		return traders.stream()
				.map(Trader::getCity)
				//.distinct()
				.collect(Collectors.toSet());
	}

	private static List<Trader> findTradersByCityAndSortByName(List<Trader> traders, String city) {
		return traders.stream()
				.filter(trader -> trader.getCity().equals(city))
				.sorted(Comparator.comparing(Trader::getName))
				.collect(Collectors.toList());
	}

	private static boolean areTradersInCity(List<Trader> traders, String city) {
		return traders.stream()
				.anyMatch(trader -> trader.getCity().equals(city));
	}

	private static List<Transaction> findTxOfTradersLivingInCity(List<Transaction> transactions, List<Trader> traders, String city) {
		return transactions.stream()
				.filter(tx -> tx.getTrader().getCity().equalsIgnoreCase(city))
				.collect(Collectors.toList());
	}

}
