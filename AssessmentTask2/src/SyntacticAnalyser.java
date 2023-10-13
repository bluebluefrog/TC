import java.util.*;

public class SyntacticAnalyser {

	public static ParseTree parse(List<Token> tokens) throws SyntaxException {
		//Turn the List of Tokens into a ParseTree.

		//Handle the empty token list error
		if (tokens.size() == 0){
			throw new SyntaxException("");
		}

		//Initialize the tree
		TreeNode root = new TreeNode(TreeNode.Label.prog, null);
		ParseTree parseTree = new ParseTree(root);
		ParseTable parseTable = new ParseTable();

		//Used as an index of the leafNodes array
		//In this implementation, instead of using a stack, we use an array and an index to
		//manage the stack. So the leafNodes are nodes which are at the very bottom of the tree - the lowest
		//level of the tree. We use array to manage it.
		//For the token list, instead of using a stack, we use for loop and decrease the i variable when
		//we don't want the stack to pop.
		int leafNodeCounter = 0;
		int failCount = 0;
		for (int i = 0; i < tokens.size(); i++) {
			//Get the leafNodes
			ArrayList<TreeNode> leafNodes = new ArrayList<TreeNode>();
			leafNodes = parseTree.getLeafNode(root);

			//Initialize the index of the parsing table which consist of a variable and a terminal
			TableIndex index = new TableIndex(leafNodes.get(leafNodeCounter).getLabel(), tokens.get(i).getType());;
			//Preprocess
			//Because we built the terminal set from reserved word set of the simple java programme,
			//So instead of TYPE from the token list, we have it as INT, CHAR and BOOLEAN
			//So then, before doing anything, we need to convert the TokenType to INT, CHAR, BOOLEAN and then
			//after doing all the step, we will convert it back to TYPE. This sounds redundant, but we can't risk making
			//changes to the parsing table.
			if (tokens.get(i).getType().equals(Token.TokenType.TYPE)){
				if (tokens.get(i).getValue().get().equals("int")){
					index = new TableIndex(leafNodes.get(leafNodeCounter).getLabel(), Token.TokenType.INT);
				} else if (tokens.get(i).getValue().get().equals("boolean")) {
					index = new TableIndex(leafNodes.get(leafNodeCounter).getLabel(), Token.TokenType.BOOLEAN);
				} else if (tokens.get(i).getValue().get().equals("char")) {
					index = new TableIndex(leafNodes.get(leafNodeCounter).getLabel(), Token.TokenType.CHAR);
				}
			}else {
				index = new TableIndex(leafNodes.get(leafNodeCounter).getLabel(), tokens.get(i).getType());
			}
			//refresh the leafNodes
			TreeNode currentNode = leafNodes.get(leafNodeCounter);

			//Process
			Rule rule = parseTable.getValue(index);
			//If rule is found.
			if (rule != null){
				failCount = 0;
				ArrayList<Symbol> ruleRightHandSide = rule.extractRule();
				//Add node to tree
				//Add all tokens and labels from the rule to tree
				for (int j = 0; j < ruleRightHandSide.size(); j++) {
					if (ruleRightHandSide.get(j).isVariable()){
						leafNodes.get(leafNodeCounter).addChild(new TreeNode((TreeNode.Label) ruleRightHandSide.get(j),currentNode ));
					}
					else if (!ruleRightHandSide.get(j).isVariable()){
						Token token = new Token((Token.TokenType) ruleRightHandSide.get(j));
						switch (token.getType()){
							case INT:
							case BOOLEAN:
							case CHAR : token = new Token(Token.TokenType.TYPE, ruleRightHandSide.get(j).toString().toLowerCase());
						}
						leafNodes.get(leafNodeCounter).addChild(new TreeNode(TreeNode.Label.terminal, token,currentNode ));
					}
				}
				//Refresh the leafNodes array after add nodes to tree
				leafNodes = parseTree.getLeafNode(root);
				if (leafNodes.get(leafNodeCounter).getToken().isPresent()){
					//Skip episilon from the leafNodes array
					if (leafNodes.get(leafNodeCounter).getLabel().equals(TreeNode.Label.epsilon)){
						leafNodeCounter++;
					}

					//If the current leaf node equal to the current token, move the pointer which is the
					//leafNodeCounter index - increase it.
					if (leafNodes.get(leafNodeCounter).getToken().get().getType().equals(tokens.get(i).getType())){
						if (tokens.get(i).getValue().isPresent()){
							leafNodes.get(leafNodeCounter).getToken().get().setValue(tokens.get(i).getValue().get());
						}

						leafNodeCounter++;
						continue;
					}
					//This if to handle the different in test case and the programme
					//We can't use int, char and boolean as enum in java programme because they are reserved words.
					//Instead, we use INT, CHAR, BOOLEAN
					//So we need to compare them using equalIgnoreCase
					if (leafNodes.get(leafNodeCounter).getToken().get().getType().toString().equalsIgnoreCase(tokens.get(i).getValue().get().toString())){
						System.out.println("come to 80");
						if (tokens.get(i).getValue().isPresent()){
							leafNodes.get(leafNodeCounter).getToken().get().setValue(tokens.get(i).getValue().get());
						}
						leafNodeCounter++;
					}
				}else{
					//if the current leaf node is a variable which mean we need more node to reach the current token
					//then we skip to the next for loop to find it on the
					//parsing table because we find the rule at the beginning of the for loop.
					if (leafNodes.get(leafNodeCounter).getLabel().equals(TreeNode.Label.epsilon)){
						leafNodeCounter++;
					}
					//Instead of a stack, we just decrease the i, so it won't change in the next loop.
					//Work the same as stopping the stack to pop
					i--;
				}
			}else {
				// this else case is when we can't find the current leaf node on the parsing table.
				//check if it's a terminal and compare it with the tokens list
				if (leafNodes.get(leafNodeCounter).getToken().isPresent()){
					if (leafNodes.get(leafNodeCounter).getToken().get().getType().equals(tokens.get(i).getType())){
						if (tokens.get(i).getValue().isPresent()){
							leafNodes.get(leafNodeCounter).getToken().get().setValue(tokens.get(i).getValue().get());
						}
						leafNodeCounter++;
						continue;
					}
				}else {
					//if not give it another chance and do the loop again
					i--;
					if (failCount > 1){
						System.out.println("Wrong");
						throw new SyntaxException("");
					}
					failCount++;
					continue;
				}

				//Throw error if it's not a terminal
				System.out.println("Wrong");
				throw new SyntaxException("");
			}
		}
		return parseTree;
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
