package bb.model.gate;

import bb.model.interfaces.ComponentKind;

public class AndGate extends ComponentKind {
	@Override
	public int compute(int... enter) {
		// and logic
		return enter[0] & enter[1];
	}
}
