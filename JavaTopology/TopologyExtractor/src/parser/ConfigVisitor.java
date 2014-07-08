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
    private HashMap<String,Interface> interfs = new HashMap<String, Interface>();
    private HashMap<String,List<String>> global_map = new HashMap<String, List<String>>();
    private HashMap<String,Nat> nat_map = new HashMap<String, Nat>();
    private List<Static> static_list = new LinkedList<Static>();
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
                ac_ext_map.put(toUnderscore(name),l);
            }
            else
                l.add(res);

        }
        return null;
    }
    private String toUnderscore(String str){
        return str.replace('-','_');
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

    private String abbreviate (String str){
        //if (str.contains("Ethernet"))
            return str.replace("Ethernet","Eth");
    }

    @Override
    public Object visitInterf(@NotNull AsaParser.InterfContext ctx){
        Interface intf = new Interface();
        // e.g. Ethernet0/1
        String key = abbreviate(ctx.interf_id().getText());
        intf.interf = key;
        for (int i=0; i<ctx.interfaceSpec().getChildCount(); i++){
            //System.out.println(ctx.interfaceSpec().getChild(i).getText());
            // we have a nameif field
            Object o = ctx.interfaceSpec().getChild(i);
            if (o instanceof AsaParser.NameifContext){
                AsaParser.NameifContext nif = (AsaParser.NameifContext)o;
                if (nif.has_nameif() != null)
                    intf.nameif = nif.has_nameif().getChild(1).getText();
            }
            if (o instanceof AsaParser.SecuritylevelContext){
                AsaParser.SecuritylevelContext sec = (AsaParser.SecuritylevelContext)o;
                if (sec.has_securitylevel() != null)
                    intf.security_level = sec.has_securitylevel().getChild(1).getText();
            }
            if (o instanceof AsaParser.Ip_interfaceContext){
                AsaParser.Ip_interfaceContext ipadd = (AsaParser.Ip_interfaceContext)o;
                if (ipadd.has_ip_address() != null) {
                    intf.ip = ipadd.has_ip_address().getChild(1).getText();
                    intf.mask = ipadd.has_ip_address().getChild(2).getText();
                }
            }
            if (o instanceof AsaParser.VlanContext){
                intf.vlan = ((AsaParser.VlanContext)o).getChild(1).getText();
            }
        }
        interfs.put(key,intf);
        return null;
    }

    @Override
    public Object visitGlobal(@NotNull AsaParser.GlobalContext ctx){
        List<String> l = global_map.get(ctx.global_int().getText());
        if (l == null){
            l = new LinkedList<String>();
            global_map.put(ctx.global_int().getText(),l);
        }
        l.add(ctx.global_ip().getText());
        return null;
    }

    @Override
    public Object visitNat(@NotNull AsaParser.NatContext ctx){
        Nat n = new Nat();
        if (ctx.nat_destination() != null)
            n.nat_destination = ctx.nat_destination().getText();
        n.nat_int = ctx.nat_int().getText();
        n.nat_ip = ctx.nat_ip().getText();
        n.nat_mask = ctx.nat_mask().getText();
        this.nat_map.put(ctx.nat_int().getText(),n);

        return null;
    }

    @Override
    public Object visitStatick(@NotNull AsaParser.StatickContext ctx){
        Static s = new Static();
        s.static_src = ctx.static_src().getText();
        s.static_dst = ctx.static_dst().getText();
        s.static_mapped_ip = ctx.static_mapped_ip().getText();
        s.static_real_ip = ctx.static_real_ip().getText();
        s.static_netmask = ctx.static_netmask().getText();
        this.static_list.add(s);
        return null;
    }


    public Map<String,Interface> get_interf_map(){
        return interfs;
    }
    public Map<String,List<String>> get_ac_map(){
        return ac_ext_map;
    }
    public Map<String,List<String>> get_global_map(){
        return global_map;
    }
    public Map<String,Nat> get_nat_list(){
        return nat_map;
    }
    public List<Static> get_static_list(){
        return static_list;
    }
}


