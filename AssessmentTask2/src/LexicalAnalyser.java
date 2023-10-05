import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LexicalAnalyser {

	public static List<Token> analyse(String sourceCode) throws LexicalException {

		List<Token> output = new ArrayList<Token>();
		String spaced = "";
		String singleDelimiters = "{}()+-/%*";
		for (int i = 0; i < sourceCode.length(); ++i) {
			char c = sourceCode.charAt(i);
			if (Character.isAlphabetic(c) || Character.isDigit(c) || c == '[' || c == ']' || c == '.') {
				spaced += c;
			} else if (singleDelimiters.contains("" + c)) {
				spaced += " " + c + " ";
			} else {
				spaced += " " + c;
				switch (c) {
				case '|':
				case '&':
					if (sourceCode.charAt(i + 1) == c)
						spaced += sourceCode.charAt(++i);
					break;
				case '<':
				case '=':
				case '!':
				case '>':
					if (sourceCode.charAt(i + 1) == '=')
						spaced += sourceCode.charAt(++i);
					break;
				case '\'':
					spaced += sourceCode.charAt(++i);
					spaced += sourceCode.charAt(++i);
					break;
				case '"':
					while (i + 1 < sourceCode.length() && sourceCode.charAt(i + 1) != '"') {
						spaced += sourceCode.charAt(++i);
					}
					spaced += sourceCode.charAt(++i);
					break;
				}
				spaced += " ";
			}
		}

		String[] tokens = spaced.split("[\\s|\n|\r]+");

		for (String t : tokens) {
			if (t.length() > 0) {
				Optional<Token> token = tokenFromString(t);
				if (token.isPresent())
					output.add(token.get());
				else if (t.charAt(0) == '"') {
					if (t.length() > 1 && t.charAt(t.length() - 1) == '"') {
						String stringlit = t.substring(1, t.length() - 1);
						output.add(new Token(Token.TokenType.DQUOTE));
						output.add(new Token(Token.TokenType.STRINGLIT, stringlit));
						output.add(new Token(Token.TokenType.DQUOTE));
					} else
						throw new LexicalException("Malformed string literal: " + t);
				} else if (t.charAt(0) == '\'')
					if (t.length() == 3 && t.charAt(2) == '\'') {
						String charlit = t.substring(1, t.length() - 1);
						output.add(new Token(Token.TokenType.SQUOTE));
						output.add(new Token(Token.TokenType.CHARLIT, charlit));
						output.add(new Token(Token.TokenType.SQUOTE));
					} else
						throw new LexicalException("Malformed character literal: " + t);
				else
					throw new LexicalException("Unrecognised token: " + t);
			}
		}
		return output;

	}

	private static Optional<Token> tokenFromString(String t) {
		Optional<Token.TokenType> type = tokenTypeOf(t);
		if (type.isPresent())
			return Optional.of(new Token(type.get(), t));
		return Optional.empty();
	}

	private static Optional<Token.TokenType> tokenTypeOf(String t) {
		switch (t) {
		case "public":
			return Optional.of(Token.TokenType.PUBLIC);
		case "class":
			return Optional.of(Token.TokenType.CLASS);
		case "static":
			return Optional.of(Token.TokenType.STATIC);
		case "main":
			return Optional.of(Token.TokenType.MAIN);
		case "{":
			return Optional.of(Token.TokenType.LBRACE);
		case "void":
			return Optional.of(Token.TokenType.VOID);
		case "(":
			return Optional.of(Token.TokenType.LPAREN);
		case "String[]":
			return Optional.of(Token.TokenType.STRINGARR);
		case "args":
			return Optional.of(Token.TokenType.ARGS);
		case ")":
			return Optional.of(Token.TokenType.RPAREN);
		case "int":
		case "char":
		case "boolean":
			return Optional.of(Token.TokenType.TYPE);
		case "=":
			return Optional.of(Token.TokenType.ASSIGN);
		case ";":
			return Optional.of(Token.TokenType.SEMICOLON);
		case "if":
			return Optional.of(Token.TokenType.IF);
		case "for":
			return Optional.of(Token.TokenType.FOR);
		case "while":
			return Optional.of(Token.TokenType.WHILE);
		case "==":
			return Optional.of(Token.TokenType.EQUAL);
		case "+":
			return Optional.of(Token.TokenType.PLUS);
		case "-":
			return Optional.of(Token.TokenType.MINUS);
		case "*":
			return Optional.of(Token.TokenType.TIMES);
		case "/":
			return Optional.of(Token.TokenType.DIVIDE);
		case "%":
			return Optional.of(Token.TokenType.MOD);
		case "}":
			return Optional.of(Token.TokenType.RBRACE);
		case "else":
			return Optional.of(Token.TokenType.ELSE);
		case "System.out.println":
			return Optional.of(Token.TokenType.PRINT);
		case "||":
			return Optional.of(Token.TokenType.OR);
		case "&&":
			return Optional.of(Token.TokenType.AND);
		case "true":
			return Optional.of(Token.TokenType.TRUE);
		case "false":
			return Optional.of(Token.TokenType.FALSE);
		}

		if (t.matches("\\d+"))
			return Optional.of(Token.TokenType.NUM);
		if (Character.isAlphabetic(t.charAt(0)) && t.matches("[\\d|\\w]+")) {
			return Optional.of(Token.TokenType.ID);
		}
		return Optional.empty();
	}

}
