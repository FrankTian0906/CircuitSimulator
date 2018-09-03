package bb.model.gate;

import bb.model.interfaces.ComponentKind;

public class NotGate extends ComponentKind {
	
	@Override
	public int compute(int... enter) {
		//invert logic
		return enter[0] == 0 ? 1 : 0;
	}

}
