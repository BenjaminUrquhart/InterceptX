package net.intercept.gui;

import java.awt.event.KeyEvent;

public class KeyListener implements java.awt.event.KeyListener{

	private Drawer drawer;
	
	protected KeyListener(Drawer drawer) {
		this.drawer = drawer;
	}
	@Override
	public void keyTyped(KeyEvent e) {
		char c = e.getKeyChar();
		System.err.print((int)c + " ");
		drawer.append(c);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		System.err.print(e.getKeyCode() + " ");
		switch(e.getKeyCode()) {
		case 37: drawer.decrementPos(); break;
		case 38: drawer.historyUp(); break;
		case 39: drawer.incrementPos(); break;
		case 40: drawer.historyDown(); break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {}
}
