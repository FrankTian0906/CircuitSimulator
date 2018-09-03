package bb.model.components;

import java.util.ArrayList;
import java.util.List;

import bb.model.gate.PutGate;
import bb.model.interfaces.OrientationE;

/**
 * OUTPUT
 * just print the signal out
 * 
 * @author wall
 *
 */
public class Component_output extends Component {
	/**
	 * set position
	 * 
	 * @param x
	 * @param y
	 * @param d
	 */
	public Component_output(int x, int y, OrientationE d) {
		if (x < 2 * RADIUS || y < 2 * RADIUS) {
			//System.out.println("位置小于控件最小范围");
			return;
		}
		this.x = x;
		this.y = y;
		this.d = d;
		comKind = new PutGate();
		setPort(x, y, d);
	}

	@Override
	public void setPort(int x, int y, OrientationE ori) {
		portA = new InPort();
		// It is NOT a input component
		computePutPosition(false, ori);
	}

	public void setSignal(int signal) {
		portA.setSignal(signal);
		System.out.println("get the Signal is:" + portA.getSignal());
	}

	@Override
	public void updateOut() {
		portA.updateSignal();
//		int result = compute(portA.getSignal());
		// System.out.println("The value of the OutPut  is :" + result);
	}

	@Override
	public List<Port> getPorts() {
		List<Port> ports = new ArrayList<Port>();
		ports.add(portA);
		return ports;
	}

}
