package ro.playground.exercise;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * A new language consists of only a few brackets, you need to build a compiler for the new language and report whether the code compiles or not.
 * <p>
 * The brackets are of the following types
 * <p>
 * '{', '}'
 * '(', ')'
 * '[', ']'
 * '<', '>'
 * For example the expression "((({<()>}[])))" should compile without errors, whereas "(())[<[>]])" this is incorrect.
 * <p>
 * The bracket opened at the last should be closed first. For example in "([{}])", the opening order is '(', '[' and '{' and hence '}', ']' and ')' is the closing order.
 * <p>
 * Constraints
 * <p>
 * Lenght of the string will be less than or equal to 100,000.
 * T <= 20
 * Input Format
 * <p>
 * The first line contains T test cases, each test case consists of a string containing only the above stated 8 characters.
 * Output Format
 * <p>
 * "YES" if the expression would compile without errors, else "NO". (Quotes for clarity)
 * <p>
 * <p>Sample Input
 * <p>4
 * <p>((((((((([[[<>]]])))))))))
 * <p>[[[[]]()]]
 * <p>((({<()>}[])))
 * <p>(())[<[>]])
 * <p>
 * <p>Sample Output
 * <p>YES
 * <p>YES
 * <p>YES
 * <p>NO
 */
public class ParenthesisChecker {

	public static void main(String[] args) {
		System.out.println("check(\"((((((((([[[<>]]])))))))))\") -> " + checkParenthesis("((((((((([[[<>]]])))))))))"));
		System.out.println("check(\"[[[[]]()]]\") -> " + checkParenthesis("[[[[]]()]]"));
		System.out.println("check(\"((({<()>}[])))\") -> " + checkParenthesis("((({<()>}[])))"));
		System.out.println("check(\"(())[<[>]])\") -> " + checkParenthesis("(())[<[>]])"));
		System.out.println("check(\"a(b)\") -> " + checkParenthesis("a(b)"));
		System.out.println("check(\"[{}]\") -> " + checkParenthesis("[{}]"));
		System.out.println("check(\"[(]\") -> " + checkParenthesis("[(]"));
		System.out.println("check(\"}{\") -> " + checkParenthesis("}{"));
		System.out.println("check(\"z([{}-()]{a})\") -> " + checkParenthesis("z([{}-()]{a})"));
		System.out.println("check(\"\") -> " + checkParenthesis(""));
		System.out.println("check(\"(({[)})(])\") -> " + checkParenthesis("(({[)})(])"));
		System.out.println("check(\"(({[]})())\") -> " + checkParenthesis("(({[]})())"));
	}

	private static boolean checkParenthesis(String text) {

		List<Parenthesis> onlyParenthesises = Arrays.stream(text.split(""))
				.filter(Parenthesis::isParenthesis)
				.map(Parenthesis::getParenthesis)
				.collect(Collectors.toList());

		while (!onlyParenthesises.isEmpty()) {
			int indexOfFirstClosedParenthesis = IntStream.range(0, onlyParenthesises.size())
					.filter(index -> !onlyParenthesises.get(index).isOpened())
					.findFirst()
					.orElse(-1);

			if (indexOfFirstClosedParenthesis - 1 >= 0 &&
					onlyParenthesises.get(indexOfFirstClosedParenthesis).getPair().equals(onlyParenthesises.get(indexOfFirstClosedParenthesis - 1))) {
				onlyParenthesises.remove(indexOfFirstClosedParenthesis);
				onlyParenthesises.remove(indexOfFirstClosedParenthesis - 1);
			} else {
				return false;
			}
		}

		return true;
	}

}


enum Parenthesis {
	OPENED_PARENTHESIS("(", null, true),
	CLOSED_PARENTHESIS(")", null, false),
	OPENED_SQUARE("[", null, true),
	CLOSED_SQUARE("]", null, false),
	OPENED_CURLY("{", null, true),
	CLOSED_CURLY("}", null, false),
	OPENED_BRACKET("<", null, true),
	CLOSED_BRACKET(">", null, false);

	//hack to avoid enum forward reference on above create, where null was used
	static {
		OPENED_PARENTHESIS.pair = CLOSED_PARENTHESIS;
		CLOSED_PARENTHESIS.pair = OPENED_PARENTHESIS;
		OPENED_SQUARE.pair = CLOSED_SQUARE;
		CLOSED_SQUARE.pair = OPENED_SQUARE;
		OPENED_CURLY.pair = CLOSED_CURLY;
		CLOSED_CURLY.pair = OPENED_CURLY;
		OPENED_BRACKET.pair = CLOSED_BRACKET;
		CLOSED_BRACKET.pair = OPENED_BRACKET;
	}

	private String symbol;
	private Parenthesis pair;
	private boolean isOpened;

	Parenthesis(String symbol, Parenthesis pair, boolean isOpened) {
		this.symbol = symbol;
		this.pair = pair;
		this.isOpened = isOpened;
	}

	public String getSymbol() {
		return symbol;
	}

	public Parenthesis getPair() {
		return pair;
	}

	public boolean isOpened() {
		return isOpened;
	}

	public static Parenthesis getParenthesis(String symbol) {
		return Arrays.stream(values()).filter(value -> value.getSymbol().equals(symbol)).findFirst().get();
	}

	public static boolean isParenthesis(String symbol) {
		return Arrays.stream(values()).anyMatch(value -> value.getSymbol().equals(symbol));
	}


}