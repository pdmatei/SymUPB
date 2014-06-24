import java.io.*;

public class Test {
	/**
	 * Loads the UPB configuration from Packet Tracer
	 * @throws Exception 
	 */
	/* deprecated */
	/*
	public static void loadUPB () throws Exception{
		System.out.println("Starting packet tracer...");
		TopologyExtractor_old backpack = new TopologyExtractor_old();
		backpack.run();
		System.out.println("Closing packet tracer ...");
		backpack.closePacketTracer();
		System.out.println("Running Python script, to generate hs file with devices...");
		backpack.runPythonScript();
		System.out.println("Manually adding wires to the hs file...");
		backpack.addWireRules();
		System.out.println("Done");
	}*/
	public static void loadExperiment () throws Exception{
		//Backpack backpack = new Experiment();
		Backpack backpack = new TopologyExtractor();
		backpack.run();
		backpack.closePacketTracer();
	}
	
	
	public static void main(String[] args) throws Exception {
		//loadUPB();
		loadExperiment();
	}

	
}
