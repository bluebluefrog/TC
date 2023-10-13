import java.util.ArrayList;
import java.util.List;

public class ParseTree {

	private TreeNode root;

	public ParseTree() {
		this.root = null;
	}

	public ParseTree(TreeNode root) {
		this.root = root;
	}

	public TreeNode getRoot() {
		return this.root;
	}

	public void setRoot(TreeNode root) {
		this.root = root;
	}

	public ArrayList<TreeNode> getLeafNode(TreeNode node){
		List<TreeNode> children = node.getChildren();
		ArrayList<TreeNode> leafNodes = new ArrayList<TreeNode>();
		if (children.isEmpty() || children == null){
			leafNodes.add(node);
			return leafNodes;
		}
		for (int i = 0; i < children.size(); i++) {
			leafNodes.addAll(getLeafNode(children.get(i)));
		}
		return leafNodes;
	}

	private String spaces(int num) {
		String s = "";

		for (int i = 0; i < num - 1; i++)
			s += "| ";

		s += "|-";

		return s;
	}

	private String stringify(TreeNode current, int depth) {

		String s = current.toString() + "\n";
		if (current.getChildren().size() > 0) {
			for (int i = 0; i < current.getChildren().size() - 1; i++) {
				s += spaces(depth + 1) + stringify(current.getChildren().get(i), depth + 1);
			}
			s += spaces(depth + 1) + stringify(current.getChildren().get(current.getChildren().size() - 1), depth + 1);
		}

		return s;

	}

	@Override
	public String toString() {
		if (null == root)
			return "EMPTY TREE";
		return stringify(root, 0);
	}
}
