package bb.model.components;

import java.util.ArrayList;
import java.util.List;

import bb.model.gate.PutGate;
import bb.model.interfaces.OrientationE;

/**
 * INPUT
 * 
 * @author wall
 *
 */
public class Component_input extends Component {
	/**
	 * 设置与门参数
	 * 
	 * @param x
	 * @param y
	 * @param d
	 */
	public Component_input(int x, int y, OrientationE d) {
		if (x < 2 * RADIUS || y < 2 * RADIUS) {
			System.out.println("位置小于控件最小范围");
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
		portQ = new OutPort();
		// It is input component
		computePutPosition(true, ori);
	}

	public void setSignal(int signal) {
		//if (portQ != null)
			portQ.setSignal(signal);
	}

	@Override
	public void updateOut() {
		portQ.setSignal(portQ.getSignal());
	}

	@Override
	public void reSet() {
		
	}

	@Override
	public List<Port> getPorts() {
		List<Port> ports = new ArrayList<Port>();
		ports.add(portQ);
		return ports;
	}

}
