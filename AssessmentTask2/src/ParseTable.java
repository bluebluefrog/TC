import java.lang.reflect.Array;
import java.util.*;
class TableIndex extends Pair<String, String>{
    public TableIndex(String row, String column){
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
    public static ArrayList<String> variables = new ArrayList<String>(){
        {
            /*0*/add("<<prog>>");
            add("<<los>>");
            add("<<stat>>");
            add("<<while>>");
            add("<<for>>");
            /*5*/add("<<for_start>>");
            add("<<for_arith>>");
            add("<<if>>");
            add("<<else_if>>");
            add("<<else?if>>");
            /*10*/add("<<poss_if>>");
            add("<<assign>>");
            add("<<decl>>");
            add("<<poss_assign>>");
            add("<<print>>");
            /*15*/add("<<type>>");
            add("<<expr>>");
            add("<<char_expr>>");
            add("<<bool_expr>>");
            add("<<bool_op>>");
            /*20*/add("<<bool_eq>>");
            add("<<bool_log>>");
            add("<<rel_expr>>");
            add("<<rel_expr'>>");
            add("<<rel_op>>");
            /*25*/add("<<arith_expr>>");
            add("<<arith_expr'>>");
            add("<<term>>");
            add("<<term'>>");
            add("<<factor>>");
            /*30*/add("<<print_expr>>");
        }
    };

    public String variable;
    public String rule;

    public Rule(String variable, String rule) {
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
            /*0*/add(new Rule("<<prog>>", "public class <<ID>> { public static void main ( String[] args ) { <<los>> } }"));
            add(new Rule("<<los>>", "<<stat>> <<los>>"));
            add(new Rule("<<los>>", "epsilon"));
            add(new Rule("<<stat>>", "<<while>>"));
            add(new Rule("<<stat>>", "<<for>>"));
            /*5*/add(new Rule("<<stat>>", "<<if>>"));
            add(new Rule("<<stat>>", "<<assign>> ;"));
            add(new Rule("<<stat>>", "<<decl>> ;"));
            add(new Rule("<<stat>>", "<<print>> ;"));
            add(new Rule("<<stat>>", ";"));
            /*10*/add(new Rule("<<while>>", "while ( <<rel_expr>> <<bool_expr>> ) { <<los>> }"));
            add(new Rule("<<for>>", "for ( <<for_start>> ; <<rel_expr>> <<bool_expr>> ; <<for_arith>> ) { <<los>> }"));
            add(new Rule("<<for_start>>", "<<decl>>"));
            add(new Rule("<<for_start>>", "epsilon"));
            add(new Rule("<<for_arith>>", "<<arith_expr>>"));
            /*15*/add(new Rule("<<for_arith>>", "epsilon"));
            add(new Rule("<<if>>", "if ( <<rel_expr>> <<bool_expr>> ) { <<los>> } <<else_if>>"));
            add(new Rule("<<else_if>>", "<<else?if>> { <<los>> } <<else_if>>"));
            add(new Rule("<<else_if>>", "epsilon"));
            add(new Rule("<<else?if>>", "else <<poss_if>>"));
            /*20*/add(new Rule("<<poss_if>>", "if ( <<rel_expr>> <<bool_expr>> )"));
            add(new Rule("<<poss_if>>", "epsilon"));
            add(new Rule("<<assign>>", "<<ID>> = <<expr>>"));
            add(new Rule("<<decl>>", "<<type>> <<ID>> <<poss_assign>>"));
            add(new Rule("<<poss_assign>>", "= <<expr>>"));
            /*25*/add(new Rule("<<poss_assign>>", "epsilon"));
            add(new Rule("<<print>>", "System.out.println ( <<print_expr>> )"));
            add(new Rule("<<type>>", "int"));
            add(new Rule("<<type>>", "boolean "));
            add(new Rule("<<type>>", "char"));
            /*30*/add(new Rule("<<expr>>", "<<rel_expr>> <<bool_expr>>"));
            add(new Rule("<<expr>>", "<<char expr>>"));
            add(new Rule("<<char_expr>>", "' <<char>> '"));
            add(new Rule("<<bool_expr>>", "<<bool_op>> <<rel_expr>> <<bool_expr>>"));
            add(new Rule("<<bool_expr>>", "epsilon"));
            /*35*/add(new Rule("<<bool_op>>", "<<bool_eq>>"));
            add(new Rule("<<bool_op>>", "<<bool_log>>"));
            add(new Rule("<<bool_eq>>", "=="));
            add(new Rule("<<bool_eq>>", "!="));
            add(new Rule("<<bool_log>>", "&&"));
            /*40*/add(new Rule("<<bool_log>>", "||"));
            add(new Rule("<<rel_expr>>", "<<arith_expr>> <<rel_expr'>>"));
            add(new Rule("<<rel_expr>>", "true"));
            add(new Rule("<<rel_expr>>", "false"));
            add(new Rule("<<rel_expr'>>", "<<rel_op>> <<arith_expr>>"));
            /*45*/add(new Rule("<<rel_expr'>>", "epsilon"));
            add(new Rule("<<rel_op>>", "<"));
            add(new Rule("<<rel_op>>", "<="));
            add(new Rule("<<rel_op>>", ">"));
            add(new Rule("<<rel_op>>", ">="));
            /*50*/add(new Rule("<<arith_expr>>", "<<term>> <<arith_expr'>>"));
            add(new Rule("<<arith_expr'>>", "+ <<term>> <<arith_expr'>>"));
            add(new Rule("<<arith_expr'>>", "- <<term>> <<arith_expr'>>"));
            add(new Rule("<<arith_expr'>>", "epsilon"));
            add(new Rule("<<term>>", "<<factor>> <<term'>>"));
            /*55*/add(new Rule("<<term'>>", "* <<factor>> <<term'>>"));
            add(new Rule("<<term'>>", "/ <<factor>> <<term'>>"));
            add(new Rule("<<term'>>", "% <<factor>> <<term'>>"));
            add(new Rule("<<term'>>", "epsilon"));
            add(new Rule("<<factor>>", "( <<arith_expr>> )"));
            /*60*/add(new Rule("<<factor>>", "<<ID>>"));
            add(new Rule("<<factor>>", "<<num>>"));
            add(new Rule("<<print_expr>>", "<<rel_expr>> <<bool_expr>>"));
            add(new Rule("<<print_expr>>", "\"<<string lit>> \""));
            add(new Rule("<<for_start>>", "<<assign>>"));
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
