package bb.model.components;

import java.util.ArrayList;
import java.util.List;

import bb.model.gate.OrGate;
import bb.model.interfaces.OrientationE;

/**
 * OR
 * 
 * @author wall
 *
 */
public class Component_Or extends Component {
	/**
	 * set position
	 * 
	 * @param x
	 * @param y
	 * @param d
	 */
	public Component_Or(int x, int y, OrientationE d) {
		this.x = x;
		this.y = y;
		this.d = d;
		comKind = new OrGate();
		setPort(x, y, d);
	}

	@Override
	public void setPort(int x, int y, OrientationE ori) {
		portA = new InPort();
		portB = new InPort();
		portQ = new OutPort();
		// It is NOT a single input port
		computePortPosition(false, ori);
	}

	@Override
	public void updateOut() {
		portA.updateSignal();
		portB.updateSignal();
		int result = compute(portA.getSignal(), portB.getSignal());
		//System.out.println("or  result:" + result);
		portQ.setSignal(result);
	}

	@Override
	public List<Port> getPorts() {
		List<Port> ports = new ArrayList<Port>();
		ports.add(portA);
		ports.add(portB);
		ports.add(portQ);
		return ports;
	}

}
