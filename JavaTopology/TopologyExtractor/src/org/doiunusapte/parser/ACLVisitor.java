package org.doiunusapte.parser;

import org.antlr.v4.runtime.misc.NotNull;
import org.doiunusapte.parser.generated.ACLBaseVisitor;
import org.doiunusapte.parser.generated.ACLParser;

public class ACLVisitor extends ACLBaseVisitor<String> {

    @Override
    public String visitLine(@NotNull ACLParser.LineContext ctx) {
        System.out.println(ctx.getText());
        return super.visitLine(ctx);
    }
}
