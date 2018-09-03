package bb.model.link;

import bb.model.components.Port;
import bb.model.interfaces.SignalListener;

/**
 * A end point of a link
 * 
 * @author wall
 *
 */
public class EndPoint implements SignalListener {
	public int x;
	public int y;
	public int signal = -1;
	private SignalListener listner;

	public EndPoint(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * 判断是否与某个端口相连并绑定信号监听器
	 * Is it connected with one port and with a signal listener
	 * @param port
	 * @return
	 */
	public boolean isLinked(final Port port) {
		if (this.x == port.getEnterX() && this.y == port.getEnterY()) {
			System.out.println(" The end of a link is nonnected with a port!");
			port.setLinkedPoint(this);
			return true;
		}
		return false;
	}
	
	public void reSet(){
		signal = -1;
	}

	@Override
	public void signalChanged(int signal) {
		if (this.signal != signal && listner != null) {
			listner.signalChanged(signal);
			this.signal = signal;
		}
	}

	public void setSignal(int signal) {
		if (this.signal != signal && listner != null) {
			listner.signalChanged(signal);
			this.signal = signal;
		}
	}

	public int getSignal() {
		return signal;
	}

	public void setSignalListener(SignalListener listener) {
		this.listner = listener;
	}

	public SignalListener getListener() {
		return this;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

}
