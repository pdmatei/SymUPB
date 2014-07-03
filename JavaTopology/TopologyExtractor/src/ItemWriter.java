import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Matei on 7/1/2014.
 */ /* models an abstract serializer */
class ItemWriter {
	private BufferedWriter bw;
	public ItemWriter (String src){
		File f = new File(src);
		try {
			// if file doesnt exists, then create it
			if (!f.exists()) {
				f.createNewFile();
			}

			BufferedWriter bw = new BufferedWriter(new FileWriter(f.getAbsoluteFile()));
			this.bw = bw;
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	public void write (String str){
		try {
			bw.write(str);
			bw.newLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void close (){
		try {
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
