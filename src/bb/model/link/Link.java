package bb.model.link;

import bb.model.interfaces.SignalListener;

/**
 * Link
 * 
 * @author wall
 *
 */
public class Link {
	/**
	 * End point
	 */
	public EndPoint APoint;
	public EndPoint BPoint;
	/**
	 * internal nodes of a link
	 */
	private Node[] nodes;

	/**
	 * Link
	 * 
	 * @param x
	 *            point end A:X
	 * @param y
	 *            point end A:Y
	 * @param endX
	 *             point end B:X
	 * @param endY
	 *             point end B:X
	 */
	public Link(int Ax, int Ay, int Bx, int By) {
		APoint = new EndPoint(Ax, Ay);
		BPoint = new EndPoint(Bx, By);
		// set A and B to get the same signal
		APoint.setSignalListener(new SignalListener() {

			@Override
			public void signalChanged(int signal) {
				BPoint.signal = signal;
			}
		});
		BPoint.setSignalListener(new SignalListener() {

			@Override
			public void signalChanged(int signal) {
				APoint.signal = signal;
			}
		});
	}

	public void setNodes(Node... node) {
		if (node.length > 0)
			nodes = new Node[node.length];
		for (int i = 0; i < node.length; i++) {
			nodes[i] = node[i];
		}
	}

	public EndPoint getAPoint() {
		return APoint;
	}

	public void setAPoint(EndPoint aPoint) {
		APoint = aPoint;
	}

	public EndPoint getBPoint() {
		return BPoint;
	}

	public void setBPoint(EndPoint bPoint) {
		BPoint = bPoint;
	}

	public Node[] getNodes() {
		return nodes;
	}

}
