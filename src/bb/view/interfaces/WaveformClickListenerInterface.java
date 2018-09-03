package bb.view.interfaces;

import bb.view.implementation.WaveformCanvas;
import bb.model.interfaces.ThreeValuedLogic;

public interface WaveformClickListenerInterface {

	/** Receive a click event on a {@link WaveformCanvas}. 
	 * 
	 * @param step   The simulation step at which the click happened. Precondition: step > 0
	 * @param value The value of the click 
	 */
	void clickOnWaveForm(int step, ThreeValuedLogic value);

}
