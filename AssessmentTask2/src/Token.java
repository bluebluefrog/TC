import java.util.Optional;

public class Token {

	public enum TokenType implements Symbol {
		PLUS, MINUS, TIMES, DIVIDE, MOD, ASSIGN, EQUAL, NEQUAL, LT, LE, GT, GE, LPAREN, RPAREN, LBRACE, RBRACE, AND, OR,
		SEMICOLON, PUBLIC, CLASS, STATIC, VOID, MAIN, STRINGARR, ARGS, TYPE, PRINT, WHILE, FOR, IF, ELSE, DQUOTE,
		SQUOTE, ID, NUM, CHARLIT, TRUE, FALSE, STRINGLIT;

		@Override
		public boolean isVariable() {
			return false;
		}

	};

	enum VariableType implements Symbol{
		PROG, LOS, STAT, WHILE, FOR, FOR_START, FOR_ARITH, IF, ELSE_IF, ELSE_QMARK_IF, POSS_ASSIGN, PRINT, TYPE,
		EXPR, CHAR_EXPR, BOOL_EXPR, BOOL_OP, BOOL_EQ, BOOL_LOG, REL_EXPR, REL_EXPR_PRIME, REL_OP, ARITH_EXPR,
		ARITH_EXPR_PRIME, TERM, TERM_PRIME, FACTOR, PRINT_EXPR;

		@Override
		public boolean isVariable() {
			return true;
		}
	};

	private Symbol type;
	private Optional<String> value;

	public Token(Symbol type) {
		this.type = type;
		this.value = Optional.empty();
	}

	public Token(Symbol type, String value) {
		this.type = type;
		this.value = Optional.of(value);
	}

	public Optional<String> getValue() {
		return this.value;
	}

	public Symbol getType() {
		return this.type;
	}

	@Override
	public String toString() {
		if (type instanceof TokenType){
			switch ((TokenType)type) {
				case ID :
				case NUM :
				case CHARLIT :
				case TYPE :
				case STRINGLIT : return "[" + type + ": " + value + "]";
				default : return "[" + type + "]";
			}
		} else if (type instanceof VariableType) {
			return "<<" + type + ">>";
		}
		return "<<" + value + ">>";
	}

	@Override
	public boolean equals(Object other) {
		if (other == null) return false;
		if (!Token.class.isAssignableFrom(other.getClass())) return false;

		Token t = (Token) other;

		if (t.type != this.type) return false;

		if (t.value == null || this.value == null) return t.value == this.value;

		return t.value.equals(this.value);
	}

}
