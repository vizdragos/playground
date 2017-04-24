package ro.playground.java8.lambda;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TestLambda {

	public static void main(String[] args) throws IOException {
		System.out.println(processFile((BufferedReader br) -> br.readLine()));
	}

	public static String processFile(BufferedReaderProcessor processor) throws IOException {
		try (BufferedReader bufferedReader = new BufferedReader((new FileReader("data.txt")))) {
			return processor.process(bufferedReader);
		}
	}

//	public static String processFile(Function<BufferedReader, String> processor) throws IOException {
//		try (BufferedReader bufferedReader = new BufferedReader((new FileReader("data.txt")))) {
//			return processor.apply(bufferedReader);
//		}
//	}

}
