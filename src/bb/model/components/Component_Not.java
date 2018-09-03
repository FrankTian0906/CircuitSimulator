package bb.model.components;

import java.util.ArrayList;
import java.util.List;

import bb.model.gate.NotGate;
import bb.model.interfaces.OrientationE;

/**
 * NOT
 * 
 * @author wall
 *
 */
public class Component_Not extends Component {
	/**
	 * set position
	 * 
	 * @param x
	 * @param y
	 * @param d
	 */
	public Component_Not(int x, int y, OrientationE d) {
		if (x < 2 * RADIUS || y < 2 * RADIUS) {
			//System.out.println("位置小于控件最小范围");
			return;
		}
		this.x = x;
		this.y = y;
		this.d = d;
		comKind = new NotGate();
		setPort(x, y, d);
	}

	@Override
	public void setPort(int x, int y, OrientationE ori) {
		portA = new InPort();
		portQ = new OutPort();
		// It is a single input port
		computePortPosition(true, ori);
	}

	@Override
	public void updateOut() {
		portA.updateSignal();
		int result = compute(portA.getSignal());
		portQ.setSignal(result);
	}

	@Override
	public List<Port> getPorts() {
		List<Port> ports = new ArrayList<Port>();
		ports.add(portA);
		ports.add(portQ);
		return ports;
	}

}
