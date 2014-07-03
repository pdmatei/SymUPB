import com.cisco.pt.ipc.sim.port.Port;

/**
 * Created by Matei on 7/1/2014.
 */
class Wire {
	private Port p1;
	private Port p2;

	public Wire (Port p1, Port p2){
		this.p1 = p1;
		this.p2 = p2;
	}
	@Override
	public String toString(){
		return p1.getOwnerDevice().getName()+":"+p1.getName()+" "+p2.getOwnerDevice().getName()+":"+p2.getName();
	}
}
