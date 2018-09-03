package bb.model.gate;

import bb.model.interfaces.ComponentKind;

public class PutGate extends ComponentKind {
	@Override
	public int compute(int... enter) {
		// push
		return enter[0];
	}
}
