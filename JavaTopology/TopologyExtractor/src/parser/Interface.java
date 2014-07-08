package parser;

/**
 * Created by Matei on 7/7/2014.
 */
public class Interface extends ConfigObject {
    public String interf;
    public String nameif;
    public String security_level;
    public String ip;
    public String mask;
    public String vlan;

    @Override
    public String[] getContents() {
        String [] r = {interf,nameif,security_level,ip,mask,vlan};
        return r;
    }

    @Override
    public String[] getNames() {
        String [] r = {"interf","nameif","security_level","ip","mask","vlan"};
        return r;
    }
    /*
    public Interface (String interf, String nameif, String security_level, String ip, String mask, String vlan){
        this.interf = interf;
        this.nameif = nameif;
        this.security_level = security_level;
        this.ip = ip;
        this.mask = mask;
        this.vlan = vlan;
    }
    */
}
