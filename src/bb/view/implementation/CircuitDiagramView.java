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
	 * ���·ͼ�ṩAdapter ��·ͼ���Ƶ���ʽ��Adapter����
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
