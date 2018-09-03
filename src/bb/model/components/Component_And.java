package bb.model.components;

import java.util.ArrayList;
import java.util.List;

import bb.model.gate.AndGate;
import bb.model.interfaces.OrientationE;

/**
 * AND
 * 
 * @author wall
 *
 */
public class Component_And extends Component {
	/**
	 * set position
	 * 
	 * @param x
	 * @param y
	 * @param d
	 */
	public Component_And(int x, int y, OrientationE d) {
		if (x < 2 * RADIUS || y < 2 * RADIUS) {
			//System.out.println("位置小于控件最小范围");
			return;
		}
		this.x = x;
		this.y = y;
		this.d = d;
		comKind = new AndGate();
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
		//System.out.println("add  result:" + result);
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
