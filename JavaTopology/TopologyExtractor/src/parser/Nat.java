package parser;

/**
 * Created by Matei on 7/7/2014.
 */
public class Nat extends ConfigObject {
    public String nat_int;
    public String nat_ip;
    public String nat_mask;
    public String nat_destination;
    @Override
    public String[] getContents() {
        String [] r = {nat_int,nat_ip,nat_mask,nat_destination};
        return r;
    }

    @Override
    public String[] getNames() {
        String [] r = {"nat_int","nat_ip","nat_mask","nat_destination"};
        return r;
    }
}
