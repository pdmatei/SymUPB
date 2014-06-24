package com.cisco.pt.backpacks.main.netutils;

import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Utility class that implements various network-related methods like IPv4 
 * address manipulation methods and the VLSM subnetting algorithm.
 */
public class NetworkUtils {
	
	/**
	 * VLSM subnet-generation algorithm. Splits a large network into lesser 
	 * subnets.
	 * 
	 * <p>The input map's keys are the symbolic names of the subnets, each 
	 * having associated the required number of assignable addresses.</p>
	 * 
	 * <p>The algorithm first sorts the subnets by their assignable address 
	 * counts, in descending order, then starts to map a subnet to the 
	 * nearest power of two.</p>
	 * 
	 * @param network The network to split into subnets.
	 * @param subnetDesc A map with the subnets to generate and their 
	 *        desired assignable addresses count.
	 * @return The generated subnets using the VLSM algorithm.
	 */
	public static HashMap<String, Network> makeSubnets(
				Network network, 
				HashMap<String, Integer> subnetDesc) {
		HashMap<String, Network> subnets = new HashMap<String, Network>();
		
		int[] addrComponents = network.getAddressComponents();
		SortedSet<Integer> sm = new TreeSet<Integer>(subnetDesc.values());
		
		while (sm.size() != 0) {
			Integer last = sm.last() + 2;
			
			last--;
			last |= last >> 1;
			last |= last >> 2;
			last |= last >> 4;
			last |= last >> 8;
			last |= last >> 16;
			last++;
			
			for (String key : subnetDesc.keySet()) {
				if (subnetDesc.get(key).equals(sm.last())) {
					Network net = new Network(key, 
							addrComponents[0] + "." + addrComponents[1] + "."
							+ addrComponents[2] + "." + addrComponents[3], 
							32 - (int) log2(last), sm.last());
					subnets.put(key, net);
					break;
				}
			}
			
			addrComponents[3] += last;
			if (addrComponents[3] > 255) {
				addrComponents[3] = addrComponents[3] % 256;
				addrComponents[2] = addrComponents[2] + 1;
			}
			sm.remove(sm.last());
		}
		
		return subnets;
	}
	
	/**
	 * Computes the base2 logarithm.
	 * 
	 * @param d The target number.
	 * @return log2(d)
	 */
	public static double log2(double d) {
		return Math.log(d) / Math.log(2.0);
	}
	
	/**
	 * Truncates the specified double.
	 * 
	 * @param x The number to truncate.
	 * @return The truncated number.
	 */
	public static double truncate(double x) {
		long y = (long) (x * 10);
		return (double) y / 10;
	}
	
}
