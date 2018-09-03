package bb.view.implementation;

import bb.model.interfaces.WorldToViewI;

public class TestWorldToView implements WorldToViewI {

	@Override
	public double convertX(double x) {
		return x*2;
	}

	@Override
	public double convertY(double y) {
		return y*2;
	}

	@Override
	public double invertX(double view_x) {
		return view_x/2;
	}

	@Override
	public double invertY(double view_y) {
		return view_y/2;
	}

}
