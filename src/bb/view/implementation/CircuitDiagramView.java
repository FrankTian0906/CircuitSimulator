package bb.view.implementation;

import javax.swing.JFrame;

import bb.model.interfaces.Controller;

/**
 * Circuit diagram
 * 
 * @author wall
 *
 */
public class CircuitDiagramView extends JFrame {
	private static final long serialVersionUID = 201611080019L;
	private Controller mController;

	/**
	 * 像电路图提供Adapter 电路图绘制的样式由Adapter决定
	 * provide this circuit diagram to controller
	 * @param adapter
	 */
	public CircuitDiagramView(Controller controller) {
		this.mController = controller;
	}

	public void drawView() {
		mController.drawAll();
	}

}
