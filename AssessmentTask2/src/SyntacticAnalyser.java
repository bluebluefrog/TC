import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SyntacticAnalyser {

	public static ParseTree parse(List<Token> tokens) throws SyntaxException {
	// Current token index, used to track token flow during parsing
		int currentTokenIndex = 0;

	//Create the root node of the parse tree, usually the start symbol of the program (such as 'program' or 'start')
		TreeNode rootNode = new TreeNode(TreeNode.Label.prog, null);

	// Recursively build the parse tree starting from the root node
		currentTokenIndex = parseProg(rootNode, tokens, currentTokenIndex);

	// If parsing is successful and all tokens are consumed, return the parse tree
		if (currentTokenIndex == tokens.size()) {
			return new ParseTree(rootNode);
		} else {
	// If there are remaining tokens, it means that the source code contains parts that cannot be parsed and an exception is thrown.
			throw new SyntaxException("Unexpected tokens at the end of input.");
		}
	}

	// Static method parses 'program' non-terminal symbol, implemented according to specific grammar rules
	private static int parseProg(TreeNode parentNode, List<Token> tokens, int currentTokenIndex) throws SyntaxException {
	// Get the current token
		Token currentToken = getCurrentToken(tokens, currentTokenIndex);

	// Parse based on current token and grammar rules
	// This involves multiple 'if-else' statements, checking if the current token matches a rule, and then recursively parsing the sub-rules

		return currentTokenIndex; // Return the updated token index
	}

	// Static method to get the current token, if the end of the token stream has been reached, return null
	private static Token getCurrentToken(List<Token> tokens, int currentTokenIndex) {
		if (currentTokenIndex < tokens.size()) {
			return tokens.get(currentTokenIndex);
		} else {
			return null;
		}
	}

}

// The following class may be helpful.

class Pair<A, B> {
	private final A a;
	private final B b;

	public Pair(A a, B b) {
		this.a = a;
		this.b = b;
	}

	public A fst() {
		return a;
	}

	public B snd() {
		return b;
	}

	@Override
	public int hashCode() {
		return 3 * a.hashCode() + 7 * b.hashCode();
	}

	@Override
	public String toString() {
		return "{" + a + ", " + b + "}";
	}

	@Override
	public boolean equals(Object o) {
		if ((o instanceof Pair<?, ?>)) {
			Pair<?, ?> other = (Pair<?, ?>) o;
			return other.fst().equals(a) && other.snd().equals(b);
		}

		return false;
	}

}
