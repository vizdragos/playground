package ro.playground.exercise;

import com.google.common.base.Stopwatch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;
import java.util.stream.IntStream;


/**
 * Anagram Difference
 * <p>
 * We define an anagram to be a word whose characters can be rearranged to create another word. Given two strings, we want to know the minimum number of characters already in either string that we must modify to make the two strings anagrams; if it’s not possible to make the two strings anagrams, we consider this number to be -1. For example:
 * <p>
 * tea and ate are anagrams, so we would need to modify a minimum of 0 characters.
 * tea and toe are not anagrams, but we can modify a minimum of 1 character in either string to make them anagrams.
 * act and acts are not anagrams and cannot be converted to anagrams because they contain different numbers of characters, so the minimum number of characters to modify is -1.
 * Complete the function from the provided code. It has two parameters:
 * <p>
 * An array of n strings, a.
 * An array of n strings, b.
 * The function must return an array of integers where each element i denotes the minimum number of characters you must modify to make ai and bi anagrams; if it’s not possible to modify the existing characters in a[i] and b[i] to make them anagrams, element i should be -1 instead.
 * <p>
 * Note: You can only modify existing characters in the strings, you cannot delete or append characters to change a string’s length.
 * <p>
 * Input Format
 * <p>
 * The provided code reads the following input from stdin and passes it to the function:
 * <p>
 * The first line contains an integer, n, denoting the number of elements in a.
 * <p>
 * Each line i of the n subsequent lines contains an integer describing a[i].
 * <p>
 * The next line contains an integer, n, denoting the number of elements in b.
 * <p>
 * Each line i of the n subsequent lines contains an integer describing b[i].
 * <p>
 * Constraints
 * <p>
 * Each string consists of lowercase English alphabetic letters (i.e., a to z).
 * 1 ≤ n ≤ 100
 * It is guaranteed that a and b contain the same number of elements.
 * 0 ≤ length of a[i], length of b[i] ≤ 10^4
 * 1 ≤ length of a[i] + length of b[i] ≤ 10^4
 * Output Format
 * <p>
 * The function must return an array of integers where each element i denotes the minimum number of characters you must modify to make a[i] and b[i] anagrams; if it’s not possible to modify the existing characters in a[i] and b[i] to make them anagrams, element i should be -1 instead. This is printed to stdout by the provided code.
 * <p>
 * Sample Input 0
 * <p>
 * 5
 * a
 * jk
 * abb
 * mn
 * abc
 * 5
 * bb
 * kj
 * bbc
 * op
 * def
 * Sample Output 0
 * <p>
 * -1
 * 0
 * 1
 * 2
 * 3
 * Explanation 0
 * <p>
 * Given a = [a, jk, abb, mn, abc] and b = [bb, kj, bbc, op, def], we perform the following n = 5 calculations:
 * <p>
 * Index 0: a and bb cannot be anagrams because they contain different numbers of characters, so we return -1 at this index.
 * Index 1: jk and kj are already anagrams because they both contain the same characters at the same frequencies, so we return 0 at this index.
 * Index 2: abb and bbc differ by a minimum of one character, so we return 1 at this index.
 * Index 3: mn and op differ by a minimum of two characters, so we return 2 at this index.
 * Index 4: abc and def differ by a minimum of three characters, so we return 3 at this index.
 * After checking each pair of strings, we return the array [-1, 0, 1, 2, 3] as our answer.
 */
public class AnagramDifference {

	public static void main(String[] args) {

		String[] firstAnagrams = {"a", "jk", "abb", "mn", "abc"};
		String[] secondAnagrams = {"bb", "kj", "bbc", "op", "def"};

		/*String[] firstAnagrams, secondAnagrams;
		try (Scanner scanner = new Scanner(System.in)) {
			firstAnagrams = readAnagramArray(scanner);
			secondAnagrams = readAnagramArray(scanner);

			Arrays.stream(firstAnagrams).forEach(System.out::println);
			Arrays.stream(secondAnagrams).forEach(System.out::println);
		}*/

		Arrays.stream(checkExecutionDuration(AnagramDifference::checkAnagramsImperative, firstAnagrams, secondAnagrams))
				.forEach(System.out::println); // THIS IS FASTER !

		Arrays.stream(checkExecutionDuration(AnagramDifference::checkAnagramsDeclarative, firstAnagrams, secondAnagrams))
				.forEach(System.out::println);

	}

	private static String[] readAnagramArray(Scanner scanner) {
		System.out.println("Array size:");
		Integer size = Integer.valueOf(scanner.next());
		String[] anagrams = new String[size];
		for (int i = 0; i < size; i++) {
			System.out.println(String.format("a[%d]=", i));
			anagrams[i] = scanner.next();
		}

		return anagrams;
	}

	private static <T, U, R> R checkExecutionDuration(BiFunction<T, U, R> runner, T firstParam, U secondParam) {
		Stopwatch timer = Stopwatch.createStarted();
		R result = runner.apply(firstParam, secondParam);
		timer.stop();
		System.out.println(String.format("Execution time: %d %s", timer.elapsed(TimeUnit.MILLISECONDS), TimeUnit.MILLISECONDS));

		return result;
	}

	private static int[] checkAnagramsImperative(String[] firstAnagrams, String[] secondAnagrams) {
		int[] anagramsResolution = new int[firstAnagrams.length];
		for (int i = 0; i < firstAnagrams.length; i++) {
			if (firstAnagrams[i].length() != secondAnagrams[i].length()) {
				anagramsResolution[i] = -1;
			} else {
				List<String> anagramsList = new ArrayList<>(Arrays.asList(firstAnagrams[i].split("")));
				anagramsList.removeAll(Arrays.asList(secondAnagrams[i].split("")));
				anagramsResolution[i] = anagramsList.size();
			}
		}

		return anagramsResolution;
	}

	private static int[] checkAnagramsDeclarative(String[] firstAnagrams, String[] secondAnagrams) {
		return IntStream.range(0, firstAnagrams.length)
				.map(
						index -> {
							if (firstAnagrams[index].length() != secondAnagrams[index].length()) {
								return -1;
							} else {
								return Long.valueOf(Arrays.stream(firstAnagrams[index].split(""))
										.filter(c -> !secondAnagrams[index].contains(c))
										.count())
										.intValue();
							}
						}
				)
				.toArray();
	}

}