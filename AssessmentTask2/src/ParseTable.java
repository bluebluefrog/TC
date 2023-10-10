import sun.reflect.generics.tree.Tree;

import java.lang.reflect.Array;
import java.util.*;
class TableIndex extends Pair<TreeNode.Label, String>{
    public TableIndex(TreeNode.Label row, String column){
        super(row, column);
    }
}
//class Rule extends Pair<Token, ArrayList<Token>>{
//    /**
//     * @param left
//     * @param right
//     * For example, A -> B. A is left, B is right
//     */
//    public Rule(Token left, ArrayList<Token> right) {
//        super(left, right);
//    }
//
//    public ArrayList<Token> getRight(){
//        return snd();
//    }
//
//    public Token getLeft(){
//        return fst();
//    }
//}



class Rule{
    /**
     * Use this array to get the variable name. Recommend using this array instead of hand typing because I
     * replace the white space in name with "_"
     */
    public static ArrayList<TreeNode.Label> variables = new ArrayList<TreeNode.Label>(){
        {
            /*0*/add(TreeNode.Label.prog);
            add(TreeNode.Label.los);
            add(TreeNode.Label.stat);
            add(TreeNode.Label.whilestat);
            add(TreeNode.Label.forstat);
            /*5*/add(TreeNode.Label.forstart);
            add(TreeNode.Label.forarith);
            add(TreeNode.Label.ifstat);
            add(TreeNode.Label.elseifstat);
            add(TreeNode.Label.elseorelseif);
            /*10*/add(TreeNode.Label.possif);
            add(TreeNode.Label.assign);
            add(TreeNode.Label.decl);
            add(TreeNode.Label.possassign);
            add(TreeNode.Label.print);
            /*15*/add(TreeNode.Label.type);
            add(TreeNode.Label.expr);
            add(TreeNode.Label.charexpr);
            add(TreeNode.Label.boolexpr);
            add(TreeNode.Label.boolop);
            /*20*/add(TreeNode.Label.booleq);
            add(TreeNode.Label.boollog);
            add(TreeNode.Label.relexpr);
            add(TreeNode.Label.relexprprime);
            add(TreeNode.Label.relop);
            /*25*/add(TreeNode.Label.arithexpr);
            add(TreeNode.Label.arithexprprime);
            add(TreeNode.Label.term);
            add(TreeNode.Label.termprime);
            add(TreeNode.Label.factor);
            /*30*/add(TreeNode.Label.printexpr);
        }
    };

    public TreeNode.Label variable;
    public String rule;

    public Rule(TreeNode.Label variable, String rule) {
        this.variable = variable;
        this.rule = rule;
    }

    /**
     * Use this to get the string from the right hand side of the rule
     * @return
     */
    public ArrayList<String> extractRule(){
        return new ArrayList<>(Arrays.asList(rule.split(" ")));
    }

    public static boolean isVariable(String s){
        return variables.contains(s);
    }
}

class Terminal{
    public String value;

}

/**
 * HOW TO USE THE PARSE TABLE
 * - Just initialize with no para constructor
 * - Use getValue() which take a TableIndex as parameter. Return a Rule object
 * - Rule.extractRule() will return the right hand side of the rule in an array.
 * - Example in the Runner class
 */
public class ParseTable extends HashTable<TableIndex, Rule> {
    /**
     * Don't need to care about this one
     */
    public static ArrayList<Rule> ruleList = new ArrayList<Rule>(){
        {
            /*0*/add(new Rule(TreeNode.Label.prog, "public class" + Token.TokenType.ID + "{ public static void main ( String[] args ) { " + TreeNode.Label.los + " } }"));
            add(new Rule(TreeNode.Label.los, TreeNode.Label.stat + " " + TreeNode.Label.los));
            add(new Rule(TreeNode.Label.los, "epsilon"));
            add(new Rule(TreeNode.Label.stat, "" + TreeNode.Label.whilestat));
            add(new Rule(TreeNode.Label.stat, TreeNode.Label.forstat.toString()));
            /*5*/add(new Rule(TreeNode.Label.stat, "" + TreeNode.Label.ifstat));
            add(new Rule(TreeNode.Label.stat, TreeNode.Label.assign + " ;"));
            add(new Rule(TreeNode.Label.stat, TreeNode.Label.decl + " ;"));
            add(new Rule(TreeNode.Label.stat, TreeNode.Label.print + " ;"));
            add(new Rule(TreeNode.Label.stat, ";"));
            /*10*/add(new Rule(TreeNode.Label.whilestat, "while ( " + TreeNode.Label.relexpr + " " + TreeNode.Label.boolexpr + " ) { " + TreeNode.Label.los + " }"));
            add(new Rule(TreeNode.Label.forstat, "for ( " + TreeNode.Label.forstart + " ; " + TreeNode.Label.relexpr + " " + TreeNode.Label.boolexpr + " ; " + TreeNode.Label.forarith + " ) { " + TreeNode.Label.los + " }"));
            add(new Rule(TreeNode.Label.forstart, "" + TreeNode.Label.decl));
            add(new Rule(TreeNode.Label.forstart, "epsilon"));
            add(new Rule(TreeNode.Label.forarith, "" + TreeNode.Label.arithexpr));
            /*15*/add(new Rule(TreeNode.Label.forarith, "epsilon"));
            add(new Rule(TreeNode.Label.ifstat, "if ( " + TreeNode.Label.relexpr + " " + TreeNode.Label.boolexpr + " ) { " + TreeNode.Label.los + " } " + TreeNode.Label.elseifstat));
            add(new Rule(TreeNode.Label.elseifstat, TreeNode.Label.elseorelseif + " { " + TreeNode.Label.los + " } " + TreeNode.Label.elseifstat));
            add(new Rule(TreeNode.Label.elseifstat, "epsilon"));
            add(new Rule(TreeNode.Label.elseorelseif, "else " + TreeNode.Label.possif));
            /*20*/add(new Rule(TreeNode.Label.possif, "if ( " + TreeNode.Label.relexpr + " " + TreeNode.Label.boolexpr + " )"));
            add(new Rule(TreeNode.Label.possif, "epsilon"));
            add(new Rule(TreeNode.Label.assign, Token.TokenType.ID + " = " + TreeNode.Label.expr));
            add(new Rule(TreeNode.Label.decl, TreeNode.Label.type + " " + Token.TokenType.ID + " " + TreeNode.Label.possassign));
            add(new Rule(TreeNode.Label.possassign, "= " + TreeNode.Label.expr));
            /*25*/add(new Rule(TreeNode.Label.possassign, "epsilon"));
            add(new Rule(TreeNode.Label.print, "System.out.println ( " + TreeNode.Label.printexpr + " )"));
            add(new Rule(TreeNode.Label.type, "int"));
            add(new Rule(TreeNode.Label.type, "boolean "));
            add(new Rule(TreeNode.Label.type, "char"));
            /*30*/add(new Rule(TreeNode.Label.expr, TreeNode.Label.relexpr + " " + TreeNode.Label.boolexpr));
            add(new Rule(TreeNode.Label.expr, "" + TreeNode.Label.charexpr));
            add(new Rule(TreeNode.Label.charexpr, "' " + Token.TokenType.CHARLIT + " '"));
            add(new Rule(TreeNode.Label.boolexpr, TreeNode.Label.boolop + " " + TreeNode.Label.relexpr + " " + TreeNode.Label.boolexpr));
            add(new Rule(TreeNode.Label.boolexpr, "epsilon"));
            /*35*/add(new Rule(TreeNode.Label.boolop, "" + TreeNode.Label.booleq));
            add(new Rule(TreeNode.Label.boolop, "" + TreeNode.Label.boollog));
            add(new Rule(TreeNode.Label.booleq, "=="));
            add(new Rule(TreeNode.Label.booleq, "!="));
            add(new Rule(TreeNode.Label.boollog, "&&"));
            /*40*/add(new Rule(TreeNode.Label.boollog, "||"));
            add(new Rule(TreeNode.Label.relexpr, TreeNode.Label.arithexpr + " " + TreeNode.Label.relexprprime));
            add(new Rule(TreeNode.Label.relexpr, "true"));
            add(new Rule(TreeNode.Label.relexpr, "false"));
            add(new Rule(TreeNode.Label.relexprprime, TreeNode.Label.relop + " " + TreeNode.Label.arithexpr));
            /*45*/add(new Rule(TreeNode.Label.relexprprime, "epsilon"));
            add(new Rule(TreeNode.Label.relop, "<"));
            add(new Rule(TreeNode.Label.relop, "<="));
            add(new Rule(TreeNode.Label.relop, ">"));
            add(new Rule(TreeNode.Label.relop, ">="));
            /*50*/add(new Rule(TreeNode.Label.arithexpr, TreeNode.Label.term+ " " + TreeNode.Label.arithexprprime));
            add(new Rule(TreeNode.Label.arithexprprime, "+ " + TreeNode.Label.term+ " " + TreeNode.Label.arithexprprime));
            add(new Rule(TreeNode.Label.arithexprprime, "- " + TreeNode.Label.term+ TreeNode.Label.arithexprprime));
            add(new Rule(TreeNode.Label.arithexprprime, "epsilon"));
            add(new Rule(TreeNode.Label.term, TreeNode.Label.factor + " " + TreeNode.Label.termprime));
            /*55*/add(new Rule(TreeNode.Label.termprime, "* " + TreeNode.Label.factor + " " + TreeNode.Label.termprime));
            add(new Rule(TreeNode.Label.termprime, "/ " + TreeNode.Label.factor + " " + TreeNode.Label.termprime));
            add(new Rule(TreeNode.Label.termprime, "% " + TreeNode.Label.factor + " " + TreeNode.Label.termprime));
            add(new Rule(TreeNode.Label.termprime, "epsilon"));
            add(new Rule(TreeNode.Label.factor, "( " + TreeNode.Label.arithexpr + " )"));
            /*60*/add(new Rule(TreeNode.Label.factor, "" + Token.TokenType.ID));
            add(new Rule(TreeNode.Label.factor, "" + Token.TokenType.NUM));
            add(new Rule(TreeNode.Label.printexpr, TreeNode.Label.relexpr + " " + TreeNode.Label.boolexpr));
            add(new Rule(TreeNode.Label.printexpr, "\"" + Token.TokenType.STRINGLIT + " \""));
            add(new Rule(TreeNode.Label.forstart, "" + TreeNode.Label.assign));
        }
    };

    public ParseTable() {
        addPair(new TableIndex(Rule.variables.get(26), "+"), ruleList.get(51));
        addPair(new TableIndex(Rule.variables.get(28), "+"), ruleList.get(58));
        addPair(new TableIndex(Rule.variables.get(26), "-"), ruleList.get(52));
        addPair(new TableIndex(Rule.variables.get(28), "-"), ruleList.get(58));
        addPair(new TableIndex(Rule.variables.get(28), "*"), ruleList.get(55));
        addPair(new TableIndex(Rule.variables.get(28), "/"), ruleList.get(56));
        addPair(new TableIndex(Rule.variables.get(28), "%"), ruleList.get(57));
        addPair(new TableIndex(Rule.variables.get(13), "="), ruleList.get(24));
        addPair(new TableIndex(Rule.variables.get(18), "=="), ruleList.get(33));
        addPair(new TableIndex(Rule.variables.get(19), "=="), ruleList.get(35));
        addPair(new TableIndex(Rule.variables.get(20), "=="), ruleList.get(37));
        addPair(new TableIndex(Rule.variables.get(23), "=="), ruleList.get(45));
        addPair(new TableIndex(Rule.variables.get(26), "=="), ruleList.get(53));
        addPair(new TableIndex(Rule.variables.get(28), "=="), ruleList.get(58));
        addPair(new TableIndex(Rule.variables.get(18), "!="), ruleList.get(33));
        addPair(new TableIndex(Rule.variables.get(19), "!="), ruleList.get(35));
        addPair(new TableIndex(Rule.variables.get(20), "!="), ruleList.get(38));
        addPair(new TableIndex(Rule.variables.get(23), "!="), ruleList.get(45));
        addPair(new TableIndex(Rule.variables.get(26), "!="), ruleList.get(53));
        addPair(new TableIndex(Rule.variables.get(28), "!="), ruleList.get(58));
        addPair(new TableIndex(Rule.variables.get(23), "<"), ruleList.get(44));
        addPair(new TableIndex(Rule.variables.get(24), "<"), ruleList.get(46));
        addPair(new TableIndex(Rule.variables.get(26), "<"), ruleList.get(53));
        addPair(new TableIndex(Rule.variables.get(28), "<"), ruleList.get(58));
        addPair(new TableIndex(Rule.variables.get(23), ">"), ruleList.get(44));
        addPair(new TableIndex(Rule.variables.get(24), ">"), ruleList.get(48));
        addPair(new TableIndex(Rule.variables.get(26), ">"), ruleList.get(53));
        addPair(new TableIndex(Rule.variables.get(28), ">"), ruleList.get(58));
        addPair(new TableIndex(Rule.variables.get(23), ">="), ruleList.get(44));
        addPair(new TableIndex(Rule.variables.get(24), ">="), ruleList.get(49));
        addPair(new TableIndex(Rule.variables.get(26), ">="), ruleList.get(53));
        addPair(new TableIndex(Rule.variables.get(28), ">="), ruleList.get(58));
        addPair(new TableIndex(Rule.variables.get(6), "("), ruleList.get(14));
        addPair(new TableIndex(Rule.variables.get(16), "("), ruleList.get(30));
        addPair(new TableIndex(Rule.variables.get(22), "("), ruleList.get(41));
        addPair(new TableIndex(Rule.variables.get(25), "("), ruleList.get(50));
        addPair(new TableIndex(Rule.variables.get(27), "("), ruleList.get(54));
        addPair(new TableIndex(Rule.variables.get(29), "("), ruleList.get(59));
        addPair(new TableIndex(Rule.variables.get(30), "("), ruleList.get(62));
        addPair(new TableIndex(Rule.variables.get(6), ")"), ruleList.get(13));
        addPair(new TableIndex(Rule.variables.get(18), ")"), ruleList.get(34));
        addPair(new TableIndex(Rule.variables.get(26), ")"), ruleList.get(53));
        addPair(new TableIndex(Rule.variables.get(28), ")"), ruleList.get(58));
        addPair(new TableIndex(Rule.variables.get(1), "}"), ruleList.get(2));
        addPair(new TableIndex(Rule.variables.get(8), "}"), ruleList.get(18));
        addPair(new TableIndex(Rule.variables.get(10), "{"), ruleList.get(21));
        addPair(new TableIndex(Rule.variables.get(18), "&&"), ruleList.get(33));
        addPair(new TableIndex(Rule.variables.get(19), "&&"), ruleList.get(36));
        addPair(new TableIndex(Rule.variables.get(21), "&&"), ruleList.get(39));
        addPair(new TableIndex(Rule.variables.get(23), "&&"), ruleList.get(45));
        addPair(new TableIndex(Rule.variables.get(26), "&&"), ruleList.get(53));
        addPair(new TableIndex(Rule.variables.get(28), "&&"), ruleList.get(58));
        addPair(new TableIndex(Rule.variables.get(18), "||"), ruleList.get(33));
        addPair(new TableIndex(Rule.variables.get(19), "||"), ruleList.get(36));
        addPair(new TableIndex(Rule.variables.get(21), "||"), ruleList.get(40));
        addPair(new TableIndex(Rule.variables.get(23), "||"), ruleList.get(45));
        addPair(new TableIndex(Rule.variables.get(26), "||"), ruleList.get(53));
        addPair(new TableIndex(Rule.variables.get(28), "||"), ruleList.get(58));
        addPair(new TableIndex(Rule.variables.get(1), ";"), ruleList.get(1));
        addPair(new TableIndex(Rule.variables.get(2), ";"), ruleList.get(9));
        addPair(new TableIndex(Rule.variables.get(5), ";"), ruleList.get(13));
        addPair(new TableIndex(Rule.variables.get(8), ";"), ruleList.get(18));
        addPair(new TableIndex(Rule.variables.get(13), ";"), ruleList.get(25));
        addPair(new TableIndex(Rule.variables.get(18), ";"), ruleList.get(34));
        addPair(new TableIndex(Rule.variables.get(23), ";"), ruleList.get(45));
        addPair(new TableIndex(Rule.variables.get(26), ";"), ruleList.get(53));
        addPair(new TableIndex(Rule.variables.get(28), ";"), ruleList.get(58));
        addPair(new TableIndex(Rule.variables.get(0), "public"), ruleList.get(0));
        addPair(new TableIndex(Rule.variables.get(1), "int"), ruleList.get(1));
        addPair(new TableIndex(Rule.variables.get(2), "int"), ruleList.get(7));
        addPair(new TableIndex(Rule.variables.get(5), "int"), ruleList.get(12));
        addPair(new TableIndex(Rule.variables.get(8), "int"), ruleList.get(18));
        addPair(new TableIndex(Rule.variables.get(12), "int"), ruleList.get(23));
        addPair(new TableIndex(Rule.variables.get(15), "int"), ruleList.get(27));
        addPair(new TableIndex(Rule.variables.get(1), "boolean"), ruleList.get(1));
        addPair(new TableIndex(Rule.variables.get(2), "boolean"), ruleList.get(3));
        addPair(new TableIndex(Rule.variables.get(5), "boolean"), ruleList.get(12));
        addPair(new TableIndex(Rule.variables.get(8), "boolean"), ruleList.get(18));
        addPair(new TableIndex(Rule.variables.get(12), "boolean"), ruleList.get(23));
        addPair(new TableIndex(Rule.variables.get(15), "boolean"), ruleList.get(28));
        addPair(new TableIndex(Rule.variables.get(1), "char"), ruleList.get(1));
        addPair(new TableIndex(Rule.variables.get(2), "char"), ruleList.get(3));
        addPair(new TableIndex(Rule.variables.get(5), "char"), ruleList.get(12));
        addPair(new TableIndex(Rule.variables.get(8), "char"), ruleList.get(18));
        addPair(new TableIndex(Rule.variables.get(12), "char"), ruleList.get(23));
        addPair(new TableIndex(Rule.variables.get(15), "char"), ruleList.get(29));
        addPair(new TableIndex(Rule.variables.get(1), "System.out.println"), ruleList.get(1));
        addPair(new TableIndex(Rule.variables.get(2), "System.out.println"), ruleList.get(8));
        addPair(new TableIndex(Rule.variables.get(8), "System.out.println"), ruleList.get(18));
        addPair(new TableIndex(Rule.variables.get(14), "System.out.println"), ruleList.get(26));
        addPair(new TableIndex(Rule.variables.get(1), "while"), ruleList.get(1));
        addPair(new TableIndex(Rule.variables.get(2), "while"), ruleList.get(3));
        addPair(new TableIndex(Rule.variables.get(3), "while"), ruleList.get(10));
        addPair(new TableIndex(Rule.variables.get(8), "while"), ruleList.get(18));
        addPair(new TableIndex(Rule.variables.get(1), "for"), ruleList.get(1));
        addPair(new TableIndex(Rule.variables.get(2), "for"), ruleList.get(4));
        addPair(new TableIndex(Rule.variables.get(4), "for"), ruleList.get(11));
        addPair(new TableIndex(Rule.variables.get(8), "for"), ruleList.get(18));
        addPair(new TableIndex(Rule.variables.get(1), "if"), ruleList.get(1));
        addPair(new TableIndex(Rule.variables.get(2), "if"), ruleList.get(5));
        addPair(new TableIndex(Rule.variables.get(7), "if"), ruleList.get(16));
        addPair(new TableIndex(Rule.variables.get(8), "if"), ruleList.get(18));
        addPair(new TableIndex(Rule.variables.get(10), "if"), ruleList.get(20));
        addPair(new TableIndex(Rule.variables.get(8), "else"), ruleList.get(17));
        addPair(new TableIndex(Rule.variables.get(9), "else"), ruleList.get(19));
        addPair(new TableIndex(Rule.variables.get(30), "\""), ruleList.get(63));
        addPair(new TableIndex(Rule.variables.get(16), "'"), ruleList.get(31));
        addPair(new TableIndex(Rule.variables.get(17), "'"), ruleList.get(32));
        addPair(new TableIndex(Rule.variables.get(17), "'"), ruleList.get(32));
        addPair(new TableIndex(Rule.variables.get(2), "id"), ruleList.get(6));
        addPair(new TableIndex(Rule.variables.get(5), "id"), ruleList.get(64));
        addPair(new TableIndex(Rule.variables.get(6), "id"), ruleList.get(14));
        addPair(new TableIndex(Rule.variables.get(8), "id"), ruleList.get(18));
        addPair(new TableIndex(Rule.variables.get(11), "id"), ruleList.get(22));
        addPair(new TableIndex(Rule.variables.get(16), "id"), ruleList.get(30));
        addPair(new TableIndex(Rule.variables.get(22), "id"), ruleList.get(41));
        addPair(new TableIndex(Rule.variables.get(25), "id"), ruleList.get(50));
        addPair(new TableIndex(Rule.variables.get(27), "id"), ruleList.get(54));
        addPair(new TableIndex(Rule.variables.get(29), "id"), ruleList.get(60));
        addPair(new TableIndex(Rule.variables.get(30), "id"), ruleList.get(62));
        addPair(new TableIndex(Rule.variables.get(8), "true"), ruleList.get(18));
        addPair(new TableIndex(Rule.variables.get(16), "true"), ruleList.get(30));
        addPair(new TableIndex(Rule.variables.get(22), "true"), ruleList.get(42));
        addPair(new TableIndex(Rule.variables.get(30), "true"), ruleList.get(62));
        addPair(new TableIndex(Rule.variables.get(8), "false"), ruleList.get(18));
        addPair(new TableIndex(Rule.variables.get(16), "false"), ruleList.get(30));
        addPair(new TableIndex(Rule.variables.get(22), "false"), ruleList.get(43));
        addPair(new TableIndex(Rule.variables.get(30), "false"), ruleList.get(62));
        addPair(new TableIndex(Rule.variables.get(6), "num"), ruleList.get(14));
        addPair(new TableIndex(Rule.variables.get(8), "num"), ruleList.get(18));
        addPair(new TableIndex(Rule.variables.get(16), "num"), ruleList.get(30));
        addPair(new TableIndex(Rule.variables.get(22), "num"), ruleList.get(41));
        addPair(new TableIndex(Rule.variables.get(25), "num"), ruleList.get(50));
        addPair(new TableIndex(Rule.variables.get(27), "num"), ruleList.get(54));
        addPair(new TableIndex(Rule.variables.get(29), "num"), ruleList.get(61));
        addPair(new TableIndex(Rule.variables.get(30), "num"), ruleList.get(62));
    }
}
