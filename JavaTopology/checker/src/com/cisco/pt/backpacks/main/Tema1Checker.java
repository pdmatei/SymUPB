package com.cisco.pt.backpacks.main;

import java.util.HashMap;

import com.cisco.pt.backpacks.main.netutils.Network;
import com.cisco.pt.backpacks.main.netutils.NetworkUtils;
import com.cisco.pt.ipc.sim.Device;
import com.cisco.pt.ipc.ui.IPC;
import com.cisco.pt.ptmp.PacketTracerSession;

/**
 * Tema1 checker class (2013-2014 version).
 */
public class Tema1Checker extends AbstractChecker {
	
	/**
	 * The opened PacketTracer session.
	 */
	private PacketTracerSession pts;
	
	/**
	 * The PacketTracer IPC object used to communicate with the PacketTracer 
	 * instance.
	 */
	private IPC ipc;
	
	/**
	 * Map with additional subnets to create.
	 */
	private final HashMap<String, Integer> additionalSubnets = 
			new HashMap<String, Integer>() {
		private static final long serialVersionUID = 1L;
		// initialize the map
		{
			put("IR4", 4); // Inter-router with 4 addresses
			put("IR3", 3); // Inter-router with 3 addresses
		}
	};
	
	/**
	 * A list of all important devices present in the PT file.
	 */
	private final String[] devicesList = new String[] {

			// Green zone
			"A1", "A2", "B1", "B2", 
			"GSW1", "GSW2", "GSW3", "GR1", 
			
			// Blue zone
			"E1", "E2", "F1", "F2", "G1", 
			"BSW1", "BSW2", "BSW3", "BR1", 

			// Orange zone
			"C1", "C2", "D1", "D2", 
			"OSW1", "OSW2", "OSW3", "OSW4", "OR1", 
			
			// Yellow zone
			"Apache - H1", "DNS - H2", 
			"Apache - I1", "DNS - I2", 
			"YSW2", "YSW1", "YR2", "YR1", 
			
			// ISP
			"SW1","SW2", "SW3", 
	};
	
	
	/**
	 * Initialize the checker with an established PT session to use for
	 * communicating with PT.
	 * 
	 * <p>
	 * The tested topology file must be already opened before initializing the
	 * checker.
	 * </p>
	 * 
	 * @param pts The PacketTracer session object.
	 * @param upc The PacketTracer IPC object.
	 * @param vars Personalized variables map.
	 */
	public Tema1Checker(PacketTracerSession pts, IPC ipc, HashMap<String, Integer> vars) {
		this.pts = pts;
		this.ipc = ipc;
		this.tler = this.pts.getEventManager().getTerminalLineEvents();
		
		// the parent network to subnet
		int vX = vars.remove("X");
		Network network = new Network("X", "150." + vX + ".0.0", 16, 0);
		
		// build the subnets map
		// create a subnet for all variables, except X
		HashMap<String, Integer> subnets = new HashMap<String, Integer>();
		subnets.putAll(vars);
		subnets.putAll(additionalSubnets);
		this.subnetMap = NetworkUtils.makeSubnets(network, subnets);
		
		// special formulas for inter-router VLAN numbers
		subnetMap.get("IR4").setVlan(
				(subnetMap.get("A").getVlan() + 
						subnetMap.get("B").getVlan() + 
						subnetMap.get("C").getVlan() + 
						subnetMap.get("D").getVlan() ) % 900 + 66);
		
		subnetMap.get("IR3").setVlan(
				(subnetMap.get("E").getVlan() + 
						subnetMap.get("F").getVlan() + 
						subnetMap.get("G").getVlan() + 
						subnetMap.get("H").getVlan() + 
						subnetMap.get("I").getVlan() ) % 900 + 66);
		
		// build the devices map
		this.deviceMap = new HashMap<String, Device>();
		for (String dev: devicesList) {
			deviceMap.put(dev, this.ipc.network().getDevice(dev));
		}
	}
	
	/**
	 * Main checker method.
	 */
	@Override
	public void runAll() throws Exception {
		// run all tests
		check0001();
		check0010();
		check0011();
		check0100();
		check0101();
		check0110();
		check0111();
		
		// show the total results
		echoTotal();
	}
	
	/**
	 * Test method for the first (1st) assignment.
	 * 
	 * @throws Exception if the PT topology is invalid.
	 */
	public void check0001() throws Exception {
		testHeader("0001. Subnetare");
		testResults("Total:");
	}
	
	/**
	 * Test method for the second (2nd) assignment.
	 * 
	 * @throws Exception if the PT topology is invalid.
	 */
	public void check0010() throws Exception {
		testHeader("0010. Adresare IP pe statii");
		
		assertPCIp("A1", "A", 1);
		assertPCIp("A2", "A", 1);
		assertPCIp("B1", "B", 1);
		assertPCIp("B2", "B", 1);
		assertPCIp("C1", "C", 1);
		assertPCIp("C2", "C", 1);
		assertPCIp("D1", "D", 1);
		assertPCIp("D2", "D", 1);
		assertPCIp("E1", "E", 1);
		assertPCIp("E2", "E", 1);
		assertPCIp("F1", "F", 1);
		assertPCIp("F2", "F", 1);
		assertPCIp("G1", "G", 1);
		assertPCIp("Apache - H1", "H", 1);
		assertPCIp("DNS - H2", "H", 1);
		assertPCIp("Apache - I1", "I", 1);
		assertPCIp("DNS - I2", "I", 1);
		
		assertMultiplePCDNS(new String[] { 
					"A1", "A2", "B1", "B2", "C1", "C2", "D1", "D2" }, 
				subnetMap.get("H").assignableAddress(3), "Green&Orange", 1);
		
		assertMultiplePCDNS(new String[] { "E1", "E2", "F1", "F2", "G1" }, 
				subnetMap.get("I").assignableAddress(3), "Blue", 1);
		
		rescaleCurrentScore(20.0);
		testResults("Total:");
	}
	
	/**
	 * Test method for the third (3rd) assignment.
	 * 
	 * @throws Exception if the PT topology is invalid.
	 */
	public void check0011() throws Exception {
		testHeader("0011. Configurare porturi switchuri");
		
		// Green
		assertSwPortVlan("GSW2", "Fa0/1", "A", 1); // 1
		assertSwPortVlan("GSW2", "Fa0/2", "B", 1);
		assertSwPortVlan("GSW3", "Fa0/1", "A", 1); // 2
		assertSwPortVlan("GSW3", "Fa0/2", "B", 1);
		
		assertSwPortTrunk("GSW2", "Fa0/22", new String[] { "A", "B" }, null, 1);
		assertSwPortTrunk("GSW2", "Fa0/24", new String[] { "A", "B" }, null, 1);
		assertSwPortTrunk("GSW3", "Fa0/22", new String[] { "A", "B" }, null, 1);
		assertSwPortTrunk("GSW3", "Fa0/24", new String[] { "A", "B" }, null, 1);
		assertSwPortTrunk("GSW1", "Fa0/21", new String[] { "A", "B" }, null, 1);
		assertSwPortTrunk("GSW1", "Fa0/22", new String[] { "A", "B" }, null, 1);
		assertSwPortTrunk("GSW1", "Fa0/23", new String[] { "A", "B" }, null, 1);
		assertSwPortTrunk("GSW1", "Fa0/24", new String[] { "A", "B" }, null, 1);
		assertSwPortTrunk("GSW1", "Fa0/1", new String[] { "A", "B" }, null, 1);
		
		// Orange
		assertSwPortVlan("OSW4", "Fa0/1", "C", 1); // C1
		assertSwPortVlan("OSW4", "Fa0/2", "D", 1);
		assertSwPortVlan("OSW3", "Fa0/1", "C", 1); // C2
		assertSwPortVlan("OSW3", "Fa0/2", "D", 1);
		
		assertSwPortVlan("OSW1", "Fa0/1", "C", 1); // OR1
		assertSwPortVlan("OSW2", "Fa0/1", "D", 1); // OR1
		
		assertSwPortTrunk("OSW1", "Gig1/1", new String[] { "C", "D" }, null, 1);
		assertSwPortTrunk("OSW1", "Gig1/2", new String[] { "C", "D" }, null, 1);
		assertSwPortTrunk("OSW2", "Gig1/1", new String[] { "C", "D" }, null, 1);
		assertSwPortTrunk("OSW2", "Gig1/2", new String[] { "C", "D" }, null, 1);
		assertSwPortTrunk("OSW3", "Gig1/1", new String[] { "C", "D" }, null, 1);
		assertSwPortTrunk("OSW3", "Gig1/2", new String[] { "C", "D" }, null, 1);
		assertSwPortTrunk("OSW4", "Gig1/1", new String[] { "C", "D" }, null, 1);
		assertSwPortTrunk("OSW4", "Gig1/2", new String[] { "C", "D" }, null, 1);
		
		// Blue
		assertSwPortVlan("BSW2", "Fa0/1", "E", 1); // E1
		assertSwPortVlan("BSW2", "Fa0/2", "E", 1); // E2
		assertSwPortVlan("BSW3", "Fa0/1", "F", 1); // F1
		assertSwPortVlan("BSW3", "Fa0/2", "F", 1); // F2
		assertSwPortVlan("BSW1", "Fa0/2", "G", 1); // G1
		
		assertSwPortTrunk("BSW1", "Gig1/1", new String[] { "E", "F" }, "E", 1);
		assertSwPortTrunk("BSW1", "Gig1/2", new String[] { "E", "F" }, "F", 1);
		assertSwPortTrunk("BSW1", "Fa0/1", new String[] { "E", "F", "G" }, null, 1);
		assertSwPortTrunk("BSW2", "Gig1/1", new String[] { "E", "F" }, "E", 1);
		assertSwPortTrunk("BSW2", "Gig1/2", new String[] { "E", "F" }, null, 1);
		assertSwPortTrunk("BSW3", "Gig1/2", new String[] { "E", "F" }, "F", 1);
		assertSwPortTrunk("BSW3", "Gig1/1", new String[] { "E", "F" }, null, 1);
		
		// Yellow
		assertSwPortVlan("YSW1", "Fa0/1", "H", 1); // YR1
		assertSwPortVlan("YSW2", "Fa0/1", "I", 1); // YR2
		
		assertSwPortVlan("YSW1", "Fa0/24", "H", 1); // H1
		assertSwPortVlan("YSW1", "Fa0/23", "H", 1); // H2
		assertSwPortVlan("YSW2", "Fa0/24", "I", 1); // I1
		assertSwPortVlan("YSW2", "Fa0/23", "I", 1); // I2
		
		rescaleCurrentScore(20.0);
		testResults("Total:");
	}
	
	/**
	 * Test method for the fourth (4th) assignment.
	 * 
	 * @throws Exception if the PT topology is invalid.
	 */
	public void check0100() throws Exception {
		testHeader("0100. Inter-VLAN routing");

		// Green
		assertRouterIp("GR1", "Fa0/0", "A", 1, true, 1);
		assertRouterIp("GR1", "Fa0/0", "B", 1, true, 1);
		
		// Orange
		assertRouterIp("OR1", "Fa0/0", "C", 1, false, 1);
		assertRouterIp("OR1", "Fa0/1", "D", 1, false, 1);
		
		// Blue
		assertRouterIp("BR1", "Fa0/0", "E", 1, true, 1);
		assertRouterIp("BR1", "Fa0/0", "F", 1, true, 1);
		assertRouterIp("BR1", "Fa0/0", "G", 1, true, 1);
		
		// Yellow
		assertRouterIp("YR1", "Fa0/0", "H", 1, false, 1);
		assertRouterIp("YR2", "Fa0/0", "I", 1, false, 1);
		
		rescaleCurrentScore(20.0);
		testResults("Total:");
	}
	
	/**
	 * Test method for the fifth (5th) assignment.
	 * 
	 * @throws Exception if the PT topology is invalid.
	 */
	public void check0101() throws Exception {
		testHeader("0101. Conectivitate inter-rutere");
		
		// IR4 = {  YR1(Fa1/0), YR2(Fa1/0), GR1, OR1 }
		assertRouterIp("YR1", "Fa1/0", "IR4", 1, false, 1);
		assertRouterIp("YR2", "Fa1/0", "IR4", 2, false, 1);
		assertRouterIp("GR1", "Fa0/1", "IR4", 3, false, 1);
		assertRouterIp("OR1", "Fa1/0", "IR4", 4, false, 1);
		
		// IR3 = { BR1, YR1(Fa1/1), YR2(Fa1/1)  }
		assertRouterIp("YR1", "Fa1/1", "IR3", 1, false, 1);
		assertRouterIp("YR2", "Fa1/1", "IR3", 2, false, 1);
		assertRouterIp("BR1", "Fa0/1", "IR3", 3, false, 1);
		
		// all SW-R ports are access
		assertSwPortVlan("SW3", "Fa0/24", "IR4", 1); // YR1(Fa1/0)
		assertSwPortVlan("SW3", "Fa0/23", "IR4", 1); // YR2(Fa1/0)
		assertSwPortVlan("SW1", "Fa0/1", "IR4", 1); // GR1
		assertSwPortVlan("SW3", "Fa0/1", "IR4", 1); // OR1
		
		assertSwPortVlan("SW2", "Fa0/24", "IR3", 1); // YR1(Fa1/1)
		assertSwPortVlan("SW2", "Fa0/23", "IR3", 1); // YR2(Fa1/1)
		assertSwPortVlan("SW2", "Fa0/1", "IR3", 1); // BR1
		
		// go home switch, you're trunk
		assertSwPortTrunk("SW1", "Gig1/1", new String[] { "IR4", "IR3" }, null, 1);
		assertSwPortTrunk("SW1", "Gig1/2", new String[] { "IR4", "IR3" }, null, 1);
		assertSwPortTrunk("SW2", "Gig1/1", new String[] { "IR4", "IR3" }, null, 1);
		assertSwPortTrunk("SW2", "Gig1/2", new String[] { "IR4", "IR3" }, null, 1);
		assertSwPortTrunk("SW3", "Gig1/1", new String[] { "IR4", "IR3" }, null, 1);
		assertSwPortTrunk("SW3", "Gig1/2", new String[] { "IR4", "IR3" }, null, 1);
		
		// IR3 routers: check routes to IR4
		assertRouteMultipleNextHops("BR1", "IR4", new String[]{
					subnetMap.get("IR3").assignableAddress(1), // YR1
					subnetMap.get("IR3").assignableAddress(2), // YR2
				}, 1);
		
		// IR4 routers: check routes to IR3
		assertRouteMultipleNextHops("OR1", "IR3", new String[]{
				subnetMap.get("IR4").assignableAddress(1), // YR1
				subnetMap.get("IR4").assignableAddress(2), // YR2
			}, 1);
		assertRouteMultipleNextHops("GR1", "IR3", new String[]{
				subnetMap.get("IR4").assignableAddress(1), // YR1
				subnetMap.get("IR4").assignableAddress(2), // YR2
			}, 1);
		
		rescaleCurrentScore(10.0);
		testResults("Total:");
	}
	
	/**
	 * Test method for the sixth (6th) assignment.
	 * 
	 * @throws Exception if the PT topology is invalid.
	 */
	public void check0110() throws Exception {
		testHeader("0110. Configurare STP");
		
		// Blue backup connection
		assertMultipleSwSTPPortStatus(new String[][]{
				new String[] { "BSW2", "Gig1/2" },
				new String[] { "BSW3", "Gig1/1" },
			}, "E", "BLK", true, 1);
		
		// IR4: backup switch SW2
		assertMultipleSwSTPPortStatus(new String[][]{
				new String[] { "SW1", "Gig1/1" },
				new String[] { "SW2", "Gig1/1" },
				new String[] { "SW2", "Gig1/2" },
				new String[] { "SW3", "Gig1/1" },
			}, "IR4", "BLK", true, 1);
		
		// IR3: link between SW1 and SW3 is backup
		assertMultipleSwSTPPortStatus(new String[][]{
				new String[] { "SW1", "Gig1/2" },
				new String[] { "SW3", "Gig1/2" },
			}, "IR3", "BLK", true, 1);
		
		// B1 must use Fa0/24 of GSW2
		assertMultipleSwSTPPortStatus(new String[][]{
				new String[] { "GSW1", "Fa0/22" },
				new String[] { "GSW2", "Fa0/22" },
			}, "B", "BLK", true, 1);
		// B2 must use Fa0/24 of GSW3
		assertMultipleSwSTPPortStatus(new String[][]{
				new String[] { "GSW1", "Fa0/21" },
				new String[] { "GSW3", "Fa0/22" },
			}, "B", "BLK", true, 1);
		
		// C: use OSW2 as backup
		assertMultipleSwSTPPortStatus(new String[][]{
				new String[] { "OSW2", "Gig1/1" },
				new String[] { "OSW3", "Gig1/1" },
				new String[] { "OSW2", "Gig1/2" },
				new String[] { "OSW1", "Gig1/2" },
			}, "C", "BLK", true, 1);
		
		// D: use OSW1 as backup
		assertMultipleSwSTPPortStatus(new String[][]{
				new String[] { "OSW1", "Gig1/1" },
				new String[] { "OSW4", "Gig1/1" },
				new String[] { "OSW1", "Gig1/2" },
				new String[] { "OSW2", "Gig1/2" },
			}, "D", "BLK", true, 1);
		
		
		rescaleCurrentScore(10.0);
		testResults("Total:");
	}
	
	/**
	 * Test method for the seventh (7th) assignment.
	 * 
	 * @throws Exception if the PT topology is invalid.
	 */
	public void check0111() throws Exception {
		testHeader("0111. Conectivitate punct la punct ");
		
		// GR1 routes
		assertRoute("GR1", "C", // via OR1
				subnetMap.get("IR4").assignableAddress(4), 1);
		assertRoute("GR1", "D", // via OR1 
				subnetMap.get("IR4").assignableAddress(4), 1);
		assertRoute("GR1", "E", // via YR1
				subnetMap.get("IR4").assignableAddress(1), 1);
		assertRoute("GR1", "F", // via YR1
				subnetMap.get("IR4").assignableAddress(1), 1);
		assertRoute("GR1", "G", // via YR1
				subnetMap.get("IR4").assignableAddress(1), 1);
		assertRoute("GR1", "H", // via YR2
				subnetMap.get("IR4").assignableAddress(2), 1);
		assertRoute("GR1", "I", // via YR2
				subnetMap.get("IR4").assignableAddress(2), 1);
		assertAltRoute("GR1", "H", // via YR1
				subnetMap.get("IR4").assignableAddress(1), 1);
		assertAltRoute("GR1", "I", // via YR1
				subnetMap.get("IR4").assignableAddress(1), 1);
		
		// OR1 routes
		assertRoute("OR1", "A", // via GR1
				subnetMap.get("IR4").assignableAddress(3), 1);
		assertRoute("OR1", "B", // via GR1 
				subnetMap.get("IR4").assignableAddress(3), 1);
		assertRoute("OR1", "E", // via YR1
				subnetMap.get("IR4").assignableAddress(1), 1);
		assertRoute("OR1", "F", // via YR1
				subnetMap.get("IR4").assignableAddress(1), 1);
		assertRoute("OR1", "G", // via YR1
				subnetMap.get("IR4").assignableAddress(1), 1);
		assertRoute("OR1", "H", // via YR2
				subnetMap.get("IR4").assignableAddress(2), 1);
		assertRoute("OR1", "I", // via YR2
				subnetMap.get("IR4").assignableAddress(2), 1);
		assertAltRoute("OR1", "H", // via YR1
				subnetMap.get("IR4").assignableAddress(1), 1);
		assertAltRoute("OR1", "I", // via YR1
				subnetMap.get("IR4").assignableAddress(1), 1);
		
		// BR1 routes
		assertRouteMultipleNextHops("BR1", "A", new String[] { 
				// via YR1 or YR2 
				subnetMap.get("IR3").assignableAddress(1), 
				subnetMap.get("IR3").assignableAddress(2)
			}, 1);
		assertRouteMultipleNextHops("BR1", "B", new String[] { 
				// via YR1 or YR2 
				subnetMap.get("IR3").assignableAddress(1), 
				subnetMap.get("IR3").assignableAddress(2)
			}, 1);
		assertRouteMultipleNextHops("BR1", "C", new String[] { 
				// via YR1 or YR2 
				subnetMap.get("IR3").assignableAddress(1), 
				subnetMap.get("IR3").assignableAddress(2)
			}, 1);
		assertRouteMultipleNextHops("BR1", "D", new String[] { 
				// via YR1 or YR2 
				subnetMap.get("IR3").assignableAddress(1), 
				subnetMap.get("IR3").assignableAddress(2)
			}, 1);
		assertRoute("BR1", "H", // via YR1
				subnetMap.get("IR3").assignableAddress(1), 1);
		assertRoute("BR1", "I", // via YR1
				subnetMap.get("IR3").assignableAddress(1), 1);
		assertAltRoute("BR1", "H", // via YR2
				subnetMap.get("IR3").assignableAddress(2), 1);
		assertAltRoute("BR1", "I", // via YR2
				subnetMap.get("IR3").assignableAddress(2), 1);
		
		// YR1 routes
		assertRouteViaInterface("YR1", "I", "Se0/3/0", 1);
		assertRoute("YR1", "A", // via GR1
				subnetMap.get("IR4").assignableAddress(3), 1);
		assertRoute("YR1", "B", // via GR1 
				subnetMap.get("IR4").assignableAddress(3), 1);
		assertRoute("YR1", "C", // via OR1
				subnetMap.get("IR4").assignableAddress(4), 1);
		assertRoute("YR1", "D", // via OR1 
				subnetMap.get("IR4").assignableAddress(4), 1);
		assertRoute("YR1", "E", // via BR1
				subnetMap.get("IR3").assignableAddress(3), 1);
		assertRoute("YR1", "F", // via BR1
				subnetMap.get("IR3").assignableAddress(3), 1);
		assertRoute("YR1", "G", // via BR1
				subnetMap.get("IR3").assignableAddress(3), 1);
		
		
		// YR2 routes
		assertRouteViaInterface("YR2", "H", "Se0/3/0", 1);
		assertRoute("YR2", "A", // via GR1
				subnetMap.get("IR4").assignableAddress(3), 1);
		assertRoute("YR2", "B", // via GR1 
				subnetMap.get("IR4").assignableAddress(3), 1);
		assertRoute("YR2", "C", // via OR1
				subnetMap.get("IR4").assignableAddress(4), 1);
		assertRoute("YR2", "D", // via OR1 
				subnetMap.get("IR4").assignableAddress(4), 1);
		
		assertRouteViaInterface("YR2", "E", "Se0/3/0", 1);
		assertRouteViaInterface("YR2", "F", "Se0/3/0", 1);
		assertRouteViaInterface("YR2", "G", "Se0/3/0", 1);
		
		if (totalScore > 0) {
			assertPingPCtoPC("A1", "C1", 1);
			assertPingPCtoPC("A1", "D2", 1);
			assertPingPCtoPC("A1", "E1", 1);
			assertPingPCtoPC("A1", "F2", 1);
			assertPingPCtoPC("A1", "G1", 1);
			assertPingPCtoPC("A1", "DNS - H2", 1);
			assertPingPCtoPC("A1", "Apache - I1", 1);
			assertPingPCtoPC("G1", "C2", 1);
			assertPingPCtoPC("E2", "D1", 1);
			assertPingPCtoPC("D2", "F2", 1);
			
		} else {
			addScore(true, 11);
		}
		
		rescaleCurrentScore(20.0);
		testResults("Total:");
	}
	
}
