package com.cisco.pt.backpacks.main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import com.cisco.pt.backpacks.framework.Backpack;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The main class of the tester.
 * 
 * <p>Parses the received command-line arguments and runs the checker on the 
 * specified file.</p>
 */
public class TesterMain extends Backpack {
	
	/**
	 * The Log object used for logging exceptions.
	 */
	private static final Log LOGGER = LogFactory.getLog(TesterMain.class);
	
	/**
	 * Stores the target file to be tested.
	 */
	protected String targetFile;
	
	/**
	 * The personalized variables of the instance to be tested.
	 */
	protected HashMap<String, Integer> vars;
	
	/**
	 * The checker class to use.
	 */
	protected AbstractChecker checker;
	
	/**
	 * Program's entry point.
	 * 
	 * @param args The received command-line arguments.
	 * @throws Exception if the program has encountered a fatal error and needs 
	 *         to terminate.
	 */
	public static void main(String[] args) throws Exception {
		if (args.length != 2) {
			System.out.println(
					"Usage: ./tema1checker.sh file.pkt file.txt");
			return;
		}
		
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(args[1]));
			String nume = br.readLine();
			if (nume.trim().contains(" ")) {
				throw new IOException(
						"Date din fisier gresite! Introduceti pe prima linie numele fara spatii");
			}
			String prenume = br.readLine();
			if (prenume.trim().contains(" ")) {
				throw new IOException(
						"Date din fisier gresite! Introduceti pe a doua linie prenumele fara spatii");
			}
			String grupa = br.readLine();
			if (grupa.trim().contains(" ")) {
				throw new IOException(
						"Date din fisier gresite! Introduceti pe a treia linie grupa si seria fara spatii");
			}
			
			HashMap<String, Integer> vars = AbstractChecker.getParticularData(
					nume.trim(), prenume.trim(), grupa.trim());
			
			/*
			 * Instantiate the TesterMain class and call base's run method.
			 * This will, in turn, start a new PacketTracer instance and execute 
			 * the {@link #internalRun} method that will do the actual checking.
			 */
			Backpack backpack = new TesterMain(args[0], vars);
			backpack.run();
			
		} catch (IOException io) {
			System.out.println("Format fisier gresit!");
			io.printStackTrace();
			
		} finally {
			if (br != null)
				br.close();
		}
		System.exit(0);
	}
	
	/**
	 * Initializes a new tester instance.
	 * 
	 * @param targetFile The target PacketTracer file to be tested.
	 * @param subnets The associated custom subnets of the PT project.
	 */
	public TesterMain(String targetFile, HashMap<String, Integer> vars) {
		this.targetFile = targetFile;
		this.vars = vars;
	}
	
	@Override
	protected void internalRun() throws Exception {
		try {
			// instantiate and run the checker
			checker = new Tema1Checker(packetTracerSession, 
					ipcFactory.getIPC(), vars);
			checker.runAll();
			
		} catch (Throwable t) {
			if (t instanceof ThreadDeath) {
				throw (ThreadDeath) t;
			}
			System.out.println("\n\nError:  " + t.getMessage() + "\n\n" + 
					t + "\n\n");
			t.printStackTrace(System.out);
		}
	}
	
	
	/*
	 * Base class configuration.
	 */
	
	@Override
	public int getLaunchSleep() {
		return 1000;
	}
	
	@Override
	protected boolean shouldLaunchPacketTracer() {
		return true;
	}
	
	@Override
	protected Log getLog() {
		return LOGGER;
	}
	
	@Override
	protected String getTargetFile() throws Exception {
		return targetFile;
	}
	
}
