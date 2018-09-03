package bb.model.interfaces;

import java.awt.Graphics2D;
import java.util.List;

import bb.model.components.Component;
import bb.model.components.Component_input;
import bb.model.components.Component_output;
import bb.model.components.Port;
import bb.model.gate.AndGate;
import bb.model.gate.NotGate;
import bb.model.gate.OrGate;
import bb.model.gate.PutGate;
import bb.model.link.EndPoint;
import bb.model.link.Link;
import bb.model.link.Node;
import bb.view.implementation.AWTDrawingAdapter;

/**
 * Adapter
 * 
 * @author wall
 *
 */
public class Controller extends AWTDrawingAdapter {
	private List<Component> data;
	private List<Link> links;

	/**
	 * Adapter get the data of mode，then shift it to what it would like be in View
	 * 
	 * @param graphics
	 * @param worldToView
	 * @param data
	 */
	public Controller(Graphics2D graphics, WorldToViewI worldToView, List<Component> data,
			List<Link> links) {
		super(graphics, worldToView);
		this.data = data;
		this.links = links;
		initPaint();
	}

	private void initPaint() {
		setFillColor(255, 255, 255);
		setLineColor(0, 0, 0);
		setLineWidth(1);
	}

	/**
	 * draw all components and links
	 */
	public void drawAll() {
		// components
		if (data != null && data.size() > 0)
			for (Component com : data) {
				drawItem(com);
			}
		// links
		if (links != null && links.size() > 0)
			drwaLink(links);
	}

	/**
	 * draw one component
	 * 
	 * @param com
	 */
	private void drawItem(Component com) {
		ComponentKind kind = com.comKind;
		if (kind instanceof AndGate) {
			drawAndShape(com.x, com.y, Component.HEIGHT, Component.HEIGHT, com.d);
		} else if (kind instanceof OrGate) {
			drawOrShape(com.x, com.y, Component.HEIGHT, Component.HEIGHT, com.d);
		} else if (kind instanceof NotGate) {
			drawNotShape(com.x, com.y, Component.HEIGHT, Component.HEIGHT, com.d);
		} else if (kind instanceof PutGate) {
			drawRect(com.x, com.y, Component.HEIGHT, Component.HEIGHT);
		} 
		/**else if (kind instanceof PutGate) {
			drawRect(com.x, com.y, Component.HEIGHT, Component.HEIGHT);
			drawText("PutOut", com.x, com.y, 5, 5);
		}*/
		drawPortLine(com.getPorts());
		
		if(com instanceof Component_output){
			drawText(com.portA.getSignal()+ "",com.x,com.y,5,5);
			drawText("output", com.x-5, com.y+5, 5, 5);
		}else
		if(com instanceof Component_input){
			drawText(com.portQ.getSignal()+ "",com.x,com.y,5,5);
			drawText("input", com.x-5, com.y+5, 5, 5);
		}else{
			drawText(com.portQ.getSignal()+ "",com.x,com.y,5,5);
		}
	}

	/**
	 * draw the port line for a component
	 * 
	 * @param ports
	 */
	private void drawPortLine(List<Port> ports) {
		for (int i = 0; i < ports.size(); i++) {
			Port p = ports.get(i);
			drawLine(p.baseX, p.baseY, p.enterX, p.enterY);
		}
	}

	/**
	 * draw link with internal nodes
	 * notice: too complex to compute the position of a node
	 * @param links
	 */
	private void drwaLink(List<Link> links) {
		for (int i = 0; i < links.size(); i++) {
			// 绘制每一条导线
			Link l = links.get(i);
			EndPoint a = l.getAPoint();
			EndPoint b = l.getBPoint();
			drawText(a.signal+"", a.x,a.y,5,10);
			drawText(b.signal+"", b.x,b.y,5,10);
			// 判断是否有中间节点，有的话绘制中间节点
			if (l.getNodes() == null || l.getNodes().length == 0) {
				drawLine(a.x, a.y, b.x, b.y);
			} else {
				Node[] nodes = l.getNodes();
				// 两端需要和导线起止点相连
				drawLine(a.x, a.y, nodes[0].x, nodes[0].y);
				drawLine(nodes[nodes.length - 1].x, nodes[nodes.length - 1].y, b.x, b.y);
				if (nodes.length > 1) {
					for (int j = 0; j < nodes.length - 1; j++) {
						drawLine(nodes[j].x, nodes[j].y, nodes[j + 1].x, nodes[j + 1].y);
						// 坐标标注，没用可以注释了
						drawText(nodes[j].x + "," + nodes[j].y, nodes[j].x, nodes[j].y, 5, 5);
					}
				}
			}
		}
	}

}
