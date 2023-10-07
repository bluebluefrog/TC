import java.util.List;

public class Runner {

	public static void main(String[] args) {
		ParseTable parseTable = new ParseTable();
		System.out.println(parseTable.getValue(new TableIndex(Rule.variables.get(6), "id")).extractRule());
		try {
			List<Token> results = LexicalAnalyser.analyse("public class foo { public static void main(String[] args){ int i = 0; if (i == 2) { i = i + 1; System.out.println(\"Hi\"); } else { i = i * 2; } } }");
			System.out.println(results);
			ParseTree tree = SyntacticAnalyser.parse(results);
			System.out.println(tree);
		} catch (LexicalException e) {
			e.printStackTrace();
		} catch (SyntaxException e) {
			e.printStackTrace();
		}

	}

}
