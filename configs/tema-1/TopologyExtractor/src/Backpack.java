

import com.cisco.pt.ipc.IPCError;
import com.cisco.pt.ipc.IPCFactory;
import com.cisco.pt.launcher.PacketTracerLauncher;
import com.cisco.pt.ptmp.ConnectionNegotiationProperties;
import com.cisco.pt.ptmp.PacketTracerSession;
import com.cisco.pt.ptmp.PacketTracerSessionFactory;
import com.cisco.pt.ptmp.impl.ConnectionNegotiationPropertiesImpl;
import com.cisco.pt.ptmp.impl.PacketTracerSessionFactoryImpl;

import java.io.File;
import java.io.IOException;

import org.apache.commons.logging.Log;

/**
 * Abstract class to be used as starting point for applications that need to 
 * interface with PacketTracer.
 * 
 * <p>Several methods must be implemented: </p>
 * <ul>
 *     <li>{@link #internalRun()}: the callback to execute after PacketTracer 
 *        has been initialized;</li>
 *     <li>{@link #getTargetFile()()}: the .pkt project file to be opened with 
 *        PacketTracer when it starts;</li>
 *     <li>{@link #getLog()}: the logger object to use for logging;</li>
 * </ul>
 * 
 * <p>You may also need to adjust the negotiation parameters returned by the 
 * {@link #getNegotiationProperties()} method to match the ones used by your PT 
 * configuration.</p>
 * 
 */
abstract public class Backpack {
	
	/*
	 * The PacketTracer session/IPC objects.
	 */
	protected Process packetTracerProcess;
	protected PacketTracerSession packetTracerSession;
	protected IPCFactory ipcFactory;
	
	/**
	 * The host where PT will reside.
	 */
	protected String hostName = "localhost";
	
	/**
	 * The port to be used for communicating with PT.
	 */
	protected int port = 39000;
	
	
	/**
	 * This method will be executed after a PacketTracer instance has been 
	 * successfully started.
	 * 
	 * @throws Exception if the method cannot continue due to a fatal error.
	 */
	abstract protected void internalRun() throws Exception;
	
	/**
	 * Returns the .pkt file to be opened.
	 * <p>If null, the class will launch PT with no opened file.</p>
	 * 
	 * @return The path to the PacketTracer file to be ran.
	 * @throws Exception if fetching the file path failed.
	 */
	abstract protected String getTargetFile() throws Exception;
	
	/**
	 * Method that will return the logger to be used for logging exceptions throwed 
	 * by this class's methods.
	 * 
	 * @return A logger object.
	 */
	abstract protected Log getLog();
	
	
	/**
	 * The main, entry-point method of the class.
	 * 
	 * <p>This method will connect a PacketTracer instance (optionally, a new one), 
	 * call the {@link #internalRun()} callback method that will contain the 
	 * program's logic.</p>
	 */
	public void run() {
		packetTracerSession = null;
		try {
			if (shouldLaunchPacketTracer()) {
				if (getTargetFile() != null) {
					packetTracerProcess = launchPacketTracer(new File(
							getTargetFile()));
				} else {
					packetTracerProcess = launchPacketTracer();
				}
				
			}
		} catch (Throwable t) {
			if (t instanceof ThreadDeath) {
				throw (ThreadDeath) t;
			}
			
			getLog().error("Unable to launch Packet Tracer", t);
			throw new Error("Unable to launch Packet Tracer executable", t);
		}
		
		if (shouldLaunchPacketTracer() && getLaunchSleep() > 0) {
			int sleepTime = 1000;
			int maxSleepTime = 4000;
			int sleepIterations = 5;
			
			try {
				while (sleepIterations > 0) {
					sleepIterations --;
					Thread.sleep(sleepTime);
					
					// try to connect to Packet Tracer
					if (initPTSession(false)) {
						// yay!
						break;
					} else {
						// increase the waiting time by *2
						if (sleepTime < maxSleepTime)
							sleepTime *= 2;
					}
				}
				
			} catch (InterruptedException e) {
			}
		}
		
		try {
			if (packetTracerSession == null) {
				if (!initPTSession(true))
					return;
			}
			
			internalRun();
			
		} catch (Exception e) {
			getLog().error("Fatal error!", e);
			
		} finally {
			try {
				try {
					if (packetTracerSession != null) {
						packetTracerSession.close();
					}
				} catch (Throwable t) {
					if (t instanceof ThreadDeath) {
						throw (ThreadDeath) t;
					}
					getLog().error("Exception while closing PT session.", t);
				}
				if (shouldLaunchPacketTracer()) {
					closePacketTracer();
				}
			} catch (Throwable t) {
				if (t instanceof ThreadDeath) {
					throw (ThreadDeath) t;
				}
				getLog().error(t);
			}
		}
		
	}
	
	/**
	 * Connects to a running Packet Tracer instance.
	 * 
	 * <p>Updates the packetTracerSession and ipcFactory objects if successful.</p>
	 * <p>If the additional initialization fails, the session is also closed.</p>
	 * 
	 * @param logErrors Whether to log / show errors.
	 * @return Success status (true / false).
	 */
	protected boolean initPTSession(boolean logErrors) {
		boolean success = true;
		try {
			PacketTracerSessionFactory sessionFactory = PacketTracerSessionFactoryImpl
					.getInstance();
			packetTracerSession = createSession(sessionFactory);
			ipcFactory = new IPCFactory(packetTracerSession);
			
			return success;
			
		} catch (IPCError ipcError) {
			success = false;
			if (logErrors) 
				getLog().error("An IPC error occurred!", ipcError);
			
		} catch (IOException e) {
			success = false;
			if (logErrors) 
				getLog().error("IOException when connecting to PT!", e);
			
		} catch (Throwable t) {
			success = false;
			if (t instanceof ThreadDeath) {
				throw (ThreadDeath) t;
			}
			if (logErrors)
				getLog().error("Unknown Exception!", t);
			
		} finally {
			try {
				if (!success) {
					if (packetTracerSession != null) {
						packetTracerSession.close();
					}
				}
			} catch (Throwable t) {
				if (t instanceof ThreadDeath) {
					throw (ThreadDeath) t;
				}
				if (logErrors) 
					getLog().error("Exception while closing PT session.", t);
			}
		}
		
		return success;
	}
	
	/**
	 * This method can be overridden to prevent a PT instance to be started each 
	 * time the {@link #run()} method is called.
	 * 
	 * @return Whether {@link #run()} should launch a new PT instance.
	 */
	protected boolean shouldLaunchPacketTracer() {
		return true;
	}
	
	/**
	 * Set this to the amount of time to wait for PT to initialize.
	 * <p>This is highly dependent on the machine PacketTracer will be ran.</p>
	 * 
	 * @return Sleep interval (milliseconds).
	 */
	public int getLaunchSleep() {
		return 20000;
	}
	
	/**
	 * Launches a new PT instance (with no file argument).
	 * 
	 * @return The newly launched process.
	 * @throws Exception if an error occurs.
	 */
	protected Process launchPacketTracer() throws Exception {
		return launchNewPacketTracer();
	}
	
	/**
	 * Launches a new PT instance (with .pkt file argument).
	 * 
	 * @param file The file to be opened with PacketTracer.
	 * @return The newly launched process.
	 * @throws Exception if an error occurs.
	 */
	protected Process launchPacketTracer(File file) throws Exception {
		return launchNewPacketTracer(file);
	}
	
	/**
	 * Kills the current PT instance.
	 * 
	 * @throws Exception if an error occurs.
	 */
	protected void closePacketTracer() throws Exception {
		if (packetTracerProcess != null) {
			packetTracerProcess.destroy();
			if (PTUtils.isUnix()) {
				Runtime rt = Runtime.getRuntime();
				rt.exec("killall PacketTracer5");
			}
		}
	}
	
	/**
	 * Initializes a communication session with the negotiation properties 
	 * returned by {@link #getNegotiationProperties()} (or the default ones, 
	 * if null is returned).
	 * 
	 * @param sessionFactory The factory object to use.
	 * @return A PT communication session object.
	 * @throws Exception if an error occurs.
	 */
	protected PacketTracerSession createSession(
			PacketTracerSessionFactory sessionFactory) throws Exception {
		ConnectionNegotiationProperties negotiationProperties = getNegotiationProperties();
		if (negotiationProperties == null) {
			return createDefaultSession(sessionFactory);
		} else {
			return createSession(sessionFactory, negotiationProperties);
		}
	}
	
	/**
	 * Initializes a communication session with default properties.
	 * 
	 * @param sessionFactory The factory object to use.
	 * @return A PT communication session object.
	 * @throws Exception if an error occurs.
	 */
	protected PacketTracerSession createDefaultSession(
			PacketTracerSessionFactory sessionFactory) throws Exception {
		return sessionFactory.openSession(hostName, port);
	}
	
	/**
	 * Initializes a communication session with the specified properties.
	 * 
	 * @param sessionFactory The factory object to use.
	 * @param negotiationProperties The negotiation properties to use.
	 * @return A PT communication session object.
	 * @throws Exception if an error occurs.
	 */
	protected PacketTracerSession createSession(
			PacketTracerSessionFactory sessionFactory,
			ConnectionNegotiationProperties negotiationProperties)
			throws Exception {
		return sessionFactory.openSession(hostName, port, negotiationProperties);
	}
	
	/**
	 * Returns the negotiation properties to use for initializing a 
	 * communication session with PacketTracer.
	 * 
	 * <p>Those parameters need to match the corresponding values of the PTA 
	 * file.</p>
	 * 
	 * @return A fully-initialized negotiation properties store.
	 */
	protected ConnectionNegotiationProperties getNegotiationProperties() {
		ConnectionNegotiationPropertiesImpl props = new ConnectionNegotiationPropertiesImpl();
		
		props.setAuthenticationApplication("net.netacad.cisco.ptseplayer");
		props.setAuthenticationSecret("AjDi87dHAkda783HD");
		props.setSignature("PTMP");
		props.setVersion(1);
		props.setAuthentication(4); // MD5_AUTH
		props.setCompression(1);
		props.setEncoding(1);
		props.setEncryption(1);
		props.setKeepAlivePeriod(0);
		
		return props;
		// return null;
	}
	
	/**
	 * Launches a new Packet Tracer process.
	 * 
	 * @return The new PacketTracer's process object.
	 * @throws Exception if an error occurs.
	 */
	protected Process launchNewPacketTracer() throws Exception {
		PacketTracerLauncher launcher = PacketTracerLauncher.getInstance();
		getLog().debug("PT launch directory: " + launcher.getBinDirectory());
		
		return launcher.launch();
	}
	
	/**
	 * Launches a new Packet Tracer process with the specified file argument.
	 * 
	 * @param file The file to be opened in PacketTracer.
	 * @return The new PacketTracer's process object.
	 * @throws Exception if an error occurs.
	 */
	protected Process launchNewPacketTracer(File file) throws Exception {
		PacketTracerLauncher launcher;
		if (PTUtils.isUnix()) {
			launcher = PacketTracerLauncher.getInstance(new File(
					"/usr/local/PacketTracer5/"));
		} else {
			launcher = PacketTracerLauncher.getInstance();
		}
		return launcher.launch(file);
	}
}
