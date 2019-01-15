package net.intercept.gui;

import java.io.InputStream;

public class InterceptX {
	
	public static InputStream enable() {
		return new Window().getSTDIN();
	}
	public static void main(String[] args) {
		enable();
	}
}
