package com.cisco.pt.backpacks.main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cisco.pt.backpacks.framework.PTUtils;
import com.cisco.pt.backpacks.main.netutils.Network;
import com.cisco.pt.ipc.events.TerminalLineEventRegistry;
import com.cisco.pt.ipc.sim.CiscoDevice;
import com.cisco.pt.ipc.sim.Device;
import com.cisco.pt.ipc.sim.Pc;
import com.cisco.pt.ipc.sim.Router;
import com.cisco.pt.ipc.sim.process.routing.StaticRoute;

/**
 * Abstract class that can be used as framework for PacketTracer assignment  
 * testers.
 * 
 * <p>Contains methods for outputting pretty test result messages and various 
 * PacketTracer-related assertions. </p>
 * 
 * <p>Also has automatic score-managing routines for incrementing / displaying 
 * the test scores (see {@link #currentScore}, {@link #totalScore}).</p>
 * 
 */
public abstract class AbstractChecker {
	
	/**
	 * Display line length, used to control the wrapping and alignment of the 
	 * outputted text.
	 */
	protected int displayLength = 74;
	
	
	/**
	 * The current and total score variables used to count the test scores.
	 * 
	 * <p>Those values are automatically managed by the test* methods.</p>
	 * 
	 * <p>The currentScore automatically resets when beginning a new test 
	 * (i.e. after calling the {@link #testHeader} method) and it is displayed 
	 * when {@link #testResults} is called.</p>
	 * 
	 * <p>The totalScore is is displayed by {@link #echoTotals}.</p>
	 * 
	 */
	protected double currentScore, totalScore;
	
	/**
	 * Stores the maximum number of points for the current test / in total. 
	 * 
	 * <p>Managed like currentScore/totalScore, but it will be always incremented (even if a 
	 * checkpoint fails).</p>
	 */
	protected double currentMaxScore, totalMaxScore;
	
	/**
	 * Stores the PT file's devices list.
	 * 
	 * <p>Used by the assertion methods, for test writing convenience.</p>
	 * <p>Needs to be initialized from the subclass.</p>
	 */
	protected HashMap<String, Device> deviceMap;
	
	/**
	 * Stores a map with the network's subnets.
	 * 
	 * <p>Used by the assertion methods, for test writing convenience.</p>
	 * <p>Needs to be initialized from the subclass.</p>
	 */
	protected HashMap<String, Network> subnetMap;
	
	/**
	 * Terminal events manager of the connected PacketTracer session.
	 * 
	 * <p>Used by the assertion methods, for test writing convenience.</p>
	 * <p>Needs to be initialized from the subclass.</p>
	 */
	protected TerminalLineEventRegistry tler;
	
	/**
	 * Will become false after the first ping command has been issued.
	 */
	protected boolean firstPing = true;
	
	
	/* ========================================================================
	 *                     Constructors, getters and setters
	 */
	
	/**
	 * Abstract constructor.
	 * 
	 * <p>Initializes all fields to their default values. Since all important 
	 * class fields are protected, the inheriting class can override them, if 
	 * required</p>
	 */
	protected AbstractChecker() {
		// initialize the scores
		currentScore = totalScore = 0;
		currentMaxScore = totalMaxScore = 0;
		
		/*
		 * Those will need to be initialized by the subclass, if the assertion 
		 * methods will be used.
		 */
		deviceMap = null;
		subnetMap = null;
		tler = null;
	}
	

	/* ========================================================================
	 *               Abstract methods that need implementations
	 */
	
	/**
	 * Runs all tests and outputs the results to <i>System.out</i>.
	 * 
	 * @throws Exception if the PT topology is invalid
	 */
	public abstract void runAll() throws Exception;
	
	
	/* ========================================================================
	 *                     Output and score management routines
	 */
	
	/**
	 * Counts the given points to the current/total score fields.
	 * If b == false, it only adds them to the max* score fields.
	 * 
	 * @param b Whether to affect the earned score.
	 * @param score The amount to add.
	 */
	protected void addScore(boolean b, double points) {
		if (b) currentScore += points;
		currentMaxScore += points;
		if (b) totalScore += points;
		totalMaxScore += points;
	}
	
	/**
	 * Rescales the current score from [0, currentMaxScore] to [0, max].
	 * 
	 * <p>Also affects the total score - it is realigned with the new 
	 * partial values.</p>
	 * 
	 * @param max The new maximum score.
	 */
	protected void rescaleCurrentScore(double max) {
		// subtract the current score from the total
		totalScore -= currentScore;
		totalMaxScore -= currentMaxScore;
		
		currentScore = ( currentScore / currentMaxScore ) * max;
		currentMaxScore = max;
		
		// add the new, rescaled, score
		totalScore += currentScore;
		totalMaxScore += currentMaxScore;
	}
	
	/**
	 * Rescales the total score from [0, totalMaxScore] to [0, max].
	 * 
	 * @param max The new maximum score.
	 */
	protected void rescaleTotalScore(double max) {
		totalScore = ( totalScore / totalMaxScore ) * max;
		totalMaxScore = max;
	}
	
	/**
	 * Starts a new test, outputing a test header.
	 * 
	 * @param t Test's name.
	 */
	protected void testHeader(String t) {
		int tLen = (displayLength - t.length()) / 2;
		
		for (int i = 0; i < tLen; i++) {
			t = "#" + t;
		}
		for (int i = 0; i < tLen; i++) {
			t = t + "#";
		}
		System.out.println(t);
		
		// reset the score counters
		currentScore = 0;
		currentMaxScore = 0;
	}
	
	/**
	 * Outputs a test checkpoint's results, incrementing the scores if the 
	 * test has passed.
	 * 
	 * @param t Checkpoint's name.
	 * @param b Whether the test has passed (true) or failed (false).
	 * @param points The number of points to add, if passed.
	 */
	protected void testCheckpoint(String t, boolean b, double points) {
		String res = (b ? "pass" : "fail");
		addScore(b, points);
		
		int tLen = t.length() + res.length();
		// add '.' padding
		for (int i = 0; i < displayLength - tLen; i++) {
			t += ".";
		}
		t += res;
		System.out.println(t);
	}
	
	/**
	 * Prints the current test's results (partial scores).
	 * 
	 * @param t Test's name.
	 */
	protected void testResults(String t) {
		currentScore = (double)Math.round(currentScore * 100) / 100;
		currentMaxScore = (double)Math.round(currentMaxScore * 100) / 100;
		
		String score = "[ " + currentScore + "/" + currentMaxScore + " ]";
		int tLen = t.length() + score.length();
		for (int i = 0; i < displayLength - tLen; i++) {
			t += ".";
		}
		t = t + score + "\n\n";
		System.out.println(t);
	}
	
	/**
	 * Prints the checker ending message with the total number of points 
	 * earned.
	 * 
	 * @param points The total number of points earned.
	 * @param maxPoints The maximum grade.
	 */
	protected void echoTotal() {
		totalScore = (double)Math.round(totalScore * 100) / 100;
		totalMaxScore = (double)Math.round(totalMaxScore * 100) / 100;
		
		String score = "Total  =  [ " + totalScore + "/" + totalMaxScore + " ]";
		int scoreLen = score.length();
		for (int i = 0; i < displayLength - scoreLen; i++) {
			score = " " + score;
		}
		score = "\n\n" + score + "\n\n";
		System.out.println(score);
	}
	
	
	/* ========================================================================
	 *                            Assertion methods
	 */
	
	/**
	 * Method that asserts that a PC device has the correct IP address/netmask 
	 * set up (it needs to belong to the given subnet).
	 * 
	 * <p>The device's name must respect the convention of a A-Z string 
	 * followed by the station's index.</p>
	 * 
	 * <p>The device must have its IP address equal to the second/third/fourth 
	 * etc. assignable address of the subnet (depending on its index).</p>
	 * 
	 * @param pcName The PC device's name.
	 * @param subnet The subnet the device needs to be part of.
	 * @param points The value of this test
	 * @throws Exception if the PacketTracer topology is invalid.
	 */
	protected void assertPCIp(String pcName, String subnet, 
				double points) throws Exception {
		
		Pc pc = (Pc)deviceMap.get(pcName);
		Network net = subnetMap.get(subnet);
		
		String ip = "";
		Pattern namePat = Pattern.compile("^.*[^0-9]+([0-9]+)$");
		Matcher m = namePat.matcher(pcName);
		if (m.matches()) {
			int i = Integer.parseInt(m.group(1));
			i++; // the first address is reserved for the gateway
			ip = net.assignableAddress(i);
			
		} else throw new Exception(pcName + ": numele PC-ului este invalid!");
		
		boolean passed = ip.equals(PTUtils.getPCIpAddress(pc))
				&& net.getOctalMask().equals(PTUtils.getPCNetmask(pc))
				&& net.assignableAddress(1).equals(PTUtils.getPCGateway(pc));
		
		testCheckpoint("Verificare IP/Netmask/Gateway pe " + pcName, 
				passed, points);
	}
	
	/**
	 * Like the above method, but takes an array of device names.
	 * 
	 * <p>Has an extra 'group' parameter that defines the name that will be 
	 * printed on the console.</p>
	 * 
	 * @see #assertPCIp(String, String, double)
	 * @param devices An array with the names of the devices to check.
	 * @param subnet The subnet the devices need to be part of.
	 * @param group Device group's name (used for displaying).
	 * @param points The value (in points) of this test.
	 * @throws Exception if the PacketTracer topology is invalid.
	 */
	protected void assertMultiplePCIp(String[] devices, String subnet, 
				String group, double points) throws Exception {
		
		boolean passed = true;
		Network net = subnetMap.get(subnet);
		
		for (String pcName: devices) {
			Pc pc = (Pc)deviceMap.get(pcName);
			
			String ip = "";
			Pattern namePat = Pattern.compile("^.*[^0-9]+([0-9]+)$");
			Matcher m = namePat.matcher(pcName);
			if (m.matches()) {
				int i = Integer.parseInt(m.group(1));
				i++; // the first address is reserved for the gateway
				ip = net.assignableAddress(i);
				
			} else throw new Exception(pcName + ": numele PC-ului este invalid!");
			
			passed &= ip.equals(PTUtils.getPCIpAddress(pc))
					&& net.getOctalMask().equals(PTUtils.getPCNetmask(pc))
					&& net.assignableAddress(1).equals(PTUtils.getPCGateway(pc));
		}
		
		testCheckpoint("Verificare IP/Netmask/Gateway - " + group, 
				passed, points);
	}
	
	/**
	 * Asserts that the DNS address of the requested PC device is correct.
	 * 
	 * @param pcName The name of the PC to check.
	 * @param expectedDNS The expected value of the DNS.
	 * @param points The value (in points) of this test.
	 * @throws Exception if the PacketTracer topology is invalid.
	 */
	protected void assertPCDNS(String pcName, 
				String expectedDNS, double points) throws Exception {
		
		boolean passed;
		Pc pc = (Pc)deviceMap.get(pcName);
		passed = expectedDNS.equals(PTUtils.getPCDNS(pc));
		
		testCheckpoint("Verificare DNS pe " + pcName, passed, points);
	}
	
	
	/**
	 * Asserts that the DNSes of multiple PCs from a common zone are correct.
	 * 
	 * @param devices The names of the PCs to check.
	 * @param expectedDNS The expected value of the DNS addresses.
	 * @param zone The DNS zone's name (used for displaying).
	 * @param points The value (in points) of this test.
	 * @throws Exception if the PacketTracer topology is invalid.
	 */
	protected void assertMultiplePCDNS(String[] deviceNames, String expectedDNS, 
				String zone, double points) throws Exception {
		
		boolean passed = true;
		for (String name: deviceNames) {
			Pc pc = (Pc)deviceMap.get(name);
			passed = passed & expectedDNS.equals(PTUtils.getPCDNS(pc));
		}
		
		testCheckpoint("Verificare DNS - " + zone, passed, points);
	}
	
	/**
	 * Asserts that the VLAN of the given switch port is equal to that of the 
	 * given subnet.
	 * 
	 * @param swName Switch device's name.
	 * @param port Switch interface to check.
	 * @param expectedSubnet The expected subnet's symbolic name.
	 * @param points The value (in points) of this test.
	 * @throws Exception if the PacketTracer topology is invalid.
	 */
	protected void assertSwPortVlan(String swName, String port, 
			String expectedSubnet, double points) throws Exception {
		
		CiscoDevice sw = (CiscoDevice) deviceMap.get(swName);
		Network net = subnetMap.get(expectedSubnet);
		port = PTUtils.fullIfName(port);
		
		testCheckpoint("VLAN " + " - " + port + " -- " + swName,
				PTUtils.getSwitchPortAccessVlan(sw, port) == net.getVlan(),
				points);
	}
	
	/**
	 * Asserts that the given switch port is set to TRUNK mode.
	 * 
	 * <p>If the interface is correctly set to trunk, the method can 
	 * optionally check if the list of port's allowed VLANS is the same as of 
	 * the specified subnets (if allowedSubnets != null); it can also assert 
	 * the native VLAN (if nativeSubnet != null).</p>
	 * 
	 * @param swName Switch device's name.
	 * @param port Switch interface to check.
	 * @param allowedSubnets Switch's allowed subnets, null to ignore.
	 * @param nativeSubnet The subnet whose VLAN needs to be native, null not  
	 *        to check this.
	 * @param points The value (in points) of this test.
	 * @throws Exception if the PacketTracer topology is invalid.
	 */
	protected void assertSwPortTrunk(String swName, String port, 
			String[] allowedSubnets, String nativeSubnet, double points) throws Exception {
		CiscoDevice sw = (CiscoDevice) deviceMap.get(swName);
		List<Integer> vlans = new ArrayList<Integer>();
		port = PTUtils.fullIfName(port);
		
		if (PTUtils.isSwitchPortTrunking(sw, port)) {
			testCheckpoint("Mode TRUNK port " + port + " -- " + swName,
					true, 0);
			
			boolean allowedSubnetsOK = true;
			if (allowedSubnets != null) {
				List<Integer> allowed = PTUtils
						.getVlanAllowedOnSwitchPortTrunk(sw, port);
				for (String subnet : allowedSubnets) {
					int vlan = subnetMap.get(subnet).getVlan();
					if (!allowed.contains(vlan)) {
						allowedSubnetsOK = false;
					} else {
						allowed.remove((Integer) vlan);
					}
					vlans.add(vlan);
				}
				
				allowedSubnetsOK = allowedSubnetsOK & allowed.isEmpty();
				testCheckpoint("Acces pe TRUNK numai pentru VLAN-urile permise", 
						allowedSubnetsOK, 0);
				
			}
			
			boolean nativeVLANOK = true;
			if (nativeSubnet != null) {
				int nativeVLAN = subnetMap.get(nativeSubnet).getVlan();
				nativeVLANOK = (PTUtils.getSwitchPortNativeVlan(sw, port) == nativeVLAN);
				testCheckpoint("Vlan nativ TRUNK - " + port, nativeVLANOK, 0);
			}
			
			// manual points management
			addScore(allowedSubnetsOK && nativeVLANOK, points);
			
		} else {
			testCheckpoint("Mode TRUNK port " + port + " -- " + swName,
					false, points);
			
		}
	}
	
	/**
	 * Asserts that the specified router interface has a specific IP set up 
	 * (from subnet, the ipNr'th assignable address).
	 * 
	 * <p>If subinterface is set to true, the subinterface with the subnet's 
	 * VLAN tag will be checked.</p>
	 * 
	 * @param routerName The router device's name.
	 * @param interf The interface to check.
	 * @param subnet The subnet the interface needs to belong to.
	 * @param ipNr Assignable address's number of the interface.
	 * @param subinterface If the interface uses VLAN subinterfaces.
	 * @param points The value (in points) of this test.
	 * @throws Exception if the PacketTracer topology is invalid.
	 */
	protected void assertRouterIp(String routerName, String interf,
			String subnet, int ipNr, boolean subinterface, 
			double points) throws Exception {
		
		Router r = (Router) deviceMap.get(routerName);
		Network net = subnetMap.get(subnet);
		boolean passed;
		interf = PTUtils.fullIfName(interf);
		
		if (subinterface) {
			passed = net.assignableAddress(ipNr).equals(
					PTUtils.getRouterInterfaceIpAddress(r, 
							interf + "." + net.getVlan()));
		} else {
			passed = net.assignableAddress(ipNr).equals(
					PTUtils.getRouterInterfaceIpAddress(r, interf));
		}
		
		testCheckpoint("Verificare IP - " + interf + " -- " + routerName,
				passed, points);
	}
	
	/**
	 * Asserts that a router has route to the given subnet.
	 * 
	 * <p>The route must have the administrative distance equal to 1.</p>
	 * 
	 * @param routerName Router device's name.
	 * @param targetSubnet The target subnet's symbolic name.
	 * @param nextHop Next hop of the route (IP address).
	 * @param points The value (in points) of this test.
	 * @throws Exception if the PacketTracer topology is invalid.
	 */
	protected void assertRoute(String routerName, String targetSubnet, 
			String nextHop, double points) throws Exception {
		Router r = (Router) deviceMap.get(routerName);
		Network targetNet = subnetMap.get(targetSubnet);
		String networkAddress = targetNet.getAddress();
		String netmask = targetNet.getOctalMask();
		boolean passed = false;
		
		List<StaticRoute> routes = PTUtils.getRouterStaticRoutes(r);
		
		for (StaticRoute sr : routes) {
			if (sr.getNetworkId().toString().equals(networkAddress)
						&& sr.getSubnetMask().toString().equals(netmask)
						&& sr.getNextHop().toString().equals(nextHop)
						&& sr.getAdminDistance() == 1 ) {
				passed = true;
				break;
			}
		}
		
		testCheckpoint("Route " + targetSubnet + " -- " + routerName, passed, points);
	}
	
	/**
	 * Asserts that a router has an alternative route to the given subnet.
	 * 
	 * <p>The route must have the administrative distance greater than 1.</p>
	 * 
	 * @param routerName Router device's name.
	 * @param targetSubnet The target subnet's symbolic name.
	 * @param nextHop Next hop of the route (IP address).
	 * @param points The value (in points) of this test.
	 * @throws Exception if the PacketTracer topology is invalid.
	 */
	protected void assertAltRoute(String routerName, String targetSubnet, 
			String nextHop, double points) throws Exception {
		Router r = (Router) deviceMap.get(routerName);
		Network targetNet = subnetMap.get(targetSubnet);
		String networkAddress = targetNet.getAddress();
		String netmask = targetNet.getOctalMask();
		boolean passed = false;
		
		List<StaticRoute> routes = PTUtils.getRouterStaticRoutes(r);
		
		for (StaticRoute sr : routes) {
			if (sr.getNetworkId().toString().equals(networkAddress)
						&& sr.getSubnetMask().toString().equals(netmask)
						&& sr.getNextHop().toString().equals(nextHop)
						&& sr.getAdminDistance() > 1 ) {
				passed = true;
				break;
			}
		}
		
		testCheckpoint("Alt. route " + targetSubnet + " -- " + routerName, passed, points);
	}
	
	/**
	 * Asserts that a router has route to the given subnet (multiple next hops 
	 * variant).
	 * 
	 * <p>The route must have one the specified next hops.</p>
	 * <p>The route must have the administrative distance equal to 1.</p>
	 * 
	 * @param routerName Router device's name.
	 * @param targetSubnet The target subnet's symbolic name.
	 * @param nextHops Array with the allowed next hops (IP addresses).
	 * @param points The value (in points) of this test.
	 * @throws Exception if the PacketTracer topology is invalid.
	 */
	protected void assertRouteMultipleNextHops(String routerName, String targetSubnet, 
			String[] nextHops, double points) throws Exception {
		Router r = (Router) deviceMap.get(routerName);
		Network targetNet = subnetMap.get(targetSubnet);
		String networkAddress = targetNet.getAddress();
		String netmask = targetNet.getOctalMask();
		boolean passed = false;
		
		List<StaticRoute> routes = PTUtils.getRouterStaticRoutes(r);
		
		for (StaticRoute sr : routes) {
			for (String nextHop: nextHops) {
				if (sr.getNetworkId().toString().equals(networkAddress)
							&& sr.getSubnetMask().toString().equals(netmask)
							&& sr.getNextHop().toString().equals(nextHop)
							&& sr.getAdminDistance() == 1 ) {
					passed = true;
					break;
				}
			}
		}
		
		testCheckpoint("Route " + targetSubnet + " -- " + routerName, passed, points);
	}
	
	/**
	 * Asserts that a router has route to the given subnet, via a specific 
	 * interface.
	 * 
	 * @param routerName Router device's name.
	 * @param targetSubnet The target subnet's symbolic name.
	 * @param viaInterf The via interface of the route.
	 * @param points The value (in points) of this test.
	 * @throws Exception if the PacketTracer topology is invalid.
	 */
	protected void assertRouteViaInterface(String routerName, 
			String targetSubnet, String viaInterf,
			double points) throws Exception {
		
		Router r = (Router) deviceMap.get(routerName);
		Network targetNet = subnetMap.get(targetSubnet);
		String networkAddress = targetNet.getAddress();
		String netmask = targetNet.getOctalMask();
		boolean passed = false;
		viaInterf = PTUtils.fullIfName(viaInterf);
		
		List<StaticRoute> routes = PTUtils.getRouterStaticRoutes(r);
		
		for (StaticRoute sr : routes) {
			if (sr.getNetworkId().toString().equals(networkAddress)
					&& sr.getSubnetMask().toString().equals(netmask)
					&& sr.getPortName().equals(viaInterf)) {
				passed = true;
				break;
			}
		}
		
		testCheckpoint("Route " + targetSubnet + " via " + viaInterf + 
				" -- " + routerName, 
				passed, points);
	}
	
	/**
	 * Asserts that there is ping between 2 PCs.
	 * 
	 * @param fromName Source PC's name.
	 * @param toName Destination PC's name.
	 * @param points The value (in points) of this test.
	 * @throws Exception if the PacketTracer topology is invalid.
	 */
	protected void assertPingPCtoPC(String fromName, String toName,
			double points) throws Exception {
		
		Pc fromPC = (Pc) deviceMap.get(fromName);
		Pc toPC = (Pc) deviceMap.get(toName);
		
		int retries = 5;
		if (firstPing) retries = 15;
		
		boolean passed = PTUtils.isPingFromPCToIp(
						fromPC, tler, PTUtils.getPCIpAddress(toPC), retries);
		firstPing = false;
		
		testCheckpoint("Ping de la " + fromName + " la " + toName, passed, points);
	}
	
	/**
	 * Asserts that the given switch is the STP root bridge for the given 
	 * VLANs.
	 * 
	 * @param swName The switch's name.
	 * @param vlanSubnets An array with the subnets where the switch needs to 
	 *        be root.
	 * @param message Message to print for the checkpoint.
	 * @param points The value (in points) of this test.
	 * @throws Exception if the PacketTracer topology is invalid.
	 */
	protected void assertSwSTPRootBridge(String swName, String[] vlanSubnets, 
			String message, double points) throws Exception {
		
		Device sw = deviceMap.get(swName);
		boolean passed = true;
		
		for (String vlanId : vlanSubnets) {
			Network net = subnetMap.get(vlanId);
			passed &= PTUtils.isRootBridgeOnVlan(sw, net.getVlan());
		}
		
		testCheckpoint(message, passed, points);
	}
	
	/**
	 * Asserts that multiple switch devices' ports of the same VLAN have the  
	 * given STP status.
	 * 
	 * <p>If or == true, at least one device must have the given status; if 
	 * false, all devices must meet this criteria.</p>
	 * 
	 * <p>Note that, if a single device doesn't run STP, the test will fail.</p>
	 * 
	 * <p>The devices must be specified in an 2-dimensional array with the 
	 * following format: <br />
	 * <code>new String[][] {
	 * 		new String[] { swName, swPort }, ...
	 * }</code> <br />
	 * where swName is the switch's name and swPort is the interface to check.
	 * </p>
	 * 
	 * @param swDevicesAndPorts The switch and ports to check 
	 *        (array of String[2] pairs).
	 * @param vlanSubnet The VLAN to check (subnet name).
	 * @param expPortStatus The expected port status string.
	 * @param or Whether to perform a boolean OR (true) or an AND (false) for 
	 *        the success status of the test.
	 * @param points The value (in points) of this test.
	 * @throws Exception if the PacketTracer topology is invalid.
	 */
	protected void assertMultipleSwSTPPortStatus(String[][] swDevicesAndPorts, 
				String vlanSubnet, String expPortStatus, boolean or, 
				double points) throws Exception {
		
		int vlanId = subnetMap.get(vlanSubnet).getVlan();
		boolean passed = ( or ? false : true );
		
		for (String[] pair : swDevicesAndPorts) {
			CiscoDevice sw = (CiscoDevice) deviceMap.get(pair[0]);
			String port = PTUtils.fullIfName(pair[1]);
			String portStatus = PTUtils.getSTPPortStatus(sw, vlanId, port);
			
			if (portStatus == null) {
				passed = false;
				break;
			} else {
				if (or)
					passed |= portStatus.equals(expPortStatus);
				else 
					passed &= portStatus.equals(expPortStatus);
			}
		}
		testCheckpoint("Check STP ports status on vlan " + vlanSubnet, 
				passed, points);
	}
	
	
	/* ========================================================================
	 *                          Static utility methods
	 */
	
	/**
	 * Reads the particular data for the specified student.
	 * 
	 * @param Nume Student's last name.
	 * @param Prenume Student's first name.
	 * @param Grupa Student's group.
	 * @return A map with all the variables and their values.
	 */
	public static HashMap<String, Integer> getParticularData(String Nume,
			String Prenume, String Grupa) {
		HashMap<String, Integer> hm = new HashMap<String, Integer>();
		
		try {
			String line;
			String ur = "http://elf.cs.pub.ro/rl/res/tema1/gen.php?nume="
					+ Nume + "&prenume=" + Prenume + "&grupa=" + Grupa;
			System.out.println("Generare date pentru: " + Nume + " " + Prenume
					+ " " + Grupa);
			URL tema1 = new URL(ur);
			URLConnection yc = tema1.openConnection();
			
			BufferedReader input = new BufferedReader(new InputStreamReader(
					yc.getInputStream()));
			
			while ((line = input.readLine()) != null) {
				String[] lines = line.split("<br/>");
				for (String entry : lines) {
					hm.put(entry.split(" = ")[0],
							Integer.parseInt(entry.split(" = ")[1]));
				}
			}
			
			input.close();
		} catch (Exception err) {
			err.printStackTrace();
		}
		return hm;
	}
	
	
}
