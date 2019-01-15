package net.intercept.gui;

import java.io.IOException;
import java.io.OutputStream;

public class STDOUT extends OutputStream{

	private Drawer drawer;
	
	protected STDOUT(Drawer drawer) {
		this.drawer = drawer;
	}
	@Override
	public void write(int b) throws IOException {
		drawer.appendToBuff((char)b);
	}

}
