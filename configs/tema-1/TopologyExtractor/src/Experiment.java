import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;

import com.cisco.pt.ipc.events.TerminalLineEventRegistry;
import com.cisco.pt.ipc.sim.Device;
import com.cisco.pt.ipc.sim.FrameInstance;
import com.cisco.pt.ipc.sim.Network;
import com.cisco.pt.ipc.sim.port.Link;
import com.cisco.pt.ipc.sim.port.Port;
import com.cisco.pt.ipc.system.Simulation;
import com.cisco.pt.ipc.ui.IPC;
import com.cisco.pt.ipc.ui.LogicalWorkspace;

public class Experiment extends Backpack {

	@Override
	protected void internalRun() throws Exception {
		Thread.sleep(31000);
		IPC ipc =ipcFactory.getIPC();
		// get a device from the topology
		/*
		Device dev=ipc.network().getDevice("Switch1");
		Port p = dev.getPort("FastEthernet0/1");
		Link l = p.getLink();
		
		Port p1 = ipc.network().getDevice("Switch0").getPort("FastEthernet0/2");
		Port p2 = ipc.network().getDevice("Switch2").getPort("FastEthernet0/2");	
		Link l1 = p1.getLink();
		Link l2 = p2.getLink();
		*/
		genClickTopo(ipc);
		
		
		
		
	}
	
	private void genClickTopo(IPC ipc) {
		List<Wire> wires = getLinks(ipc);
		List<Device> devices = getDevices(ipc);
		Device dev=ipc.network().getDevice("Switch0");
		TerminalLineEventRegistry tler = this.packetTracerSession.getEventManager().getTerminalLineEvents();
		
		String span = PTUtils.enterCommand(dev,tler,"show spanning-tree",36000);
		String mac = PTUtils.enterCommand(dev,tler,"show mac-address-table",36000);
		//System.out.println(mac);
		//ClickObject o = ClickObjectFactory.build(dev,this);
		System.out.println(mac);
		System.out.println(span);
		
	}
	private List<Device> getDevices (IPC ipc){
		List<Device> dl = new LinkedList<Device>();
		Network net = ipc.network();
		
		int nrDevices = net.getDeviceCount();
		for (int i=0; i<nrDevices; i++)
			dl.add(net.getDeviceAt(i));
		return dl;
	}
	
	private List<Wire> getLinks (IPC ipc){
		
		//build complete active port list
		LinkedList<Port> pl = new LinkedList<Port>();
		Network net = ipc.network();
		
		for(Device d:getDevices(ipc)){
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
