package com.cisco.pt.backpacks.framework;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import com.cisco.pt.ipc.IPCConstants;
import com.cisco.pt.ipc.enums.CommandStatus;
import com.cisco.pt.ipc.enums.SpecialChar;
import com.cisco.pt.ipc.enums.SwitchPortMode;
import com.cisco.pt.ipc.events.TerminalLineEventRegistry;
import com.cisco.pt.ipc.sim.CiscoDevice;
import com.cisco.pt.ipc.sim.Device;
import com.cisco.pt.ipc.sim.HostIp;
import com.cisco.pt.ipc.sim.Pc;
import com.cisco.pt.ipc.sim.Router;
import com.cisco.pt.ipc.sim.TerminalLine;
import com.cisco.pt.ipc.sim.dns.DNSClient;
import com.cisco.pt.ipc.sim.dns.impl.DNSClientImpl;
import com.cisco.pt.ipc.sim.impl.HostIpImpl;
import com.cisco.pt.ipc.sim.port.HostPort;
import com.cisco.pt.ipc.sim.port.RouterPort;
import com.cisco.pt.ipc.sim.port.SwitchPort;
import com.cisco.pt.ipc.sim.port.impl.HostPortImpl;
import com.cisco.pt.ipc.sim.port.impl.RouterPortImpl;
import com.cisco.pt.ipc.sim.port.impl.SwitchPortImpl;
import com.cisco.pt.ipc.sim.process.STPMainProcess;
import com.cisco.pt.ipc.sim.process.routing.RoutingProcess;
import com.cisco.pt.ipc.sim.process.routing.StaticRoute;
import com.cisco.pt.util.Pair;

/**
 * PacketTracer-related utilities class.
 * 
 * <p>
 * Contains static methods useful for interfacing with a PacketTracer project.
 * </p>
 */
public class PTUtils {
	
	/**
	 * The STP convergence timeout.
	 */
	public static final int STP_TIMEOUT = 40;
	
	/**
	 * Set to true after the first detected STP convergence.
	 */
	private static boolean stpConverged = true;
	
	
	/**
	 * Runs a terminal command of the specified PT device.
	 * 
	 * <p>
	 * The command's output will be returned as String.
	 * </p>
	 * 
	 * @param device The target device to run the command on.
	 * @param tler The PT session's terminal event manager.
	 * @param comm The command to execute.
	 * @param timeout The timeout to wait for command's execution.
	 * @return Command's output.
	 */
	public static String enterCommand(Device device,
			TerminalLineEventRegistry tler, String comm, int timeout) {
		
		ArrayList<String> events = new ArrayList<String>();
		events.add("terminalUpdated");
		
		TerminalLine tlDevice = device.getCommandLine();
		tlDevice.enterCommand("\n");
		TerminalListener tl = new TerminalListener(tlDevice.getPrompt(), tlDevice);
		try {
			tler.addSpecificListenerFiltered(tl, tlDevice, null);
			
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
		
		tlDevice.enterCommand(comm);
		try {
			if (!tl.getWaitOnTerminal().tryAcquire(timeout, TimeUnit.MILLISECONDS)) {
				// send Ctrl+C to abort the command
				tlDevice.enterChar((byte) 3, SpecialChar.fromIntValue(3));
				// now wait for the command to complete...
				if (!tl.getWaitOnTerminal().tryAcquire(1000, TimeUnit.MILLISECONDS)) {
					System.err.println("Warning: Deadlock at getWaitOnTerminal()!");
					return "";
				}
			}
			
			String output = tl.getOutput();
			
			return output;
			
		} catch (InterruptedException e) {
			return "";
			
		} finally {
			try {
				tler.removeSpecificListenerFiltered(tl, tlDevice, events);
				
				// UGLY HACK: because PT Backpacks is bugged like hell (it doesn't remove our listener)
				// we remove it ourselves ;) 
				Field f = tler.getClass().getDeclaredField("specificObjectListeners");
				f.setAccessible(true);
				@SuppressWarnings("unchecked")
				HashMap<Object, Object> set = (HashMap<Object, Object>) f.get(tler);
				// don't waste time searching, delete everything
				for (Entry<Object, Object> entry: set.entrySet()) {
					set.remove(entry.getKey());
				}
				
				Thread.sleep(100);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	/* ========================================================================
	 *                      PC configuration query methods
	 */
	
	
	/**
	 * Returns the IP address of the requested PC device.
	 * 
	 * @param pc The target PC.
	 * @return The PC's IPv4 address (in dot-decimal notation).
	 */
	public static String getPCIpAddress(Pc pc) {
		HostPort hp = new HostPortImpl(pc.getFactory(),
				pc.getPacketTracerSession(), pc.getPortAt(0).getAccessMessage());
		return hp.getIpAddress().toString();
	}
	
	/**
	 * Returns the subnet mask of the requested PC.
	 * 
	 * @param pc The target PC.
	 * @return The PC's subnet mask (dot-decimal notation).
	 */
	public static String getPCNetmask(Pc pc) {
		HostPort hp = new HostPortImpl(pc.getFactory(),
				pc.getPacketTracerSession(), pc.getPortAt(0).getAccessMessage());
		return hp.getSubnetMask().toString();
	}
	
	/**
	 * Returns the gateway address of the requested PC.
	 * 
	 * @param pc The target PC.
	 * @return The PC's gateway IPv4 address (dot-decimal notation).
	 */
	public static String getPCGateway(Pc pc) {
		HostIp hi = new HostIpImpl(pc.getFactory(),
				pc.getPacketTracerSession(), pc.getProcess(
						IPCConstants.HOST_IP_PROCESS).getAccessMessage());
		return hi.getDefaultGateway().toString();
	}
	
	/**
	 * Returns the configured DNS server of the requested PC.
	 * 
	 * @param pc The target PC.
	 * @return The DNS IPv4 address of the DNS server.
	 */
	public static String getPCDNS(Pc pc) {
		DNSClient dns = new DNSClientImpl(pc.getFactory(),
				pc.getPacketTracerSession(), pc.getProcess(
						IPCConstants.DNS_CLIENT_PROCESS).getAccessMessage());
		return dns.getServerIp().toString();
	}
	
	
	/* ========================================================================
	 *                  Router configuration query methods
	 */
	
	
	/**
	 * Fetches a router device's IP address for the specified interface.
	 * 
	 * @param r The target router device.
	 * @param interf The target interface's full name (e.g.: "FastEthernet0/0").
	 * @return The interface's IP, if set.
	 */
	public static String getRouterInterfaceIpAddress(Router r, String interf) {
		// FIXME: is this really necessary?
		PrintStream original = System.out;
		System.setOut(new PrintStream(new OutputStream() {
			public void write(int b) {
				// DO NOTHING
			}
		}));
		
		if (r.getPort(interf) == null) {
			System.setOut(original);
			return null;
		}
		
		RouterPort rp = new RouterPortImpl(r.getFactory(),
				r.getPacketTracerSession(), r.getPort(interf)
						.getAccessMessage());
		if (rp.getIpAddress() == null) {
			System.setOut(original);
			return null;
		}
		System.setOut(original);
		return rp.getIpAddress().toString();
	}
	
	/**
	 * Fetches a router device's subnet mask for the specified interface.
	 * 
	 * @param r The target router device.
	 * @param interf The target interface's full name (e.g.: "FastEthernet0/0").
	 * @return The interface's subnet mask (dot-decimal notation), if set.
	 */
	public static String getRouterInterfaceSubnetMask(Router r, String interf) {
		
		if (r.getPort(interf) == null) {
			return null;
		}
		RouterPort rp = new RouterPortImpl(r.getFactory(),
				r.getPacketTracerSession(), r.getPort(interf)
						.getAccessMessage());
		return rp.getSubnetMask().toString();
	}
	
	/**
	 * Returns the list of static routes of the specified router device.
	 * 
	 * @param r The target router device.
	 * @return Router's static route list.
	 */
	public static List<StaticRoute> getRouterStaticRoutes(Router r) {
		RoutingProcess process = (RoutingProcess) r
				.getProcess("RoutingProcess");
		List<StaticRoute> sr = new ArrayList<StaticRoute>();
		
		int nr = process.getStaticRouteCount();
		
		for (int i = 0; i < nr; i++) {
			sr.add(process.getStaticRouteAt(i));
		}
		
		return sr;
	}
	
	
	/* ========================================================================
	 *                     Device connectivity test methods
	 */
	
	/**
	 * Checks whether ping from a PC to an arbitrary IP address works.
	 * 
	 * <p>
	 * This is done by sending a ping command to the PC's terminal and parsing
	 * its output.
	 * </p>
	 * 
	 * @param pc The source PC.
	 * @param tler The PT session's terminal event manager.
	 * @param destIp The destination IP address.
	 * @param numRetries The maximum number of retries to make.
	 * @return True if the ping succeeds, false otherwise.
	 */
	public static boolean isPingFromPCToIp(Pc pc,
			TerminalLineEventRegistry tler, String destIp, int numRetries) {
		
		int triesLeft = numRetries;
		while (triesLeft > 0) {
			// System.out.println("Try #" + (numRetries + 1 - triesLeft));
			triesLeft--;
			
			String output = enterCommand(pc, tler, "ping -n 1 " + destIp, 2000);
			
			if (output != null && output.contains("time=")) {
				return true;
				
			} else if (output.contains("Control-C") || 
					output.contains("Request timed out")) {
				// timeout, retry
				continue;
				
			} else {
				// FIXME: throw exception, maybe ?
				// System.err.println("PING:\n" + output);
				break;
			}
		}
		
		// ping failed
		return false;
	}
	
	
	/**
	 * Checks whether ping from a router to an arbitrary IP address works.
	 * 
	 * <p>
	 * This is done by sending a ping command to the router's terminal and 
	 * parsing its output.
	 * </p>
	 * 
	 * @param r The source router.
	 * @param tler The PT session's terminal event manager.
	 * @param destIp The destination IPv4 address.
	 * @return True if there is ping, false otherwise.
	 */
	public static boolean isPingFromRouterToIp(Router r,
			TerminalLineEventRegistry tler, String destIp) {
		String output = enterCommand(r, tler, "ping " + destIp, 10000);
		
		if (output != null && output.contains("!")) {
			return true;
		} else {
			return false;
		}
	}
	
	
	/* ========================================================================
	 *                 Cisco switch VLAN querying methods
	 */
	
	
	/**
	 * Returns the target switch's VLAN for the requested port.
	 * 
	 * @param cd The target switch.
	 * @param port Switch's port name.
	 * @return Switch port's VLAN number.
	 */
	public static int getSwitchPortAccessVlan(CiscoDevice cd, String port) {
		SwitchPort sp = new SwitchPortImpl(cd.getFactory(),
				cd.getPacketTracerSession(), cd.getPort(port)
						.getAccessMessage());
		return sp.getAccessVlan();
	}
	
	/**
	 * Returns the target switch's trunk type for the requested port.
	 * 
	 * @param cd The target switch.
	 * @param port Switch's port name.
	 * @return True for trunk port, false for accesss port.
	 */
	public static boolean isSwitchPortTrunking(CiscoDevice cd, String port) {
		SwitchPort sp = new SwitchPortImpl(cd.getFactory(),
				cd.getPacketTracerSession(), cd.getPort(port)
						.getAccessMessage());
		return SwitchPortMode.fromIntValue(
				sp.getAdminOpMode()) == SwitchPortMode.ADMIN_OPERATION_TRUNK;
	}
	
	/**
	 * Returns a list of all allowed VLANs on the specified switch and port.
	 * 
	 * @param cd The target switch.
	 * @param port Switch's port name.
	 * @return A list with port's allowed VLANs.
	 */
	public static List<Integer> getVlanAllowedOnSwitchPortTrunk(CiscoDevice cd,
			String port) {
		
		List<Integer> allowed = new ArrayList<Integer>();
		String shortPort = shortIfName(port); // the output uses short if names
		
		Pair<CommandStatus, String> pr = cd.enterCommand("show int trunk",
				"enable");
		String output = pr.getSecond();
		
		if (output.indexOf("Vlans allowed on trunk\n") < 0) {
			return allowed;
		}
		
		String output2 = output
				.substring(
						output.indexOf("Vlans allowed on trunk\n")
								+ "Vlans allowed on trunk\n".length(),
						output.indexOf("Vlans allowed and active in management domain"));
		
		for (String row : output2.split("\n")) {
			row = row.replaceAll("(\\s)+\\1", "$1");
			
			if (row.toLowerCase().contains(shortPort.toLowerCase())) {
				if (row.split(" ").length < 2)
					continue;
				String[] ranges = row.split(" ")[1].split(",");
				for (String interv : ranges) {
					if (interv.contains("-")) {
						int lower = Integer.parseInt(interv.split("-")[0]);
						int higher = Integer.parseInt(interv.split("-")[1]);
						for (int i = lower; i <= higher; i++) {
							allowed.add(i);
						}
						// if (vlan >= lower && vlan <= higher) {
						// return true;
						// }
					} else {
						allowed.add(Integer.parseInt(interv));
						
					}
				}
			}
		}
		return allowed;
	}
	
	/**
	 * Returns the specified switch port's native VLAN.
	 * 
	 * @param cd The target switch.
	 * @param port Switch's port name.
	 * @return The port's native VLAN number.
	 */
	public static int getSwitchPortNativeVlan(CiscoDevice cd, String port) {
		SwitchPort sp = new SwitchPortImpl(cd.getFactory(),
				cd.getPacketTracerSession(), cd.getPort(port)
						.getAccessMessage());
		return sp.getNativeVlanId();
	}
	
	/**
	 * Checks whether the specified VLAN is in the switch's database.
	 * 
	 * @param cd The target switch.
	 * @param vlanid The VLAN ID to check.
	 * @return True if the VLAN has been set up, false otherwise.
	 */
	public static boolean isVlanInDatabase(CiscoDevice cd, int vlanid) {
		
		Pair<CommandStatus, String> pr = cd.enterCommand("show vlan id "
				+ vlanid, "enable");
		String output = pr.getSecond();
		
		if (output.contains("not found in current VLAN database")) {
			return false;
		}
		return true;
	}
	
	
	/* ========================================================================
	 *                     Cisco switch STP querying methods
	 */
	
	/**
	 * Waits for the STP to converge.
	 * 
	 * @param device The device to test.
	 */
	public static void waitForSTP(Device device) {
		long startTime = System.currentTimeMillis();
		while (true) {
			int timeout = (stpConverged ? 10 /* seconds */ : STP_TIMEOUT);
			if ((System.currentTimeMillis() - startTime) > timeout * 1000) {
				// STP timeout reached
				stpConverged = true; // avout further waiting
				break;
			}
			
			Pair<CommandStatus, String> pr = ((CiscoDevice) device).enterCommand(
					"show spanning-tree", "enable");
			String output = pr.getSecond();
			
			if (!output.contains("FWD")) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
					return;
				}
				continue;
			} else {
				stpConverged = true;
				return;
			}
		}
	}
	
	/**
	 * Checks whether the specified switch is the STP root bridge (for a given 
	 * VLAN).
	 * 
	 * @param device The device to check.
	 * @param vlanId The VLAN number to check.
	 * @return True if the switch is the root bridge for the specified VLAN, 
	 *         false otherwise.
	 */
	public static boolean isRootBridgeOnVlan(Device device, int vlanId) {
		if (isVlanInDatabase((CiscoDevice) device, vlanId)) {
			waitForSTP(device);
			STPMainProcess stpMain = (STPMainProcess) device
					.getProcess(IPCConstants.STP_MAIN_PROCESS);
			if (stpMain == null || stpMain.getStpProcess(vlanId) == null) {
				return false;
			}
			return stpMain.getStpProcess(vlanId).isRootBridge();
			
		} else {
			return false;
		}
	}
	
	/**
	 * Returns a requested switch port's STP status.
	 * 
	 * @param cd The target switch device.
	 * @param vlanid The VLAN to retrieve the status for.
	 * @param port Port to fetch the status for.
	 * @return The requested port's STP status, as string message.
	 */
	public static String getSTPPortStatus(CiscoDevice cd, int vlanid,
			String port) {
		
		waitForSTP(cd);
		
		Pair<CommandStatus, String> pr = cd.enterCommand(
				"show spanning-tree interface " + port, "enable");
		String output = pr.getSecond();
		String vlan;
		if (vlanid < 10) {
			vlan = "VLAN000" + vlanid;
		} else if (vlanid < 100) {
			vlan = "VLAN00" + vlanid;
		} else if (vlanid < 1000) {
			vlan = "VLAN0" + vlanid;
		} else if (vlanid < 4096) {
			vlan = "VLAN" + vlanid;
		} else {
			return null;
		}
		
		if (output.indexOf(vlan) < 0) {
			return null;
		}
		
		String row = output.substring(output.indexOf(vlan),
				output.indexOf("\n", output.indexOf(vlan)));
		row = row.replaceAll("(\\s)+\\1", "$1");
		return row.split(" ")[2];
	}
	
	
	/* ========================================================================
	 *                           Miscellaneous methods
	 */
	
	/**
	 * Returns the full interface name for the given port string.
	 * 
	 * <p>Replaces the following prefixes with their full counterpart: </p>
	 * <ul>
	 *     <li>Gig with GigabitEthernet</li>
	 *     <li>Fa with FastEthernet</li>
	 *     <li>E with Ethernet</li>
	 *     <li>Se with Serial</li>
	 * </ul>
	 * 
	 * <p>To be used when you need to call a method that requires the full 
	 * interface name.</p>
	 * 
	 * @example fullIfName("Fa0/1") => "FastEthernet0/1"
	 * 
	 * @param name The original name.
	 * @return The full name of the interface.
	 */
	public static String fullIfName(String name) {
		final String[][] abbr = new String[][]{
				new String[]{"Gig", "GigabitEthernet"}, 
				new String[]{"Fa", "FastEthernet"}, 
				new String[]{"E", "Ethernet"}, 
				new String[]{"Se", "Serial"}, 
		};
		
		for (String[] a: abbr) {
			if (name.matches("^" + a[0] + "[ 0-9/]+")) {
				return name.replaceFirst("^" + a[0], a[1]);
			}
		}
		
		return name;
	}
	
	/**
	 * Returns the short interface name for the given port string.
	 * 
	 * <p>The reverse of {@link #fullIfName}.</p>
	 * 
	 * <p>Used by methods that parse command results and expect short names.</p>
	 * 
	 * @example shortIfName("FastEthernet0/1") => "Fa0/1"
	 * 
	 * @param name The original name.
	 * @return The full name of the interface.
	 */
	public static String shortIfName(String name) {
		final String[][] abbr = new String[][]{
				new String[]{"Gig", "GigabitEthernet"}, 
				new String[]{"Fa", "FastEthernet"}, 
				new String[]{"E", "Ethernet"}, 
				new String[]{"Se", "Serial"}, 
		};
		
		for (String[] a: abbr) {
			if (name.matches("^" + a[1] + "[ 0-9/]+")) {
				return name.replaceFirst("^" + a[1], a[0]);
			}
		}
		
		return name;
	}
	/**
	 * Checks whether the virtual machine is running on Windows.
	 * 
	 * @return True if on Microsoft Windows.
	 */
	public static boolean isWindows() {
		String os = System.getProperty("os.name").toLowerCase();
		return (os.indexOf("win") >= 0);
	}
	
	/**
	 * Checks whether the virtual machine is running on Mac OS X.
	 * 
	 * @return True if on Mac OS X.
	 */
	public static boolean isMac() {
		String os = System.getProperty("os.name").toLowerCase();
		return (os.indexOf("mac") >= 0);
	}
	
	/**
	 * Checks whether the virtual machine is running on a Unix-based operating 
	 * system (or Linux).
	 * 
	 * @return True if on Unix or Linux.
	 */
	public static boolean isUnix() {
		String os = System.getProperty("os.name").toLowerCase();
		return (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0);
	}
	
	/**
	 * Checks whether the virtual machine is running on Solaris.
	 * 
	 * @return True if on Solaris.
	 */
	public static boolean isSolaris() {
		String os = System.getProperty("os.name").toLowerCase();
		// Solaris
		return (os.indexOf("sunos") >= 0);
	}
	
}
