package parser;

/**
 * Created by Matei on 7/7/2014.
 */
public class Static extends ConfigObject {

    public String static_src;
    public String static_dst;
    public String static_mapped_ip;
    public String static_real_ip;
    public String static_netmask;

    @Override
    public String[] getContents() {
        String [] r = {static_src, static_dst, static_mapped_ip, static_real_ip, static_netmask};
        return r;
    }

    @Override
    public String[] getNames() {
        String [] r = {"static_src", "static_dst", "static_mapped_ip", "static_real_ip", "static_netmask"};
        return r;
    }
}
