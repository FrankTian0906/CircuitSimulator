package bb.simulator.interfaces;

import bb.simulator.implementation.SimulationEventQueue;

public abstract class SimulationEvent implements Comparable<SimulationEvent> {
	
	final protected int simulationTime ;
	
	public SimulationEvent( int simulationTime) {
		this.simulationTime = simulationTime ;
	}

	abstract public void execute(SimulationEventQueue simulationEventQueue);
	
	@Override public int compareTo( SimulationEvent other ) {
		return this.simulationTime - other.simulationTime ;
	}

}
