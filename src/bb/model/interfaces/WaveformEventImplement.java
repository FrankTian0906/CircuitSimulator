/**
 * 
 */
package bb.model.interfaces;

import bb.model.interfaces.WaveformModelInterface.WaveformEvent;

/**
 * @author Frank Tian
 *
 */
public class WaveformEventImplement implements WaveformEvent{
	private int time;
	private ThreeValuedLogic value;
	/* (non-Javadoc)
	 * @see bb.model.interfaces.WaveformModelInterface.WaveformEvent#getTimeStep()
	 */
	@Override
	public int getTimeStep() {
		// TODO Auto-generated method stub
		return time;
	}

	/* (non-Javadoc)
	 * @see bb.model.interfaces.WaveformModelInterface.WaveformEvent#getValue()
	 */
	@Override
	public ThreeValuedLogic getValue() {
		// TODO Auto-generated method stub
		return value;
	}
	public void SetTime(int time)
	{
		this.time = time;
	}
	public void SetValue(ThreeValuedLogic value)
	{
		this.value = value;
	}
}
