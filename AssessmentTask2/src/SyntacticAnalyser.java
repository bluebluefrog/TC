import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SyntacticAnalyser {

	private static ParseTable parseTable = new ParseTable(); // This is the parsing table you defined before

	public static ParseTree parse(List<Token> tokens) throws SyntaxException {
		int currentTokenIndex = 0;

// Create the root node of the parse tree, usually the start symbol of the program (such as 'program' or 'start')
		TreeNode rootNode = new TreeNode(TreeNode.Label.prog, null);

// Create a stack to keep track of pending nodes and symbols
		Deque<Object> parseStack = new ArrayDeque<>();
		parseStack.push(TreeNode.Label.prog); // Start pushing symbols onto the stack

		while (!parseStack.isEmpty() && currentTokenIndex < tokens.size()) {
			Object top = parseStack.peek(); // View the top element of the stack but do not remove it from the stack
			Token currentToken = tokens.get(currentTokenIndex); // Get the current token

			if (top instanceof TreeNode.Label) { // If the top of the stack is a non-terminal symbol
				TreeNode.Label label = (TreeNode.Label) top;
				Rule rule = parseTable.getRule(label, currentToken.type); // Get rules from the parsing table based on non-terminal symbols and current token

				if (rule != null) {
					parseStack.pop(); // Remove the top element from the stack
// Push the right side of the rule (that is, the symbols of the production) onto the stack in reverse order
					for (int i = rule.getProductions().length - 1; i >= 0; i--) {
						parseStack.push(rule.getProductions()[i]);
					}

//Create a new tree node according to the rules and add it to the child nodes of the current node
					TreeNode newNode = new TreeNode(label, rule);
					rootNode.addChild(newNode);
				} else {
// If no matching rule is found in the parsing table, it means that the source code violates the grammar rules and an exception is thrown.
					throw new SyntaxException("Syntax error: unexpected token " + currentToken.content + " at position " + currentTokenIndex);
				}
			} else if (top instanceof Token.TokenType) { // If the top of the stack is a terminal (token type)
				Token.TokenType tokenType = (Token.TokenType) top;
				if (currentToken.type == tokenType) { // If the current token matches the terminator at the top of the stack
					parseStack.pop(); // Remove the top element from the stack
					currentTokenIndex++; // move to next token
				} else {
// If the current token does not match, the source code violates the syntax rules and throws an exception.
					throw new SyntaxException("Syntax error: expected token " + tokenType + " but found " + currentToken.content + " at position " + currentTokenIndex);
				}
			} else {
// If the top of the stack is neither a nonterminal nor a terminal, this is an unexpected condition and an exception is thrown
				throw new IllegalStateException("Parse stack contains invalid element: " + top);
			}
		}

// If parsing is successful and all tokens have been consumed, return the parse tree
		if (parseStack.isEmpty() && currentTokenIndex == tokens.size()) {
			return new ParseTree(rootNode);
		} else {
// If the stack is not empty or there are still tokens remaining, the source code contains unparsable parts and an exception is thrown.
			throw new SyntaxException("Unexpected tokens at the end of input or syntax error.");
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
