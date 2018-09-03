package bb.view.implementation;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;

import javax.swing.JPanel;

import bb.model.components.Component;
import bb.model.components.Port;
import bb.model.interfaces.Controller;
import bb.model.interfaces.WorldToViewI;
import bb.model.link.Link;

/**
 * 绘制面板，将页面、数据和控制器结合在一起的地方
 * combine the view, mode and controller
 * @author wall
 *
 */
public class MyCircuitDiagramPanel extends JPanel {
	private static final long serialVersionUID = 2016111119231L;
	private List<Component> modes;
	private List<Link> links;
	private WorldToViewI worldToView;
	private Controller mController;

	public MyCircuitDiagramPanel(WorldToViewI worldToView, List<Component> modes, List<Link> links) {
		// get mode M
		this.modes = modes;
		this.links = links;
		this.worldToView = worldToView;
		checkLinkedStatus();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();
		// 将数据传入控制器adapter，数据不关心画到哪里和怎么画 M->C
		// the data of mode to controller
		mController = new Controller(g2d, worldToView, modes, links);
		// 将adapter与界面绑定，adapter处理数据并将结果传给界面 V<-C->M
		// controller to view
		CircuitDiagramView view = new CircuitDiagramView(mController);
		// 界面通过adapter发起绘制命令，它不必关心要画成什么样的 V->C
		// view to controller
		view.drawView();
	}

	public void reSet() {
		for (int i = 0; i < modes.size(); i++) {
			modes.get(i).reSet();
		}
	}

	/**
	 * 检查所有空间和导线的连接关系并绑定信号监听器
	 * check the relationship between the ends of links and the ports of components
	 */
	public void checkLinkedStatus() {
		if (links != null && links.size() > 0) {
			for (int i = 0; i < modes.size(); i++) {
				// compare all ends of links with the ports of components
				List<Port> ports = modes.get(i).getPorts();
				if (ports.size() > 1) {// AND/OR/NOR gate
					Port a = ports.get(0);
					Port b = ports.get(1);
					// just 2 ports
					Port c = null;
					// if there is the third port 
					if (ports.size() == 3)
						c = ports.get(2);
					// check it connected or not  每根导线和所有控件进行比对，找到连接点
					for (int j = 0; j < links.size(); j++) {
						Link link = links.get(j);
						link.APoint.isLinked(a);
						link.APoint.isLinked(b);
						link.BPoint.isLinked(a);
						link.BPoint.isLinked(b);
						if (c != null) {
							link.APoint.isLinked(c);
							link.BPoint.isLinked(c);
						}
					}
				} else if (ports.size() == 1) {
					Port a = ports.get(0);
					for (int j = 0; j < links.size(); j++) {
						Link link = links.get(j);
						link.APoint.isLinked(a);
						link.BPoint.isLinked(a);
					}
				}
				System.out.println("binded one component with a link!");
			}
		}
		/*
		for (int i = 0; i < modes.size(); i++) {
				// 控件和所有控件端点比较，确定连接点
				List<Port> ports = modes.get(i).getPorts();
				if (ports.size() > 1) {
					Port a = ports.get(0);
					Port b = ports.get(1);
					// 有可能只有两个端口
					Port c = null;
					if (ports.size() == 3)
						c = ports.get(2);
					// 和除自己以外的所有控件进行比对
					for (int j = i; j < modes.size(); j++) {
						if (j == i)// 不和自己比较
							continue;
						// 控件和所有导线端点比较，确定连接点 ,妈蛋三层for循环了，汗~~~
						List<Port> ports2 = modes.get(j).getPorts();
						for (int k = 0; k < ports2.size(); k++) {
							a.isLinked(null, ports2.get(k));
							b.isLinked(null, ports2.get(k));
							if (c != null)
								c.isLinked(null, ports2.get(k));
						}
					}
				} else if (ports.size() == 1) {
					Port a = ports.get(0);
					// 和除自己以外的所有控件进行比对
					for (int j = i; j < modes.size(); j++) {
						if (j == i)// 不和自己比较
							continue;
						// 控件和所有导线端点比较，确定连接点 ,妈蛋三层for循环了，汗~~~
						List<Port> ports3 = modes.get(j).getPorts();
						for (int k = 0; k < ports3.size(); k++) {
							a.isLinked(null, ports3.get(k));
						}
					}
				}
			}*/
		}
}
