package parser;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;
import java.util.*;

/**
 * Created by Matei on 7/3/2014.
 */
public class ConfigVisitor extends AsaBaseVisitor {
    private HashMap<String,List<String>> ac_ext_map = new HashMap<String, List<String>>();
    private HashMap<String,List<String>> ac_std_map = new HashMap<String, List<String>>();

    public ConfigVisitor(){

    }

    private String build_address(AsaParser.PacketContext ctx){
        String str = "";
        if (ctx.any() != null)
            str += "any";
        else if (ctx.host() != null)
            str += "host "+ctx.host().ip().getText();
        else if (ctx.iprange() != null) {
            str += ctx.iprange().ip(0).getText() + " " + ctx.iprange().ip(1).getText();
        }
        return str;
    }
    private String build_port(AsaParser.PortContext ctx){
        String str = "";
        if (ctx != null)
            str+= "eq "+ctx.portid().getText();
        return str;
    }

    @Override
    public Object visitAccesslist(@NotNull AsaParser.AccesslistContext ctx) {
        String name = ctx.acname().getText();//return super.visitAccesslist(ctx);
        if (ctx.acstd() != null)
        {

        }
        if (ctx.acext() != null) {
            String action = ctx.acext().action().getText();
            String proto = ctx.acext().proto().getText();
            String src = build_address(ctx.acext().src().packet());
            String dst = build_address(ctx.acext().dst().packet());
            String sport = build_port(ctx.acext().src().packet().port());
            String dport = build_port(ctx.acext().dst().packet().port());

            String res = "\"" + action + "\" \"" + proto + "\" \"" + src + "\" \"" + sport + "\" \"" + dst + "\"" + " \"" + dport + "\"";
            List<String> l = ac_ext_map.get(name);
            if (l == null){
                l = new LinkedList<String>();
                l.add(res);
                ac_ext_map.put(name.replace('-','_'),l);
            }
            else
                l.add(res);

        }
        return null;
    }

    //builds a rule which results from a sequence of flow conditions
    public String buildRuleSequence (String name, List<String>l, String type){
        int i = 0;
        String tab = "\t\t";
        String str = "r_"+name+" = let\n ";
        for (String s:l){
            str += tab+" f"+i+" = "+type+" "+s+"\n";
            i++;
        }
        str+=tab+"in pipeLine (";
        for (int j=0; j<i; j++)
            str+="f"+j+":";
        str+="[])\n";
        return str;
    }

    public Map<String,List<String>> get_ac_list(){
        return ac_ext_map;
    }
}


