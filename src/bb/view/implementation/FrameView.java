/**
 * 
 */
package bb.view.implementation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import bb.model.components.Component;
import bb.model.components.Component_And;
import bb.model.components.Component_Not;
import bb.model.components.Component_Or;
import bb.model.components.Component_input;
import bb.model.components.Component_output;
import bb.model.components.Port;
import bb.model.interfaces.OrientationE;
import bb.model.interfaces.WorldToViewI;
import bb.model.link.Link;

/**
 * Frame
 * 
 * @author wall
 *
 */
public class FrameView extends JFrame {
	private WorldToViewI worldToView = new TestWorldToView();
	public List<Component> modes;
	public List<Link> links;

	private Component isSelectedC = null;
	private Link isSelectedL = null;
	private boolean isComponent = true;
	private int isSelectnumber = -1;
	private boolean isEndA = true;
	private DragStatus status = DragStatus.Ready;
	private int comPointX = 0, comPointY = 0;
	private int mousePointX = 0, mousePointY = 0;

	private static final long serialVersionUID = -7277812986638623077L;

	private JMenuBar menuBar;
	private JMenuItem andGate;
	private JMenuItem orGate;
	private JMenuItem notGate;
	private JMenuItem input;
	private JMenuItem output;
	private JMenuItem link;
	private JPanel panel;
	private JPopupMenu jpComponent;
	private JPopupMenu jpLink;

	// Main Frame
	public FrameView() {
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		andGate = new JMenuItem("AND");
		menuBar.add(andGate);
		andGate.addActionListener(new MenuListener("AND"));
		orGate = new JMenuItem("OR");
		menuBar.add(orGate);
		orGate.addActionListener(new MenuListener("OR"));
		notGate = new JMenuItem("NOT");
		menuBar.add(notGate);
		notGate.addActionListener(new MenuListener("NOT"));
		input = new JMenuItem("INPUT");
		input.addActionListener(new MenuListener("INPUT"));
		menuBar.add(input);
		output = new JMenuItem("OUTPUT");
		output.addActionListener(new MenuListener("OUTPUT"));
		menuBar.add(output);
		link = new JMenuItem("Link");
		menuBar.add(link);
		link.addActionListener(new MenuListener("LINK"));
		JMenuItem run = new JMenuItem("RUN");
		menuBar.add(run);
		run.addActionListener(new MenuListener("RUN"));

		JMenuItem rotation = new JMenuItem("Rotation");
		rotation.addActionListener(new ClickMenuListener("rotation"));
		JMenuItem deleteC = new JMenuItem("Delete");
		deleteC.addActionListener(new ClickMenuListener("delete"));
		jpComponent = new JPopupMenu();
		jpComponent.add(rotation);
		jpComponent.add(deleteC);

		JMenuItem deleteL = new JMenuItem("Delete");
		deleteL.addActionListener(new ClickMenuListener("delete"));
		jpLink = new JPopupMenu();
		jpLink.add(deleteL);

		panel = setData();
		panel.addMouseListener(new Mouse());
		panel.addMouseMotionListener(new Mouse());
		this.add(panel);
	}

	private JPanel setData() {
		/**
		 * 在这里组织控件和导线的位置数据<br>
		 * 按你需要的逻辑往里填数据就行了 the panel for drawing all of components and links
		 */
		modes = new ArrayList<Component>();
		links = new ArrayList<Link>();
		/*
		 * links.add(new Link(30, 60, 120, 60)); links.add(new Link(30, 70, 60,
		 * 70)); modes.add(new Component_Not(80, 70, OrientationE.EAST));
		 * links.add(new Link(100, 70, 120, 70)); modes.add(new
		 * Component_And(140, 65, OrientationE.EAST)); modes.add(new
		 * Component_Or(180, 70, OrientationE.EAST)); links.add(new Link(160,
		 * 75, 160, 90));
		 */
		// modes
		return new MyCircuitDiagramPanel(worldToView, modes, links);
	}

	// RUN operation for getting the value
	public void getResult() {
		if (panel instanceof MyCircuitDiagramPanel) {
			((MyCircuitDiagramPanel) panel).checkLinkedStatus();
			((MyCircuitDiagramPanel) panel).reSet();
		}
		for (int i = 0; i < modes.size() / 2; i++) {
			for (Component com : modes) {
				com.updateOut();
			}
		}
		// if (modes.get(modes.size() - 1) instanceof Component_output) {
		// // 输出端只有一个输入端口，而且没有逻辑门。
		// modes.get(modes.size() - 1).portA.getSignal();
		// } else {
		// modes.get(modes.size() - 1).portQ.getSignal();
		// }
		repaint();
	}

	class MenuListener implements ActionListener {
		private String gate;

		public MenuListener(String gate) {
			this.gate = gate;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.
		 * ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			switch (gate) {
			case "AND":
				modes.add(new Component_And(20, 20, OrientationE.EAST));
				break;
			case "OR":
				modes.add(new Component_Or(20, 20, OrientationE.EAST));
				break;
			case "NOT":
				modes.add(new Component_Not(20, 20, OrientationE.EAST));
				break;
			case "INPUT":
				modes.add(0, new Component_input(20, 20, OrientationE.EAST));
				break;
			case "OUTPUT":
				modes.add(modes.size() - 1, new Component_output(20, 20, OrientationE.EAST));
				break;
			case "LINK":
				links.add(new Link(10, 10, 30, 10));
				break;
			case "RUN":
				getResult();
				break;
			}
			repaint();
		}
	}

	// right click menu
	class ClickMenuListener implements ActionListener {
		private String menu;

		public ClickMenuListener(String menu) {
			this.menu = menu;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.
		 * ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			switch (menu) {
			// rotation
			case "rotation":
				switch (isSelectedC.d) {
				case EAST:
					isSelectedC.d = OrientationE.SOUTH;
					break;
				case SOUTH:
					isSelectedC.d = OrientationE.WEST;
					break;
				case NORTH:
					isSelectedC.d = OrientationE.EAST;
					break;
				case WEST:
					isSelectedC.d = OrientationE.NORTH;
					break;
				}
				// set the position of a port
				if (isSelectedC instanceof Component_Not)
					isSelectedC.computePortPosition(true, isSelectedC.d);
				else if (isSelectedC instanceof Component_input)
					isSelectedC.computePutPosition(true, isSelectedC.d);
				else if (isSelectedC instanceof Component_output)
					isSelectedC.computePutPosition(false, isSelectedC.d);
				else
					isSelectedC.computePortPosition(false, isSelectedC.d);
				break;
			// delete
			case "delete":
				if (isComponent)
					modes.remove(isSelectnumber);
				else
					links.remove(isSelectnumber);
				break;
			}
			isSelectnumber = -1;
			isComponent = true;
			isSelectedC = null;
			isSelectedL = null;
			repaint();
		}

	}

	// double click(set value) and right click(menu)
	class Mouse implements MouseListener, MouseMotionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
		 */
		@Override
		public void mouseClicked(MouseEvent e) {
			int x = e.getX() / 2;
			int y = e.getY() / 2;
			if (e.getButton() == MouseEvent.BUTTON3) {
				// TODO Auto-generated method stub

				for (int i = 0; i < modes.size(); i++) {
					// System.out.println("I: " + i + " x = " +
					// modes.get(i).getX() + " y = " + modes.get(i).getY() );

					if ((x >= modes.get(i).x - 10) && x <= (modes.get(i).x + 10) && y >= (modes.get(i).y - 10) && y <= (modes.get(i).y + 10)) {
						jpComponent.show(panel, e.getX(), e.getY());
						isSelectnumber = i;
						isSelectedC = modes.get(i);
						break;
					}
				}
				for (int i = 0; i < links.size(); i++) {
					if ((x >= links.get(i).APoint.getX() - 1) && (x <= links.get(i).APoint.getX() + 1) && (y >= links.get(i).APoint.getY() - 1) && (y <= links.get(i).APoint.getY() + 1)) {
						jpLink.show(panel, e.getX(), e.getY());
						isComponent = false;
						isSelectnumber = i;
						isSelectedL = links.get(i);
						break;
					}
					if ((x >= links.get(i).BPoint.getX() - 1) && (x <= links.get(i).BPoint.getX() + 1) && (y >= links.get(i).BPoint.getY() - 1) && (y <= links.get(i).BPoint.getY() + 1)) {
						jpLink.show(panel, e.getX(), e.getY());
						// System.out.println("Link:" + i + " !");
						isComponent = false;
						isSelectnumber = i;
						isSelectedL = links.get(i);
						break;
					}
				}

			}
			if (e.getClickCount() == 2) {
				for (int i = 0; i < modes.size(); i++) {
					if ((x >= modes.get(i).x - 10) && x <= (modes.get(i).x + 10) && y >= (modes.get(i).y - 10) && y <= (modes.get(i).y + 10) && modes.get(i) instanceof Component_input) {
						isSelectnumber = i;
						isSelectedC = modes.get(i);
						int inputvalue = Integer.parseInt(JOptionPane.showInputDialog("Please input a value"));
						isSelectedC.portQ.setSignal(inputvalue);
						repaint();
						break;
					}
				}
				isSelectedC = null;
				isSelectnumber = -1;
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
		 */
		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
		 */
		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		/*
		 * (non-Javadoc) the first step for dragging a component or a link
		 * 
		 * @see
		 * java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
		 */
		@Override
		public void mousePressed(MouseEvent e) {
			if (status == DragStatus.Ready) {
				status = DragStatus.Dragging;
				int x = e.getX() / 2;
				int y = e.getY() / 2;

				// is it a component?
				for (int i = 0; i < modes.size(); i++) {
					Component c = modes.get(i);
					System.out.println("I: " + i + " x = " + c.x + " y = " + c.y);
					if ((x >= c.x - 10) && x <= (c.x + 10) && y >= (c.y - 10) && y <= (c.y + 10)) {
						System.out.println("component:" + i + " !");
						isSelectedC = c;
						comPointX = isSelectedC.x;
						comPointY = isSelectedC.y;
						break;
					}
				}

				// is it a link?
				for (int i = 0; i < links.size(); i++) {
					if ((x >= links.get(i).APoint.getX() - 1) && (x <= links.get(i).APoint.getX() + 1) && (y >= links.get(i).APoint.getY() - 1) && (y <= links.get(i).APoint.getY() + 1)) {
						System.out.println("linkA:" + i + " !");
						isSelectedL = links.get(i);
						isEndA = true;
						break;
					}
					if ((x >= links.get(i).BPoint.getX() - 1) && (x <= links.get(i).BPoint.getX() + 1) && (y >= links.get(i).BPoint.getY() - 1) && (y <= links.get(i).BPoint.getY() + 1)) {
						System.out.println("linkB:" + i + " !");
						isSelectedL = links.get(i);
						isEndA = false;
						break;
					}
				}
				mousePointX = x;
				mousePointY = y;
				// if (isSelectedC instanceof Component_input)
				// ((Component_input) isSelectedC).setSignal(1);
			}
		}

		/*
		 * (non-Javadoc) the final step for dragging a component or a link
		 * 
		 * @see
		 * java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
		 */
		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			if (status == DragStatus.Dragging) {
				if (status == DragStatus.Dragging) {
					if (isSelectedL != null) {
						for (int i = 0; i < modes.size(); i++) {
							List<Port> ports = modes.get(i).getPorts();
							for (int j = 0; j < ports.size(); j++) {
								// System.out.println("Gate: " + i + " ,Port:" +
								// j);
								if (((ports.get(j).enterX - 5) < e.getX() / 2) && ((ports.get(j).enterX + 5) > e.getX() / 2) && ((ports.get(j).enterY - 5) < e.getY() / 2) && ((ports.get(j).enterY + 5) > e.getY() / 2)) {
									if (isEndA) {
										isSelectedL.APoint.setPosition(ports.get(j).enterX, ports.get(j).enterY);
										System.out.println("connectA!");
									} else {
										isSelectedL.BPoint.setPosition(ports.get(j).enterX, ports.get(j).enterY);
										System.out.println("connectB!");
									}
									break;
								}
							}
						}
						repaint();
					}
				}
				status = DragStatus.Ready;
				isSelectedC = null;
				isSelectedL = null;
			}
		}

		/*
		 * (non-Javadoc) the second step for dragging a component or a link
		 * 
		 * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.
		 * MouseEvent)
		 */
		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
			if (status == DragStatus.Dragging && isSelectedC != null) {

				isSelectedC.x = comPointX + e.getX() / 2 - mousePointX;
				isSelectedC.y = comPointY + e.getY() / 2 - mousePointY;

				if (isSelectedC instanceof Component_Not)
					isSelectedC.computePortPosition(true, isSelectedC.d);
				else if (isSelectedC instanceof Component_input)
					isSelectedC.computePutPosition(true, isSelectedC.d);
				else if (isSelectedC instanceof Component_output)
					isSelectedC.computePutPosition(false, isSelectedC.d);
				else
					isSelectedC.computePortPosition(false, isSelectedC.d);
			}

			if (status == DragStatus.Dragging && isSelectedL != null) {
				// System.out.println((comPointX + e.getX()- mousePointX) + " +
				// " + (comPointY + e.getY() - mousePointY));
				if (isEndA) {
					isSelectedL.APoint.setX(e.getX() / 2);
					isSelectedL.APoint.setY(e.getY() / 2);
				} else {
					isSelectedL.BPoint.setX(e.getX() / 2);
					isSelectedL.BPoint.setY(e.getY() / 2);
				}
			}
			repaint();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.
		 * MouseEvent)
		 */
		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub

		}
	}

	// drag states
	private enum DragStatus {

		Ready, Dragging
	}
}
