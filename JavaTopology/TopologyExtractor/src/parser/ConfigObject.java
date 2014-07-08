package parser;

/**
 * Created by Matei on 7/7/2014.
 */
public abstract class ConfigObject {
    public abstract String[] getContents();
    public abstract String[] getNames();

    @Override public String toString(){
        String str="";
        String[] lst = getContents();
        String[] name = getNames();
        for (int i=0; i<lst.length; i++){
            if (lst[i] != null)
                str+=name[i]+" "+lst[i]+"\n";
        }
        return str;
    }
}
