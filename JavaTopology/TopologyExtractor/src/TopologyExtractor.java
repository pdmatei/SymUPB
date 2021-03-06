import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;

import com.cisco.pt.ipc.enums.DeviceType;
import com.cisco.pt.ipc.events.TerminalLineEventRegistry;
import com.cisco.pt.ipc.sim.Device;
import com.cisco.pt.ipc.sim.Network;
import com.cisco.pt.ipc.sim.port.Port;
import com.cisco.pt.ipc.ui.IPC;

public class TopologyExtractor extends Backpack {

	
	@Override
	protected void internalRun() throws Exception {
		Thread.sleep(30000);
		List<Device> devices = getDevices();
		List<Wire> wires = getWires();
		ItemWriter devs = new ItemWriter(Names.PATH+Names.DEVICE_FILE);
		for (Device d:devices){
			devs.write(d.getName()+" "+d.getType());
			
			if (d.getType().equals(DeviceType.SWITCH)){
				ItemWriter span = new ItemWriter(Names.PATH+Names.SPAN_PREFIX+d.getName()+Names.SUFFIX);
				ItemWriter mac = new ItemWriter(Names.PATH+Names.MAC_PREFIX+d.getName()+Names.SUFFIX);
				
				TerminalLineEventRegistry tler = this.packetTracerSession.getEventManager().getTerminalLineEvents();
				
				String spanOutput = PTUtils.enterCommand(d,tler,"show spanning-tree",36000);
				String macOutput = PTUtils.enterCommand(d,tler,"show mac-address-table",36000);
				
				span.write(spanOutput);
				mac.write(macOutput);
				
				span.close();
				mac.close();
			}
		}
		devs.close();
		
		ItemWriter wrs = new ItemWriter(Names.PATH+Names.WIRE_FILE);
		for (Wire w:wires){
			wrs.write(w.toString());
		}
		wrs.close();
		
	}
	
	private List<Device> getDevices (){
		IPC ipc =ipcFactory.getIPC();
		List<Device> dl = new LinkedList<Device>();
		Network net = ipc.network();
		
		int nrDevices = net.getDeviceCount();
		for (int i=0; i<nrDevices; i++)
			dl.add(net.getDeviceAt(i));
		return dl;
	}
	
	private List<Wire> getWires (){
		IPC ipc =ipcFactory.getIPC();
		//build complete active port list
		LinkedList<Port> pl = new LinkedList<Port>();
		Network net = ipc.network();
		
		for(Device d:getDevices()){
			//System.out.println(d.getName());
			int nrPorts = d.getPortCount();
			for (int j=0; j<nrPorts; j++){
				Port p = d.getPortAt(j);
				if (p.isPortUp())
					pl.add(p);
			}
		}
		//build complete link list
		LinkedList<Wire> wl = new LinkedList<Wire>();
		for (Port p1:pl){
			for (Port p2:pl){
				if (p1 != p2 && p1.getLink().getObjectUUID().equals(p2.getLink().getObjectUUID())){
					wl.add(new Wire(p1,p2));
				}
			}
		}
		return wl;
	}

	@Override
	protected String getTargetFile() throws Exception {
		return "Trial1.pkt";
	}

	@Override
	protected Log getLog() {
		// TODO Auto-generated method stub
		return null;
	}

}
