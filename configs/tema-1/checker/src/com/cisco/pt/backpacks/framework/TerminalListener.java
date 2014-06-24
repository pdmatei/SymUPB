package com.cisco.pt.backpacks.framework;

import java.util.concurrent.Semaphore;

import com.cisco.pt.ipc.events.TerminalLineEvent;
import com.cisco.pt.ipc.events.TerminalLineEvent.EventType;
import com.cisco.pt.ipc.events.TerminalLineEventListener;
import com.cisco.pt.ipc.sim.TerminalLine;

/**
 * Utility class used to aggregate a command's output from terminal events.
 * 
 * @see PTUtils#enterCommand
 */
public class TerminalListener implements TerminalLineEventListener {
	
	/**
	 * An instances ID counter used to generate unique ID's for each TerminalListener 
	 * objects.
	 */
	private static int instanceIDCounter = 0;
	private static Object instanceIDLock = new Object();
	
	/**
	 * The TerminalListener's unique ID.
	 */
	private int id;
	
	private String output;
	private Semaphore waitOnTerminal;
	private String prompt;
	private TerminalLine tl;
	
	/**
	 * Default constructor ('>' prompt, no line to listen)
	 */
	public TerminalListener() {
		this(">", null);
	}
	
	/**
	 * Constructor with default prompt value ('>') and a terminal line to 
	 * listen to.
	 * 
	 * @param tl The terminal line from which to spy.
	 */
	public TerminalListener(TerminalLine tl) {
		this(">", tl);
	}
	
	/**
	 * Full-parameters constructor.
	 * 
	 * @param prompt The prompt string used by the terminal.
	 * @param tl The terminal line from which to spy.
	 */
	public TerminalListener(String prompt, TerminalLine tl) {
		synchronized (instanceIDLock) {
			this.id = instanceIDCounter++;
		}
		
		waitOnTerminal = new Semaphore(0);
		output = "";
		this.prompt = prompt;
		this.tl = tl;
	}
	
	@Override
	public void handleEvent(TerminalLineEvent event) {
		if (event.type == EventType.TERMINAL_UPDATED) {
			TerminalLineEvent.TerminalUpdated termUpdEvent = 
					(TerminalLineEvent.TerminalUpdated) event;
			output = output + termUpdEvent.updatedStr;
			
			if (output.endsWith("\n" + prompt)) {
				waitOnTerminal.release();
				
			} else if (termUpdEvent.updatedStr.contains("--More--")) {
				// press space ;) 
				tl.enterCommand(" ");
			}
			
		}
	}
	
	/**
	 * Returns the output captured so far.
	 * 
	 * @return The captured terminal output.
	 */
	public String getOutput() {
		return output;
	}
	
	/**
	 * Modifies the captured output.
	 * 
	 * <p>Can be used to reset the output for reusing the listener instance.</p>
	 * 
	 * @param output The output to set.
	 */
	public void setOutput(String output) {
		this.output = output;
	}
	
	/**
	 * Returns the semaphore that can be used to wait for the current command 
	 * to finish executing. 
	 * 
	 * <p>The semaphore will be automatically released when the prompt re-appears.</p> 
	 * 
	 * @return A semaphore object.
	 */
	public Semaphore getWaitOnTerminal() {
		return waitOnTerminal;
	}
	
	/**
	 * Sets the internal semaphore instance used to notify a command's 
	 * completion event.
	 * 
	 * @param waitOnTerminal The semaphore to set.
	 */
	public void setWaitOnTerminal(Semaphore waitOnTerminal) {
		this.waitOnTerminal = waitOnTerminal;
	}
	
	/**
	 * Changes (or sets) the terminal to capture the output from.
	 * 
	 * @param tl A TerminalLine object.
	 */
	public void setTerminalLine(TerminalLine tl) {
		this.tl = tl;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof TerminalListener)) {
			return false;
		}
		
		return id == ((TerminalListener)obj).id;
	}
	
	@Override
	public int hashCode() {
		return id;
	}
	
}
