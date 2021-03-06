import java.io.*;
import java.util.*;

class Device {
	public String name;
	public String type;
	public Device (String name, String type){
		this.name = name;
		this.type = type;
	}
	@Override 
	public String toString (){
		return name+"("+type+")";
	}
}

class Switch extends Device{
	public String mac, span;
	public Switch (String name, String type, String mac, String span){
		super(name,type);
		this.mac = mac;
		this.span = span;
	}
	@Override 
	public String toString (){
		return super.toString()+"\n"+mac+"\n"+span;
	}

}

class Link {
	public String p1;
	public String d1;
	public String p2;
	public String d2;
	public Link (String from, String to){
		String[] from_elems = from.split(":");
		String[] to_elems = to.split(":");
		
		this.d1 = from_elems[0];
		this.d2 = to_elems[0];
		this.p1 = abbreviate(from_elems[1]);
		this.p2 = abbreviate(to_elems[1]);
		
	}
	public String abbreviate (String str){
		if(str.contains("FastEthernet"))
			return str.replace("FastEthernet", "Fa");
		if(str.contains("GigabitEthernet"))
			return str.replace("GigabitEthernet", "Ga");
		return str;
	}
	@Override 
	public String toString (){
		return d1+":"+p1+"--"+d2+":"+p2;
	}
}

class Tokens {
	public static final String DEVICE_PORT_SEP = "-";
	public static final String PORT_IN = ":in";
	public static final String PORT_OUT = ":out";
	public static final String RULE_VAR_NAME = "r";
}

public class TopologyGenerator {

	public static String[] getPythonOutput (String name, String span, String mac){

        System.out.println(name+" "+span+" "+mac);

		String out = runScript("gen",name+" "+span+" "+mac);//System.out.println(s);
	    String[] output = out.split("[\\r\\n]+");

	    String[] rs = {output[0],output[1]};
	    return rs;
	}
	public static String runScript (String script, String param){
		String s = "";
		String[] r = null;
		String[] output = null;
		String location = Names.FULL_PROJECT_PATH;
        System.out.println(location+script+" "+param+"");
        //System.out.println("Param is:"+param);
		String[] dosCommand = {"cmd.exe", "/c", location+script+" "+param+""};
	      try {
	         final Process process = Runtime.getRuntime().exec(dosCommand);
	         final InputStream in = process.getInputStream();
	         int ch;
	         
	         while((ch = in.read()) != -1) {
	            //System.out.print((char)ch);
	            s += (char)ch;
	         }
              System.out.println("Raw output is");
              System.out.println(s);
              output = s.split("[\\r\\n]+");
	      } catch (IOException e) {
	         e.printStackTrace();
	      }
	      return s;
	}
	
	public static void loadTopology(List<Device> dl, List<Link> wl){
		File src = new File(Names.PATH);
        for( File f : src.listFiles()){
            if (f.getName().equals(Names.DEVICE_FILE)){
            	
            	try {
					BufferedReader bf = new BufferedReader(new FileReader(f.getAbsolutePath()));
					String str;
					while ((str = bf.readLine()) != null){
						String [] elem = str.split("\\s+");
						
						
						if (elem[1].equals("SWITCH")){
							/*
							File spanSrc = new File(Names.PATH+Names.SPAN_PREFIX+elem[0]+Names.SUFFIX);
							BufferedReader spanBf = new BufferedReader(new FileReader(spanSrc.getAbsolutePath()));

							File macSrc = new File(Names.PATH+Names.MAC_PREFIX+elem[0]+Names.SUFFIX);
							BufferedReader macBf = new BufferedReader(new FileReader(macSrc.getAbsolutePath()));
							
							String raw_span ="", raw_mac="";
							while ((str = spanBf.readLine()) != null)
								raw_span+=str+"\n";
							
							while ((str = macBf.readLine()) != null)
								raw_mac+=str+"\n";

							spanBf.close();
							macBf.close();*/
							
							String spanPath = Names.SPAN_PREFIX+elem[0]+Names.SUFFIX;
							String macPath = Names.MAC_PREFIX+elem[0]+Names.SUFFIX;
							String output[] = getPythonOutput(elem[0],spanPath,macPath);
							
							
							dl.add(new Switch(elem[0],elem[1],output[0],output[1]));
						}
						else
							dl.add(new Device(elem[0],elem[1]));
						
					}
					bf.close();
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
            
            if (f.getName().equals(Names.WIRE_FILE)){
            	try {
					BufferedReader bf = new BufferedReader(new FileReader(f.getAbsolutePath()));
					String str;
					while ((str = bf.readLine()) != null){
						String [] elem = str.split("\\s+");
						wl.add(new Link(elem[0],elem[1]));
					}
					bf.close();
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	continue;
            }
            
        }
	}
	
	public static String prefix (){
		return "import Switch_rule\nimport Rules\nimport Behaviours\nimport CompactFlow\nimport Flow\nimport Expr\nimport Applications\n\n";
	}
	public static String nf (int count){
		String str = "rules = [";
		for (int i=0; i<count; i++)
			if (i == count - 1)
				str += Tokens.RULE_VAR_NAME+i;
			else
				str += Tokens.RULE_VAR_NAME+i+",";
		return str+="]\n";
	}
	
	public static String genHaskell(List<Device> dl, List<Link> wl){
		int i=0;
		String output = prefix();
		for (Device d:dl){
			if (d instanceof Switch){
				output += "r"+i+" = gen_switch "+((Switch)d).mac+" "+((Switch)d).span+"\n";
				i++;
			}
		}
		
		
		for (Link l:wl){
			
			output += "r"+i+" = wire_rule \""+l.d1+Tokens.DEVICE_PORT_SEP+l.p1+Tokens.PORT_OUT+"\" \""+l.d2+Tokens.DEVICE_PORT_SEP+l.p2+Tokens.PORT_IN+"\"\n";
			i++;
			output += "r"+i+" = wire_rule \""+l.d2+Tokens.DEVICE_PORT_SEP+l.p2+Tokens.PORT_OUT+"\" \""+l.d1+Tokens.DEVICE_PORT_SEP+l.p1+Tokens.PORT_IN+"\"\n";
			i++;
		}
		return output+=nf(i);
	}
	
	public static void main(String[] args) throws Exception {
		List<Device> dl = new LinkedList<Device>();
		List<Link> wl = new LinkedList<Link>();
		loadTopology(dl,wl);
		
		String output = genHaskell(dl,wl);
		BufferedWriter bw = new BufferedWriter(new FileWriter(Names.PATH+"Topo.hs"));
		bw.write(output);
		bw.close();
		//System.out.println(output);

		/*
		for (Device d:dl){
        	System.out.println(d);
        }
        for (Link l:wl){
        	System.out.println(l);
        }*/
	}

}
