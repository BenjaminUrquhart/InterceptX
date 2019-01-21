package net.intercept.gui;

public class InterceptX {
	
	public static STDIN enable() {
		return new Window().getSTDIN();
	}
	public static void main(String[] args) {
		enable();
	}
}
