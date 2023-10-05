import java.lang.reflect.Array;
import java.util.*;
class TableIndex extends Pair<Token, Token>{
    public TableIndex(Token row, Token column){
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
    public static ArrayList<String> variables = new ArrayList<>(){
        {
            add("<<prog>>");
            add("<<los>>");
            add("<<stat>>");
            add("<<while>>");
            add("<<for>>");
            add("<<for_start>>");
            add("<<for_arith>>");
            add("<<if>>");
            add("<<else_if>>");
            add("<<else?if>>");
            add("<<poss_if>>");
            add("<<assign>>");
            add("<<decl>>");
            add("<<poss_assign>>");
            add("<<print>>");
            add("<<type>>");
            add("<<expr>>");
            add("<<char_expr>>");
            add("<<bool_expr>>");
            add("<<bool_op>>");
            add("<<bool_eq>>");
            add("<<bool_log>>");
            add("<<rel_expr>>");
            add("<<rel_expr'>>");
            add("<<rel_op>>");
            add("<<arith_expr>>");
            add("<<arith_expr'>>");
            add("<<term>>");
            add("<<term'>>");
            add("<<factor>>");
            add("<<print_expr>>");
        }
    };

    public String variable;
    public String rule;

    public Rule(String variable, String rule) {
        this.variable = variable;
        this.rule = rule;
    }

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

public class ParseTable extends HashTable<TableIndex, Rule> {
    public ArrayList<Pair<TableIndex, Rule>> table = new ArrayList<>();



    public static ArrayList<Rule> ruleList = new ArrayList<>(){
        {
            add(new Rule("<<prog>>", "public class <<ID>> { public static void main ( String[] args ) { <<los>> } }"));
            add(new Rule("<<los>>", "<<stat>> <<los>>"));
            add(new Rule("<<los>>", "epsilon"));
            add(new Rule("<<stat>>", "<<while>>"));
            add(new Rule("<<stat>>", "<<for>>"));
            add(new Rule("<<stat>>", "<<if>>"));
            add(new Rule("<<stat>>", "<<assign>> ;"));
            add(new Rule("<<stat>>", "<<decl>> ;"));
            add(new Rule("<<stat>>", "<<print>> ;"));
            add(new Rule("<<stat>>", ";"));
            add(new Rule("<<while>>", "while ( <<rel_expr>> <<bool_expr>> ) { <<los>> }"));
            add(new Rule("<<for>>", "for ( <<for_start>> ; <<rel_expr>> <<bool_expr>> ; <<for_arith>> ) { <<los>> }"));
            add(new Rule("<<for_start>>", "<<decl>> | <<assign>>"));
            add(new Rule("<<for_start>>", "epsilon"));
            add(new Rule("<<for_arith>>", "<<arith_expr>>"));
            add(new Rule("<<for_arith>>", "epsilon"));
            add(new Rule("<<if>>", "if ( <<rel_expr>> <<bool_expr>> ) { <<los>> } <<else_if>>"));
            add(new Rule("<<else_if>>", "<<else?if>> { <<los>> } <<else_if>>"));
            add(new Rule("<<else_if>>", "epsilon"));
            add(new Rule("<<else?if>>", "else <<poss_if>>"));
            add(new Rule("<<poss_if>>", "if ( <<rel_expr>> <<bool_expr>> )"));
            add(new Rule("<<poss_if>>", "epsilon"));
            add(new Rule("<<assign>>", "<<ID>> = <<expr>>"));
            add(new Rule("<<decl>>", "<<type>> <<ID>> <<poss_assign>>"));
            add(new Rule("<<poss_assign>>", "= <<expr>>"));
            add(new Rule("<<poss_assign>>", "epsilon"));
            add(new Rule("<<print>>", "System.out.println ( <<print_expr>> )"));
            add(new Rule("<<type>>", "int"));
            add(new Rule("<<type>>", "boolean "));
            add(new Rule("<<type>>", "char"));
            add(new Rule("<<expr>>", "<<rel_expr>> <<bool_expr>>"));
            add(new Rule("<<expr>>", "<<char expr>>"));
            add(new Rule("<<char_expr>>", "' <<char>> '"));
            add(new Rule("<<bool_expr>>", "<<bool_op>> <<rel_expr>> <<bool_expr>>"));
            add(new Rule("<<bool_expr>>", "epsilon"));
            add(new Rule("<<bool_op>>", "<<bool_eq>>"));
            add(new Rule("<<bool_op>>", "<<bool_log>>"));
            add(new Rule("<<bool_eq>>", "=="));
            add(new Rule("<<bool_eq>>", "!="));
            add(new Rule("<<bool_log>>", "&&"));
            add(new Rule("<<bool_log>>", "||"));
            add(new Rule("<<rel_expr>>", "<<arith_expr>> <<rel_expr'>>"));
            add(new Rule("<<rel_expr>>", "true"));
            add(new Rule("<<rel_expr>>", "false"));
            add(new Rule("<<rel_expr'>>", "<<rel_op>> <<arith_expr>>"));
            add(new Rule("<<rel_expr'>>", "epsilon"));
            add(new Rule("<<rel_op>>", "<"));
            add(new Rule("<<rel_op>>", "<="));
            add(new Rule("<<rel_op>>", ">"));
            add(new Rule("<<rel_op>>", ">="));
            add(new Rule("<<arith_expr>>", "<<term>> <<arith_expr'>>"));
            add(new Rule("<<arith_expr'>>", "+ <<term>> <<arith_expr'>>"));
            add(new Rule("<<arith_expr'>>", "- <<term>> <<arith_expr'>>"));
            add(new Rule("<<arith_expr'>>", "epsilon"));
            add(new Rule("<<term>>", "<<factor>> <<term'>>"));
            add(new Rule("<<term'>>", "* <<factor>> <<term'>>"));
            add(new Rule("<<term'>>", "/ <<factor>> <<term'>>"));
            add(new Rule("<<term'>>", "% <<factor>> <<term'>>"));
            add(new Rule("<<term'>>", "epsilon"));
            add(new Rule("<<factor'>>", "( <<arith_expr>> )"));
            add(new Rule("<<factor'>>", "( <<ID>> )"));
            add(new Rule("<<factor'>>", "( <<num>> )"));
            add(new Rule("<<print_expr>>", "<<rel_expr>> <<bool_expr>>"));
            add(new Rule("<<print_expr>>", "\"<<string lit>> \""));
        }
    };
}
