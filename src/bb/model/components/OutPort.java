package bb.model.components;

/**
 * Outport
 * 
 * @author wall
 *
 */
public class OutPort extends Port {
	
	@Override
	public void setSignal(int signal) {
		this.signal = signal;
		if (linkedPoint != null) {
			for (int i = 0; i < linkedPoint.size(); i++) {
				linkedPoint.get(i).setSignal(signal);
			}
		}
		if (linkedPort != null && linkedPort instanceof InPort)
			linkedPort.setSignal(signal);
	}

}
