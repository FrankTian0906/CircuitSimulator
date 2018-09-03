/**
 * 
 */
package bb.model.interfaces;

import java.util.Iterator;
import java.util.TreeMap;

/**
 * @author wall
 *
 */
public class WaveformModel implements WaveformModelInterface {
	private String name;
	TreeMap<Integer,WaveformEvent> waveEvents= new  TreeMap<Integer,WaveformEvent>();
	/* (non-Javadoc)
	 * @see bb.model.interfaces.WaveformModelInterface#getIterator()
	 */
	@Override
	public Iterator<WaveformEvent> getIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see bb.model.interfaces.WaveformModelInterface#getName()
	 */
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	
	public void addEvent(WaveformEvent ev)
	{
		waveEvents.put(ev.getTimeStep(),ev);
	}
}
