package ro.playground.java8.lambda;

import java.io.BufferedReader;
import java.io.IOException;

@FunctionalInterface
public interface BufferedReaderProcessor {

	String process(BufferedReader bufferedReader) throws IOException;
}
