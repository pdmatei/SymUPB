package parser;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.io.*;
import java.util.*;


public class Test {
    public static void main(String[] args) throws Exception {
// create a CharStream that reads from standard input
        InputStream str = new FileInputStream("src/asa-simple.txt");
        ANTLRInputStream input = new ANTLRInputStream(str);
// create a lexer that feeds off of input CharStream
        AsaLexer lexer = new AsaLexer(input);
// create a buffer of tokens pulled from the lexer
        CommonTokenStream tokens = new CommonTokenStream(lexer);
// create a parser that feeds off the tokens buffer
        AsaParser parser = new AsaParser(tokens);
        ParseTree tree = parser.config(); // begin parsing at init rule
        System.out.println(tree.toStringTree(parser)); // print LISP-style tree


        ConfigVisitor v = new ConfigVisitor();
        v.visit(tree);

        // building rules from access-list
        for (Map.Entry<String,List<String>> e:v.get_ac_map().entrySet()){
            System.out.println(v.buildRuleSequence(e.getKey(),e.getValue(),"acl"));
        }
        // building the list of interfaces
        for (Map.Entry<String,Interface> e:v.get_interf_map().entrySet()) {
            System.out.println(e.getValue());
        }

        // building "global" instructions
        for (Map.Entry<String,List<String>> e:v.get_global_map().entrySet()) {
            System.out.println(e.getKey()+" "+e.getValue());
        }

        // building "nat" instructions
        for (Map.Entry<String,Nat> e:v.get_nat_list().entrySet()) {
            System.out.println(e.getKey()+" "+e.getValue());
        }

        // building "static" instructions
        for (Static e:v.get_static_list()) {
            System.out.println(e);
        }
    }
}
