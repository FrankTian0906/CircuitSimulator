package bb.model.gate;

import bb.model.interfaces.ComponentKind;

public class OrGate extends ComponentKind {

	@Override
	public int compute(int... enter) {
		// or logic
		return enter[0] | enter[1];
	}

}
