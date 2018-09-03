package bb.model.components;

import java.awt.Color;
import java.util.List;

import bb.model.interfaces.ComponentKind;
import bb.model.interfaces.OrientationE;

/**
 * component including size,port, and logic
 * 
 * @author wall
 *
 */
public abstract class Component {
	/**
	 * height and width are 20 pixels
	 */
	public static final int HEIGHT = 20;
	// half length
	public static final int RADIUS = HEIGHT / 2;
	// quarter length
	public static final int HALF_RADIUS = RADIUS / 2;
	/**
	 * central position x
	 */
	public int x;
	/**
	 * central position y
	 */
	public int y;
	/**
	 * orientation
	 */
	public OrientationE d;
	/**
	 * the logic of a component
	 */
	public ComponentKind comKind;
	/**
	 * input portA
	 */
	public InPort portA;
	/**
	 * input portB
	 */
	public InPort portB;
	/**
	 * output portQ
	 */
	public OutPort portQ;
	/**
	 * component color
	 */
	public Color color;
	
	/**
	 * abstract, set the position of a port according to the type of a component
	 * <p>
	 * notice£º
	 * </p>
	 * fixed size
	 * 
	 * @param x
	 * @param y
	 * @param ori
	 */
	public abstract void setPort(int x, int y, OrientationE ori);

	/**
	 * abstract get all post of a component
	 * 
	 * @return
	 */
	public abstract List<Port> getPorts();

	/**
	 * compute the result from gate logic 
	 * 
	 * @param enter
	 * @return
	 */
	public int compute(int... enter) {
		return comKind.compute(enter);
	}

	/**
	 * update the value of output
	 */
	public void updateOut() {

	}
	
	public void setColor(Color color){
		this.color = color;
	}
	
	public void reSet() {
		if (portA != null)
			portA.reSet();
		if (portB != null)
			portB.reSet();
		if (portQ != null)
			portQ.reSet();
	}

	/**
	 * compute the position the  port of a AND/OR/ONT gate
	 * 
	 * @param isSingleIn
	 *            is there only one input port
	 * @param ori
	 *            orientation
	 */
	public void computePortPosition(boolean isSingleIn, OrientationE ori) {
		switch (ori) {
		case EAST:
			if (!isSingleIn) {
				portA.setPoint(x - RADIUS, y - HALF_RADIUS, x - 2 * RADIUS, y - HALF_RADIUS);
				portB.setPoint(x - RADIUS, y + HALF_RADIUS, x - 2 * RADIUS, y + HALF_RADIUS);
			} else {
				portA.setPoint(x - RADIUS, y, x - 2 * RADIUS, y);
			}
			portQ.setPoint(x + RADIUS, y, x + 2 * RADIUS, y);
			break;
		case WEST:
			if (!isSingleIn) {
				portA.setPoint(x + RADIUS, y - HALF_RADIUS, x + 2 * RADIUS, y - HALF_RADIUS);
				portB.setPoint(x + RADIUS, y + HALF_RADIUS, x + 2 * RADIUS, y + HALF_RADIUS);
			} else {
				portA.setPoint(x + RADIUS, y, x + 2 * RADIUS, y);
			}
			portQ.setPoint(x - RADIUS, y, x - 2 * RADIUS, y);
			break;
		case NORTH:
			if (!isSingleIn) {
				portA.setPoint(x - HALF_RADIUS, y + RADIUS, x - HALF_RADIUS, y + 2 * RADIUS);
				portB.setPoint(x + HALF_RADIUS, y + RADIUS, x + HALF_RADIUS, y + 2 * RADIUS);
			} else {
				portA.setPoint(x, y + RADIUS, x, y + 2 * RADIUS);
			}
			portQ.setPoint(x, y - RADIUS, x, y - 2 * RADIUS);
			break;
		case SOUTH:
			if (!isSingleIn) {
				portA.setPoint(x - HALF_RADIUS, y - RADIUS, x - HALF_RADIUS, y - 2 * RADIUS);
				portB.setPoint(x + HALF_RADIUS, y - RADIUS, x + HALF_RADIUS, y - 2 * RADIUS);
			} else {
				portA.setPoint(x, y - RADIUS, x, y - 2 * RADIUS);
			}
			portQ.setPoint(x, y + RADIUS, x, y + 2 * RADIUS);
			break;
		}
	}

	/**
	 * compute the position the  port of a input/output
	 * 
	 * @param isPutIn
	 * @param ori
	 */
	public void computePutPosition(boolean isInput, OrientationE ori) {
		switch (ori) {
		case EAST:
			if (!isInput) {
				portA.setPoint(x - RADIUS, y, x - 2 * RADIUS, y);
			} else {
				portQ.setPoint(x + RADIUS, y, x + 2 * RADIUS, y);
			}
			break;
		case WEST:
			if (!isInput) {
				portA.setPoint(x + RADIUS, y, x + 2 * RADIUS, y);
			} else {
				portQ.setPoint(x - RADIUS, y, x - 2 * RADIUS, y);
			}
			break;
		case NORTH:
			if (!isInput) {
				portA.setPoint(x, y + RADIUS, x, y + 2 * RADIUS);
			} else {
				portQ.setPoint(x, y - RADIUS, x, y - 2 * RADIUS);
			}
			break;
		case SOUTH:
			if (!isInput) {
				portA.setPoint(x, y - RADIUS, x, y - 2 * RADIUS);
			} else {
				portQ.setPoint(x, y + RADIUS, x, y + 2 * RADIUS);
			}
			break;
		}
	}
}
