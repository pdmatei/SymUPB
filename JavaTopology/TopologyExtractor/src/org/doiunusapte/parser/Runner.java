package org.doiunusapte.parser;

import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.doiunusapte.parser.generated.ACLBaseVisitor;
import org.doiunusapte.parser.generated.ACLLexer;
import org.doiunusapte.parser.generated.ACLParser;

import java.io.*;

public class Runner {

    public static void main(String[] args) throws IOException {
        parse(args[0]);
    }

    private static void parse(String input) throws IOException {
        ACLLexer lexer = new ACLLexer(new ANTLRFileStream(input));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ACLParser parser = new ACLParser(tokens);

        ParseTree tree = parser.configFile();

        new ACLVisitor().visit(tree);
    }
}
