/**
 * 
 */
package bb.simulator.implementation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import bb.view.implementation.FrameView;


/**
 * @author wall
 *
 */
public class Clock extends Thread{
	Timer timer;
	SimulationEventQueue myQ ;
	FrameView myPanel;
	public Clock(SimulationEventQueue myQ,FrameView myPanel)
	{
		this.myQ = myQ;
		this.myPanel =myPanel;
	}

	boolean isFirst =true;
	public   void ActionPerformer(  ) {
		
		Timer timer = new Timer(100, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tick();
            }
        });
		timer.start();
	}
		private  void tick() {
			if(!myQ.isEmpty())
			{
				if(isFirst)
				{
					myQ.Donow();
					isFirst =false;
				}
				else
				{
					myPanel.repaint();
					myQ.doNextEvent();
				}
			}
			else
			{
				//timer.stop();
			}
		} 
}
