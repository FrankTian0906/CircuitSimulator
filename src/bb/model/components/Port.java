package bb.model.components;

import java.util.ArrayList;
import java.util.List;

import bb.model.interfaces.SignalListener;
import bb.model.link.EndPoint;

/**
 * Port
 * 
 * @author wall
 *
 */
public class Port {
	/**
	 * the position connected with a component
	 */
	public int baseX;
	public int baseY;
	/**
	 *  the position at witch can connected with a link
	 */
	public int enterX;
	public int enterY;
	// port signal(default = -1)
	public int signal = -1;
	public SignalListener listner;
	/**
	 * 已连接的端口
	 */
	public Port linkedPort;
	/**
	 * the end point of a link that connected with THIS port
	 */
	public List<EndPoint> linkedPoint = new ArrayList<EndPoint>();

	public void setPoint(int bx, int by, int ex, int ey) {
		baseX = bx;
		baseY = by;
		enterX = ex;
		enterY = ey;
	}

	/**
	 * update signal
	 */
	public void updateSignal() {
		if (linkedPoint != null) {
			for (int i = 0; i < linkedPoint.size(); i++) {
				signal = linkedPoint.get(i).getSignal();
			}
		}
		if (linkedPort != null) {
			signal = linkedPort.getSignal();
		}
	}

	public int getSignal() {
		return signal;
	}

	public void reSet() {
		signal = -1;
	}

	public void setSignal(int signal) {
		this.signal = signal;
		if (linkedPoint != null) {
			for (int i = 0; i < linkedPoint.size(); i++) {
				linkedPoint.get(i).setSignal(signal);
			}
		}
	}

	/**
	 * 
	 * if the position is same with the port, adding the end of link.
	 * @param end
	 * @param port
	 * @return
	 */
	public boolean isLinked(EndPoint end, Port port) {
		if (end != null && this.enterX == end.getX() && this.enterY == end.getY()) {
			setLinkedPoint(end);
			return true;
		} else if (port != null && this.enterX == port.getEnterX() && this.enterY == port.getEnterY()) {
			setLinkedPort(port);
			return true;
		}
		return false;
	}

	public int getBaseX() {
		return baseX;
	}

	public void setBaseX(int baseX) {
		this.baseX = baseX;
	}

	public int getBaseY() {
		return baseY;
	}

	public void setBaseY(int baseY) {
		this.baseY = baseY;
	}

	public int getEnterX() {
		return enterX;
	}

	public void setEnterX(int enterX) {
		this.enterX = enterX;
	}

	public int getEnterY() {
		return enterY;
	}

	public void setEnterY(int enterY) {
		this.enterY = enterY;
	}

	public Port getLinkedPort() {
		return linkedPort;
	}

	public void setLinkedPort(Port linkedPort) {
		this.linkedPort = linkedPort;
	}

	//public EndPoint getLinkedPoint() {
	//	return linkedPoint;
	//}

	public void setLinkedPoint(EndPoint linkedPoint) {
		this.linkedPoint.add(linkedPoint);
	}

}
