package bb.model.gate;

import bb.model.interfaces.ComponentKind;

public class Adder extends ComponentKind {

	@Override
	public int compute(int... enter) {
		// add logic
		return enter[0] ^ enter[1];
	}

}
