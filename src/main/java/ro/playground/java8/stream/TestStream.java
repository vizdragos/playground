package ro.playground.java8.stream;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class TestStream {

	static List<Dish> MENU = Arrays.asList(
			new Dish("pork", false, 800, Dish.Type.MEAT),
			new Dish("beef", false, 700, Dish.Type.MEAT),
			new Dish("chicken", false, 400, Dish.Type.MEAT),
			new Dish("french fries", true, 530, Dish.Type.OTHER),
			new Dish("rice", true, 350, Dish.Type.OTHER),
			new Dish("season fruit", true, 120, Dish.Type.OTHER),
			new Dish("pizza", true, 550, Dish.Type.OTHER),
			new Dish("prawns", false, 300, Dish.Type.FISH),
			new Dish("salmon", false, 450, Dish.Type.FISH));

	public static void main(String[] args) {
		//threeHighCaloricDishNames();
		//streamOfWords();
		//pairOfNumbers();
		//reverseSentenceWords();
		//checkMenu();
		//reduceNumbers();
		//numericRanges();
		//uniqueWordsInFile();
		//infiniteStreams();
		//collectorsReducing();
		//partitionPrimes(10);
		dishesByCaloricLevel();
	}

	private static void threeHighCaloricDishNames() {
		List<String> threeHighCaloricDishNames = MENU.stream()
				.filter(dish -> dish.getCalories() > 300)
				.map(Dish::getName)
				.limit(3)
				//.forEach(System.out::println);
				.collect(toList());

		System.out.println(threeHighCaloricDishNames);
	}

	private static void streamOfWords() {
		String[] arrayOfWords = {"Hello", "World"};
		Stream<String> streamOfWords = Arrays.stream(arrayOfWords);

		List<String> uniqueCharacters = streamOfWords.map(w -> w.split(""))
				// flattens multiple streams into one stream
				.flatMap(Arrays::stream)
				.distinct()
				.collect(toList());

		System.out.println(uniqueCharacters);
	}

	private static void pairOfNumbers() {
		List<Integer> firstNumbers = Arrays.asList(1, 2, 3);
		List<Integer> secondNumbers = Arrays.asList(3, 4);

		List<int[]> pairs = firstNumbers.stream()
				.flatMap(i -> secondNumbers.stream()
						.map(j -> new int[]{i, j})
				)
				.filter(pair -> (pair[0] + pair[1]) % 3 == 0) // divisible by 3
				.collect(toList());

		pairs.stream().forEach(pair -> System.out.println("(" + pair[0] + "," + pair[1] + ")"));
	}

	private static void reverseSentenceWords() {
		String sentence = "Ana  are mere";

		String reversedSentence = Arrays.stream(sentence.split(" +"))
				.map(word -> new StringBuilder(word.toLowerCase()).reverse().toString())
				.sorted(Collections.reverseOrder())
				.collect(Collectors.joining(" "));

		System.out.println(reversedSentence);
	}

	private static void checkMenu() {
		if (MENU.stream().anyMatch(Dish::isVegetarian)) {
			System.out.println("The menu is (somewhat) vegetarian friendly!!");
		}

		boolean isHealthy = MENU.stream()
				.noneMatch(d -> d.getCalories() >= 1000);

		isHealthy = MENU.stream()
				.noneMatch(d -> d.getCalories() >= 1000);
	}

	private static void reduceNumbers() {
		Integer sum = Arrays.asList(1, 2, 3).stream().reduce(0, Integer::sum);
		System.out.println(sum);

		Optional<Integer> max = Arrays.asList(1, 2, 3).stream().reduce(Integer::max);
		System.out.println(max.get());
	}

	private static void numericRanges() {
		IntStream evenNumbers = IntStream.range(1, 100)
				.filter(n -> n % 2 == 0);
		System.out.println(String.format("IntStream.range(1, 100) %s even numbers", evenNumbers.count()));

		evenNumbers = IntStream.rangeClosed(1, 100)
				.filter(n -> n % 2 == 0);
		System.out.println(String.format("IntStream.rangeClosed(1, 100) %s even numbers", evenNumbers.count()));
	}

	private static void uniqueWordsInFile() {
		long uniqueWords = 0;

//		Path currentDir = Paths.get(".");
//		System.out.println(currentDir.toAbsolutePath());
//		System.out.println(Paths.get("data.txt").toAbsolutePath());

		try (Stream<String> lines = Files.lines(Paths.get("data.txt"), Charset.defaultCharset())) {
//			uniqueWords = lines.flatMap(line -> Arrays.stream(line.split(" +")))
//					.distinct()
//					.count();

			lines.flatMap(line -> Arrays.stream(line.split(" +")))
					.distinct()
					.forEach(System.out::println);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Unique words in file - " + uniqueWords);
	}

	private static void infiniteStreams() {
		Stream.iterate(0, n -> n + 2)
				.limit(10)
				.forEach(System.out::println);

		System.out.println("\nFibonacci: ");//Fibonacci
		Stream.iterate(new int[]{0, 1}, t -> new int[]{t[1], t[0] + t[1]})
				.limit(10)
				.map(t -> t[0])
				.forEach(System.out::println);

		System.out.println("\nGenerate random:");
		Stream.generate(Math::random)
				.limit(5)
				.forEach(System.out::println);
	}

	private static void collectorsReducing() {
		int totalCalories = MENU.stream()
				.collect(reducing(0, Dish::getCalories, Integer::sum));
		System.out.println("totalCalories = " + totalCalories);

		Optional<Dish> mostCalorieDish =
				MENU.stream().collect(reducing(
						(d1, d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2));
		System.out.println("mostCalorieDish = " + mostCalorieDish);
	}

	private static boolean isPrime(int candidate) {
		int candidateRoot = (int) Math.sqrt((double) candidate);
		return IntStream.rangeClosed(2, candidateRoot)
				.noneMatch(i -> candidate % i == 0);
	}

	private static void partitionPrimes(int n) {
		Map<Boolean, List<Integer>> partitionPrimes = IntStream.rangeClosed(2, n).boxed()
				.collect(
						partitioningBy(candidate -> isPrime(candidate))
				); // Map<Boolean, List<Integer>>

		System.out.println(partitionPrimes);
	}

	private static void dishesByCaloricLevel() {
		Function<Dish, Dish.CaloricLevel> getCaloricLevel = dish -> {
			if (dish.getCalories() <= 400) return Dish.CaloricLevel.DIET;
			else if (dish.getCalories() <= 700) return

					Dish.CaloricLevel.NORMAL;
			else return Dish.CaloricLevel.FAT;
		};


		Map<Dish.CaloricLevel, List<Dish>> dishesByCaloricLevel = MENU.stream().collect(
				groupingBy(getCaloricLevel));
		System.out.println(dishesByCaloricLevel);


		Map<Dish.Type, Map<Dish.CaloricLevel, List<Dish>>> dishesByTypeAndCaloricLevel = MENU.stream().collect(
				groupingBy(Dish::getType,
						groupingBy(getCaloricLevel)
				)
		);
		System.out.println(dishesByTypeAndCaloricLevel);

	}

}
