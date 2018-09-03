package bb.simulator.implementation;

import java.util.PriorityQueue;

import bb.simulator.interfaces.SimulationEvent;

public class SimulationEventQueue {
	
	private PriorityQueue<SimulationEvent> pq = new PriorityQueue<SimulationEvent>() ;
	
	
	public boolean isEmpty() {
		return pq.isEmpty() ;
	}
	
	/**
	 * Precondition: not isEmpty() 
	 */
	public void doNextEvent() {
		SimulationEvent ev = pq.remove() ;
		ev.execute( this ) ;
	}
	
	public void addEvent( SimulationEvent event ) {
		pq.add( event ) ;
	}
	public SimulationEvent GetNowEvent()
	{
		return pq.peek();
	}
	
	public void Donow()
	{
		SimulationEvent ev =pq.peek();
		ev.execute( this ) ;
		System.out.println("do now"+ "pq size" +pq.size());
	}
}
