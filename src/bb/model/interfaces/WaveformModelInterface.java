package bb.model.interfaces;

import java.util.Iterator;

/** A model for a waveforms. */
public interface WaveformModelInterface {
	public interface WaveformEvent {
		int getTimeStep() ;
		ThreeValuedLogic getValue() ;
	}
	
	/** Return an iterator representing the values of the waveform.
	 * <p> A waveform is a function from a set of simulation times to
	 * a set of values. The set of times are a set of integers running
	 * from 0 up to some maximum.  The set of values from the enumeration
	 * {@link ThreeValuedLogic}.  This function can be represented by an ordered
	 * set of events where the value changes.  The iterator returned from this
	 * method should return the elements of such a set.  The iterator must return
	 * the events in an order that is consistent with the time ordering. (I.e. if
	 * event x has a smaller time than event y, then event x must be delivered
	 * by the iterator before event y.)   The value at each point in time is the
	 * value of the latest event with an equal or smaller time. For times before
	 * the time of the first event, the value is undefined.
	 * <p>
	 * If two or more events have the same time, the value is that of the
	 * last event in the sequence.
	 * <p>
	 * It is permissible for two adjacent events to have the same value. 
	 * @return An iterator giving a sequence of {@link WaveformEvent}s ordered
	 * by time.
	 */
	public Iterator<WaveformEvent> getIterator() ;
	
	/** Return a name to display */
	public String getName() ;
}
