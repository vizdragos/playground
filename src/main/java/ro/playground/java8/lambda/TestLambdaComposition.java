package ro.playground.java8.lambda;

import java.util.function.Consumer;
import java.util.function.Function;

public class TestLambdaComposition {

	public static void main(String[] args) {
		Function<String, String> addHeader = Letter::addHeader;
		Function<String, String> transformationPipeline = addHeader
				.andThen(Letter::checkSpelling)
				.andThen(Letter::addFooter);

		Consumer<String> printer = System.out::println;

		printer.accept(transformationPipeline.apply("This is some awesome labda test !"));
	}
}

interface Letter {
	static String addHeader(String text) {
		return "From Raoul, Mario and Alan: " + text;
	}

	static String addFooter(String text) {
		return text + " Kind regards";
	}

	static String checkSpelling(String text) {
		return text.replaceAll("labda", "lambda");
	}
}
