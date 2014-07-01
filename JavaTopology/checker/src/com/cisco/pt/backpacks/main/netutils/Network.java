package com.cisco.pt.backpacks.main.netutils;

import java.util.Arrays;

/**
 * The Network class encapsulates a subnet's identification data (address,
 * subnet mask) and some optional vlan and name tags.
 * 
 * <p>
 * Also implements several utility address parameters computation methods.
 * </p>
 */
public class Network {
	/**
	 * Network's symbolic name.
	 */
	private String name;
	
	/**
	 * Network's IP address (in dot-decimal format).
	 */
	private String address;
	
	/**
	 * Network's IP address components.
	 */
	private int[] addressComponents;
	
	/**
	 * Subnet mask (bits count).
	 */
	private int mask;
	
	/**
	 * Network's associated vlan number.
	 */
	private int vlan;
	
	
	/**
	 * Network address constructor (with full parameters initialization).
	 * 
	 * @param name Network's symbolic name.
	 * @param address Subnet's address (in dot-decimal notation).
	 * @param mask Subnet's mask (bits count).
	 * @param vlan The associated vlan number.
	 */
	public Network(String name, String address, int mask, int vlan) {
		this.name = name;
		this.address = address;
		this.mask = mask;
		this.vlan = vlan;
		
		// split the IPv4 into its decimal components
		String strComponents[] = address.split("\\.");
		addressComponents = new int[4];
		for (int i=0; i<4; i++)
			addressComponents[i] = Integer.parseInt(strComponents[i]);
	}
	
	/**
	 * Returns the network's name.
	 * @return Network's name.
	 */
	public String getVar() {
		return name;
	}
	
	/**
	 * Returns network's IP address.
	 * 
	 * @return Network's address, in dot-decimal format.
	 */
	public String getAddress() {
		return address;
	}
	
	/**
	 * Returns an array of the network address's components.
	 * 
	 * @return Array with 4 decimal values (A.B.C.D).
	 */
	public int[] getAddressComponents() {
		return Arrays.copyOf(addressComponents, 4);
	}
	
	/**
	 * Returns network's mask as bit count.
	 * 
	 * @return The number of '1' bits of the network's mask.
	 */
	public int getMask() {
		return mask;
	}
	
	/**
	 * Returns the i'th assignable address.
	 * 
	 * @param i The assignable address's index (starting from 1).
	 * @return The i'th assignable address of the network, in dot-decimal format.
	 */
	public String assignableAddress(int i) {
		String IP = addressComponents[0] + "."
				+ addressComponents[1] + "."
				+ addressComponents[2] + "."
				+ (addressComponents[3] + i);
		return IP;
	}
	
	/**
	 * Returns the network's associated vlan number.
	 * @return
	 */
	public int getVlan() {
		return vlan;
	}
	
	/**
	 * Sets (changes) the network's VLAN tag.
	 * @param vlan
	 */
	public void setVlan(int vlan) {
		this.vlan = vlan;
	}
	
	/**
	 * Returns the octal mask of the current instance (in dot-decimal format).
	 * 
	 * @return
	 */
	public String getOctalMask() {
		int remain = mask;
		
		String octalMask = "";
		if (remain >= 8) {
			octalMask += "255.";
			remain = remain - 8;
		} else {
			octalMask += ((int) (Math.pow(2, remain) - 1) << (8 - remain))
					+ ".";
			remain = 0;
		}
		if (remain >= 8) {
			octalMask += "255.";
			remain = remain - 8;
		} else {
			octalMask += ((int) (Math.pow(2, remain) - 1) << (8 - remain))
					+ ".";
			remain = 0;
		}
		if (remain >= 8) {
			octalMask += "255.";
			remain = remain - 8;
		} else {
			octalMask += ((int) (Math.pow(2, remain) - 1) << (8 - remain))
					+ ".";
			remain = 0;
		}
		if (remain >= 8) {
			octalMask += "255";
			remain = remain - 8;
		} else {
			octalMask += (int) (Math.pow(2, remain) - 1) << (8 - remain);
			remain = 0;
			
		}
		return octalMask;
	}
	
	/**
	 * Prints a string representation of the current Network instance.
	 * 
	 * <p>
	 * Warning: multi-line message.
	 * </p>
	 */
	@Override
	public String toString() {
		return "\nNume Variabila: " + name + "\nVlan: " + vlan + "\nNetaddr: "
				+ address + "\nMask: " + getOctalMask() + "\nFirstIP: "
				+ assignableAddress(1);
	}
	
}
