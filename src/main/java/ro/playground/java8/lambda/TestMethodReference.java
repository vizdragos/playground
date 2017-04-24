package ro.playground.java8.lambda;

import java.util.Arrays;
import java.util.List;

public class TestMethodReference {

	public static void main(String[] args) {
		List<String> list = Arrays.asList("a", "b", "A", "B");
		//list.sort(String::compareToIgnoreCase);
		//list.stream().forEach(System.out::println);

		list.stream()
				.sorted(String::compareToIgnoreCase)
				.forEach(System.out::println);

		list.stream()
				.forEach(System.out::println);
	}
}
