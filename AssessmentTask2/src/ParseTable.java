import sun.reflect.generics.tree.Tree;

import java.lang.reflect.Array;
import java.util.*;
class TableIndex extends Pair<TreeNode.Label, Token.TokenType>{
    public TableIndex(TreeNode.Label row, Token.TokenType column){
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
            /*0*/add(new Rule(TreeNode.Label.prog, "" + Token.TokenType.PUBLIC + " " + Token.TokenType.CLASS + "" + Token.TokenType.ID + "{ " + Token.TokenType.PUBLIC + " static void main ( " + Token.TokenType.STRINGARR.toString() + " args ) { " + TreeNode.Label.los + " } }"));
            add(new Rule(TreeNode.Label.los, TreeNode.Label.stat + " " + TreeNode.Label.los));
            add(new Rule(TreeNode.Label.los, "epsilon"));
            add(new Rule(TreeNode.Label.stat, "" + TreeNode.Label.whilestat));
            add(new Rule(TreeNode.Label.stat, TreeNode.Label.forstat.toString()));
            /*5*/add(new Rule(TreeNode.Label.stat, "" + TreeNode.Label.ifstat));
            add(new Rule(TreeNode.Label.stat, TreeNode.Label.assign + " " + Token.TokenType.SEMICOLON.toString() + ""));
            add(new Rule(TreeNode.Label.stat, TreeNode.Label.decl + " " + Token.TokenType.SEMICOLON.toString() + ""));
            add(new Rule(TreeNode.Label.stat, TreeNode.Label.print + " " + Token.TokenType.SEMICOLON.toString() + ""));
            add(new Rule(TreeNode.Label.stat, "" + Token.TokenType.SEMICOLON.toString() + ""));
            /*10*/add(new Rule(TreeNode.Label.whilestat, "while ( " + TreeNode.Label.relexpr + " " + TreeNode.Label.boolexpr + " ) { " + TreeNode.Label.los + " }"));
            add(new Rule(TreeNode.Label.forstat, "for ( " + TreeNode.Label.forstart + " " + Token.TokenType.SEMICOLON.toString() + " " + TreeNode.Label.relexpr + " " + TreeNode.Label.boolexpr + " " + Token.TokenType.SEMICOLON.toString() + " " + TreeNode.Label.forarith + " ) { " + TreeNode.Label.los + " }"));
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
            add(new Rule(TreeNode.Label.assign, Token.TokenType.ID + " " + Token.TokenType.ASSIGN.toString() + " " + TreeNode.Label.expr));
            add(new Rule(TreeNode.Label.decl, TreeNode.Label.type + " " + Token.TokenType.ID + " " + TreeNode.Label.possassign));
            add(new Rule(TreeNode.Label.possassign, "" + Token.TokenType.ASSIGN.toString() + " " + TreeNode.Label.expr));
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
            add(new Rule(TreeNode.Label.booleq, "" + Token.TokenType.EQUAL.toString() + ""));
            add(new Rule(TreeNode.Label.booleq, "" + Token.TokenType.NEQUAL.toString() + ""));
            add(new Rule(TreeNode.Label.boollog, "" + Token.TokenType.AND.toString() + ""));
            /*40*/add(new Rule(TreeNode.Label.boollog, "" + Token.TokenType.OR.toString() + ""));
            add(new Rule(TreeNode.Label.relexpr, TreeNode.Label.arithexpr + " " + TreeNode.Label.relexprprime));
            add(new Rule(TreeNode.Label.relexpr, "true"));
            add(new Rule(TreeNode.Label.relexpr, "false"));
            add(new Rule(TreeNode.Label.relexprprime, TreeNode.Label.relop + " " + TreeNode.Label.arithexpr));
            /*45*/add(new Rule(TreeNode.Label.relexprprime, "epsilon"));
            add(new Rule(TreeNode.Label.relop, "" + Token.TokenType.LT.toString() + ""));
            add(new Rule(TreeNode.Label.relop, "" + Token.TokenType.GE.toString() + ""));
            add(new Rule(TreeNode.Label.relop, "" + Token.TokenType.GT.toString() + ""));
            add(new Rule(TreeNode.Label.relop, "" + Token.TokenType.GE.toString() + ""));
            /*50*/add(new Rule(TreeNode.Label.arithexpr, TreeNode.Label.term+ " " + TreeNode.Label.arithexprprime));
            add(new Rule(TreeNode.Label.arithexprprime, Token.TokenType.PLUS.toString() + TreeNode.Label.term+ " " + TreeNode.Label.arithexprprime));
            add(new Rule(TreeNode.Label.arithexprprime, "" + Token.TokenType.MINUS.toString() + " " + TreeNode.Label.term+ TreeNode.Label.arithexprprime));
            add(new Rule(TreeNode.Label.arithexprprime, "epsilon"));
            add(new Rule(TreeNode.Label.term, TreeNode.Label.factor + " " + TreeNode.Label.termprime));
            /*55*/add(new Rule(TreeNode.Label.termprime, Token.TokenType.TIMES.toString() + TreeNode.Label.factor + " " + TreeNode.Label.termprime));
            add(new Rule(TreeNode.Label.termprime, Token.TokenType.DIVIDE.toString() + TreeNode.Label.factor + " " + TreeNode.Label.termprime));
            add(new Rule(TreeNode.Label.termprime, Token.TokenType.MOD.toString() + TreeNode.Label.factor + " " + TreeNode.Label.termprime));
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
        addPair(new TableIndex(Rule.variables.get(26), Token.TokenType.PLUS), ruleList.get(51));
        addPair(new TableIndex(Rule.variables.get(28), Token.TokenType.PLUS), ruleList.get(58));
        addPair(new TableIndex(Rule.variables.get(26), Token.TokenType.MINUS), ruleList.get(52));
        addPair(new TableIndex(Rule.variables.get(28), Token.TokenType.MINUS), ruleList.get(58));
        addPair(new TableIndex(Rule.variables.get(28), Token.TokenType.TIMES), ruleList.get(55));
        addPair(new TableIndex(Rule.variables.get(28), Token.TokenType.DIVIDE), ruleList.get(56));
        addPair(new TableIndex(Rule.variables.get(28), Token.TokenType.MOD), ruleList.get(57));
        addPair(new TableIndex(Rule.variables.get(13), Token.TokenType.ASSIGN), ruleList.get(24));
        addPair(new TableIndex(Rule.variables.get(18), Token.TokenType.EQUAL), ruleList.get(33));
        addPair(new TableIndex(Rule.variables.get(19), Token.TokenType.EQUAL), ruleList.get(35));
        addPair(new TableIndex(Rule.variables.get(20), Token.TokenType.EQUAL), ruleList.get(37));
        addPair(new TableIndex(Rule.variables.get(23), Token.TokenType.EQUAL), ruleList.get(45));
        addPair(new TableIndex(Rule.variables.get(26), Token.TokenType.EQUAL), ruleList.get(53));
        addPair(new TableIndex(Rule.variables.get(28), Token.TokenType.EQUAL), ruleList.get(58));
        addPair(new TableIndex(Rule.variables.get(18), Token.TokenType.NEQUAL), ruleList.get(33));
        addPair(new TableIndex(Rule.variables.get(19), Token.TokenType.NEQUAL), ruleList.get(35));
        addPair(new TableIndex(Rule.variables.get(20), Token.TokenType.NEQUAL), ruleList.get(38));
        addPair(new TableIndex(Rule.variables.get(23), Token.TokenType.NEQUAL), ruleList.get(45));
        addPair(new TableIndex(Rule.variables.get(26), Token.TokenType.NEQUAL), ruleList.get(53));
        addPair(new TableIndex(Rule.variables.get(28), Token.TokenType.NEQUAL), ruleList.get(58));
        addPair(new TableIndex(Rule.variables.get(23), Token.TokenType.LT), ruleList.get(44));
        addPair(new TableIndex(Rule.variables.get(24), Token.TokenType.LT), ruleList.get(46));
        addPair(new TableIndex(Rule.variables.get(26), Token.TokenType.LT), ruleList.get(53));
        addPair(new TableIndex(Rule.variables.get(28), Token.TokenType.LT), ruleList.get(58));
        addPair(new TableIndex(Rule.variables.get(23), Token.TokenType.GT), ruleList.get(44));
        addPair(new TableIndex(Rule.variables.get(24), Token.TokenType.GT), ruleList.get(48));
        addPair(new TableIndex(Rule.variables.get(26), Token.TokenType.GT), ruleList.get(53));
        addPair(new TableIndex(Rule.variables.get(28), Token.TokenType.GT), ruleList.get(58));
        addPair(new TableIndex(Rule.variables.get(23), Token.TokenType.LE), ruleList.get(44));
        addPair(new TableIndex(Rule.variables.get(24), Token.TokenType.LE), ruleList.get(47));
        addPair(new TableIndex(Rule.variables.get(26), Token.TokenType.LE), ruleList.get(53));
        addPair(new TableIndex(Rule.variables.get(28), Token.TokenType.LE), ruleList.get(58));
        addPair(new TableIndex(Rule.variables.get(23), Token.TokenType.GE), ruleList.get(44));
        addPair(new TableIndex(Rule.variables.get(24), Token.TokenType.GE), ruleList.get(49));
        addPair(new TableIndex(Rule.variables.get(26), Token.TokenType.GE), ruleList.get(53));
        addPair(new TableIndex(Rule.variables.get(28), Token.TokenType.GE), ruleList.get(58));
        addPair(new TableIndex(Rule.variables.get(6), Token.TokenType.LPAREN), ruleList.get(14));
        addPair(new TableIndex(Rule.variables.get(16), Token.TokenType.LPAREN), ruleList.get(30));
        addPair(new TableIndex(Rule.variables.get(22), Token.TokenType.LPAREN), ruleList.get(41));
        addPair(new TableIndex(Rule.variables.get(25), Token.TokenType.LPAREN), ruleList.get(50));
        addPair(new TableIndex(Rule.variables.get(27), Token.TokenType.LPAREN), ruleList.get(54));
        addPair(new TableIndex(Rule.variables.get(29), Token.TokenType.LPAREN), ruleList.get(59));
        addPair(new TableIndex(Rule.variables.get(30), Token.TokenType.LPAREN), ruleList.get(62));
        addPair(new TableIndex(Rule.variables.get(6), Token.TokenType.RPAREN), ruleList.get(13));
        addPair(new TableIndex(Rule.variables.get(18), Token.TokenType.RPAREN), ruleList.get(34));
        addPair(new TableIndex(Rule.variables.get(26), Token.TokenType.RPAREN), ruleList.get(53));
        addPair(new TableIndex(Rule.variables.get(28), Token.TokenType.RPAREN), ruleList.get(58));
        addPair(new TableIndex(Rule.variables.get(1), Token.TokenType.RBRACE), ruleList.get(2));
        addPair(new TableIndex(Rule.variables.get(8), Token.TokenType.RBRACE), ruleList.get(18));
        addPair(new TableIndex(Rule.variables.get(10), Token.TokenType.LBRACE), ruleList.get(21));
        addPair(new TableIndex(Rule.variables.get(18), Token.TokenType.AND), ruleList.get(33));
        addPair(new TableIndex(Rule.variables.get(19), Token.TokenType.AND), ruleList.get(36));
        addPair(new TableIndex(Rule.variables.get(21), Token.TokenType.AND), ruleList.get(39));
        addPair(new TableIndex(Rule.variables.get(23), Token.TokenType.AND), ruleList.get(45));
        addPair(new TableIndex(Rule.variables.get(26), Token.TokenType.AND), ruleList.get(53));
        addPair(new TableIndex(Rule.variables.get(28), Token.TokenType.AND), ruleList.get(58));
        addPair(new TableIndex(Rule.variables.get(18), Token.TokenType.OR), ruleList.get(33));
        addPair(new TableIndex(Rule.variables.get(19), Token.TokenType.OR), ruleList.get(36));
        addPair(new TableIndex(Rule.variables.get(21), Token.TokenType.OR), ruleList.get(40));
        addPair(new TableIndex(Rule.variables.get(23), Token.TokenType.OR), ruleList.get(45));
        addPair(new TableIndex(Rule.variables.get(26), Token.TokenType.OR), ruleList.get(53));
        addPair(new TableIndex(Rule.variables.get(28), Token.TokenType.OR), ruleList.get(58));
        addPair(new TableIndex(Rule.variables.get(1), Token.TokenType.SEMICOLON), ruleList.get(1));
        addPair(new TableIndex(Rule.variables.get(2), Token.TokenType.SEMICOLON), ruleList.get(9));
        addPair(new TableIndex(Rule.variables.get(5), Token.TokenType.SEMICOLON), ruleList.get(13));
        addPair(new TableIndex(Rule.variables.get(8), Token.TokenType.SEMICOLON), ruleList.get(18));
        addPair(new TableIndex(Rule.variables.get(13), Token.TokenType.SEMICOLON), ruleList.get(25));
        addPair(new TableIndex(Rule.variables.get(18), Token.TokenType.SEMICOLON), ruleList.get(34));
        addPair(new TableIndex(Rule.variables.get(23), Token.TokenType.SEMICOLON), ruleList.get(45));
        addPair(new TableIndex(Rule.variables.get(26), Token.TokenType.SEMICOLON), ruleList.get(53));
        addPair(new TableIndex(Rule.variables.get(28), Token.TokenType.SEMICOLON), ruleList.get(58));
        addPair(new TableIndex(Rule.variables.get(0), Token.TokenType.PUBLIC), ruleList.get(0));
        addPair(new TableIndex(Rule.variables.get(1), Token.TokenType.INT), ruleList.get(1));
        addPair(new TableIndex(Rule.variables.get(2), Token.TokenType.INT), ruleList.get(7));
        addPair(new TableIndex(Rule.variables.get(5), Token.TokenType.INT), ruleList.get(12));
        addPair(new TableIndex(Rule.variables.get(8), Token.TokenType.INT), ruleList.get(18));
        addPair(new TableIndex(Rule.variables.get(12), Token.TokenType.INT), ruleList.get(23));
        addPair(new TableIndex(Rule.variables.get(15), Token.TokenType.INT), ruleList.get(27));
        addPair(new TableIndex(Rule.variables.get(1), Token.TokenType.BOOLEAN), ruleList.get(1));
        addPair(new TableIndex(Rule.variables.get(2), Token.TokenType.BOOLEAN), ruleList.get(3));
        addPair(new TableIndex(Rule.variables.get(5), Token.TokenType.BOOLEAN), ruleList.get(12));
        addPair(new TableIndex(Rule.variables.get(8), Token.TokenType.BOOLEAN), ruleList.get(18));
        addPair(new TableIndex(Rule.variables.get(12), Token.TokenType.BOOLEAN), ruleList.get(23));
        addPair(new TableIndex(Rule.variables.get(15), Token.TokenType.BOOLEAN), ruleList.get(28));
        addPair(new TableIndex(Rule.variables.get(1), Token.TokenType.CHAR), ruleList.get(1));
        addPair(new TableIndex(Rule.variables.get(2), Token.TokenType.CHAR), ruleList.get(3));
        addPair(new TableIndex(Rule.variables.get(5), Token.TokenType.CHAR), ruleList.get(12));
        addPair(new TableIndex(Rule.variables.get(8), Token.TokenType.CHAR), ruleList.get(18));
        addPair(new TableIndex(Rule.variables.get(12), Token.TokenType.CHAR), ruleList.get(23));
        addPair(new TableIndex(Rule.variables.get(15), Token.TokenType.CHAR), ruleList.get(29));
        //"System.out.println" is PRINT token
        addPair(new TableIndex(Rule.variables.get(1), Token.TokenType.PRINT), ruleList.get(1));
        addPair(new TableIndex(Rule.variables.get(2), Token.TokenType.PRINT), ruleList.get(8));
        addPair(new TableIndex(Rule.variables.get(8), Token.TokenType.PRINT), ruleList.get(18));
        addPair(new TableIndex(Rule.variables.get(14), Token.TokenType.PRINT), ruleList.get(26));
        addPair(new TableIndex(Rule.variables.get(1), Token.TokenType.WHILE), ruleList.get(1));
        addPair(new TableIndex(Rule.variables.get(2), Token.TokenType.WHILE), ruleList.get(3));
        addPair(new TableIndex(Rule.variables.get(3), Token.TokenType.WHILE), ruleList.get(10));
        addPair(new TableIndex(Rule.variables.get(8), Token.TokenType.WHILE), ruleList.get(18));
        addPair(new TableIndex(Rule.variables.get(1), Token.TokenType.FOR), ruleList.get(1));
        addPair(new TableIndex(Rule.variables.get(2), Token.TokenType.FOR), ruleList.get(4));
        addPair(new TableIndex(Rule.variables.get(4), Token.TokenType.FOR), ruleList.get(11));
        addPair(new TableIndex(Rule.variables.get(8), Token.TokenType.FOR), ruleList.get(18));
        addPair(new TableIndex(Rule.variables.get(1), Token.TokenType.IF), ruleList.get(1));
        addPair(new TableIndex(Rule.variables.get(2), Token.TokenType.IF), ruleList.get(5));
        addPair(new TableIndex(Rule.variables.get(7), Token.TokenType.IF), ruleList.get(16));
        addPair(new TableIndex(Rule.variables.get(8), Token.TokenType.IF), ruleList.get(18));
        addPair(new TableIndex(Rule.variables.get(10), Token.TokenType.IF), ruleList.get(20));
        addPair(new TableIndex(Rule.variables.get(8), Token.TokenType.ELSE), ruleList.get(17));
        addPair(new TableIndex(Rule.variables.get(9), Token.TokenType.ELSE), ruleList.get(19));
        addPair(new TableIndex(Rule.variables.get(30), Token.TokenType.DQUOTE), ruleList.get(63));
        addPair(new TableIndex(Rule.variables.get(16), Token.TokenType.SQUOTE), ruleList.get(31));
        addPair(new TableIndex(Rule.variables.get(17), Token.TokenType.SQUOTE), ruleList.get(32));
        addPair(new TableIndex(Rule.variables.get(17), Token.TokenType.SQUOTE), ruleList.get(32));
        addPair(new TableIndex(Rule.variables.get(2), Token.TokenType.ID), ruleList.get(6));
        addPair(new TableIndex(Rule.variables.get(5), Token.TokenType.ID), ruleList.get(64));
        addPair(new TableIndex(Rule.variables.get(6), Token.TokenType.ID), ruleList.get(14));
        addPair(new TableIndex(Rule.variables.get(8), Token.TokenType.ID), ruleList.get(18));
        addPair(new TableIndex(Rule.variables.get(11), Token.TokenType.ID), ruleList.get(22));
        addPair(new TableIndex(Rule.variables.get(16), Token.TokenType.ID), ruleList.get(30));
        addPair(new TableIndex(Rule.variables.get(22), Token.TokenType.ID), ruleList.get(41));
        addPair(new TableIndex(Rule.variables.get(25), Token.TokenType.ID), ruleList.get(50));
        addPair(new TableIndex(Rule.variables.get(27), Token.TokenType.ID), ruleList.get(54));
        addPair(new TableIndex(Rule.variables.get(29), Token.TokenType.ID), ruleList.get(60));
        addPair(new TableIndex(Rule.variables.get(30), Token.TokenType.ID), ruleList.get(62));
        addPair(new TableIndex(Rule.variables.get(8), Token.TokenType.TRUE), ruleList.get(18));
        addPair(new TableIndex(Rule.variables.get(16), Token.TokenType.TRUE), ruleList.get(30));
        addPair(new TableIndex(Rule.variables.get(22), Token.TokenType.TRUE), ruleList.get(42));
        addPair(new TableIndex(Rule.variables.get(30), Token.TokenType.TRUE), ruleList.get(62));
        addPair(new TableIndex(Rule.variables.get(8), Token.TokenType.FALSE), ruleList.get(18));
        addPair(new TableIndex(Rule.variables.get(16), Token.TokenType.FALSE), ruleList.get(30));
        addPair(new TableIndex(Rule.variables.get(22), Token.TokenType.FALSE), ruleList.get(43));
        addPair(new TableIndex(Rule.variables.get(30), Token.TokenType.FALSE), ruleList.get(62));
        addPair(new TableIndex(Rule.variables.get(6), Token.TokenType.NUM), ruleList.get(14));
        addPair(new TableIndex(Rule.variables.get(8), Token.TokenType.NUM), ruleList.get(18));
        addPair(new TableIndex(Rule.variables.get(16), Token.TokenType.NUM), ruleList.get(30));
        addPair(new TableIndex(Rule.variables.get(22), Token.TokenType.NUM), ruleList.get(41));
        addPair(new TableIndex(Rule.variables.get(25), Token.TokenType.NUM), ruleList.get(50));
        addPair(new TableIndex(Rule.variables.get(27), Token.TokenType.NUM), ruleList.get(54));
        addPair(new TableIndex(Rule.variables.get(29), Token.TokenType.NUM), ruleList.get(61));
        addPair(new TableIndex(Rule.variables.get(30), Token.TokenType.NUM), ruleList.get(62));
        addPair(new TableIndex(Rule.variables.get(2), Token.TokenType.epsilon), ruleList.get(3));
        addPair(new TableIndex(Rule.variables.get(28), Token.TokenType.epsilon), ruleList.get(58));
        addPair(new TableIndex(Rule.variables.get(26), Token.TokenType.epsilon), ruleList.get(53));
        addPair(new TableIndex(Rule.variables.get(23), Token.TokenType.epsilon), ruleList.get(45));
        addPair(new TableIndex(Rule.variables.get(18), Token.TokenType.epsilon), ruleList.get(34));
    }
}
