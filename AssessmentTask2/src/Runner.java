import java.util.List;

public class Runner {

	public static void main(String[] args) {
//		System.out.println(Token.TokenType.MOD.toString());
		ParseTable parseTable = new ParseTable();
		System.out.println(parseTable.getValue(new TableIndex(Rule.variables.get(0), Token.TokenType.PUBLIC)).extractRule());
		System.out.println(parseTable.getValue(new TableIndex(Rule.variables.get(11), Token.TokenType.ID)).extractRule());
		System.out.println("Try hash token");
		System.out.println();
		//Test parse tree
		System.out.println("Parse tree test");
		TreeNode root = new TreeNode(TreeNode.Label.prog, null);
		TreeNode child1 = new TreeNode(TreeNode.Label.ifstat, root);
		TreeNode child2 = new TreeNode(TreeNode.Label.whilestat, root);

		TreeNode child21 = new TreeNode(TreeNode.Label.arithexpr, child1);
		TreeNode child22 = new TreeNode(TreeNode.Label.expr, child1);
		TreeNode child31 = new TreeNode(TreeNode.Label.booleq, child22);
		ParseTree testParseTree = new ParseTree();

		testParseTree.setRoot(root);
		root.addChild(child1);
		root.addChild(child2);
		child1.addChild(child21);
		child1.addChild(child22);
		child21.addChild(child31);

		System.out.println(testParseTree);
		System.out.println(testParseTree.getLeafNode(root));

		System.out.println("Parse tree test");

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
