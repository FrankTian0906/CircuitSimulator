/**
 * 
 */
package bb.view.implementation;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * the entrance 
 * @author wall
 *
 */
public class TestMain {
	

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				FrameView f = new FrameView();
				f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				f.setBounds(50, 40, 600, 600);
				f.setTitle("Circuit Simulation System");
				f.setVisible(true);
				/*f.links.get(0).APoint.setSignal(1);
				f.links.get(1).APoint.setSignal(0);
				f.links.get(3).APoint.setSignal(0);*/
			}
		});
	}
}