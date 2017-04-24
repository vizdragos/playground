package ro.playground.exercise;


import com.google.common.base.Stopwatch;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static java.util.Comparator.comparingInt;

public class LongestEvenWord {

	public static void main(String[] args) {

		String sentence = //"Write code for a great time";
				//"It is a pleasant day today";
				"You can do it the way you like";
		//"A b c d e f";

		//System.out.println("First longest even word in sentence is -> " + getLongestEvenWord(sentence));
		System.out.println("First longest even word in sentence is -> " + checkExecutionDuration(LongestEvenWord::getLongestEvenWord, sentence));

	}

	private static <T, R> R checkExecutionDuration(Function<T, R> runner, T param) {
		Stopwatch timer = Stopwatch.createStarted();
		R result = runner.apply(param);
		timer.stop();
		System.out.println(String.format("Execution time: %d %s", timer.elapsed(TimeUnit.MILLISECONDS), TimeUnit.MILLISECONDS));

		return result;
	}

	private static String getLongestEvenWord(String sentence) {
		if (sentence == null || sentence.isEmpty()) {
			return "00";
		}

		return Arrays.stream(sentence.split(" +"))
				.filter(word -> word.length() % 2 == 0)
				.sorted(comparingInt(String::length).reversed())
				.findFirst()
				.orElse("00");

		/*if (sentence != null && !sentence.isEmpty()) {
			Map<Integer, List<String>> wordsByLength = Arrays.stream(sentence.split(" +"))
					.filter(word -> word.length() % 2 == 0)
					.collect(groupingBy(String::length));

			Optional<Integer> maxLength = wordsByLength.keySet().stream().reduce(Integer::max);

			if (maxLength.isPresent()) {
				return wordsByLength.get(maxLength.get()).get(0);
			}
		}

		return "00";*/
	}
}
