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
		System.out.print((int)c + " ");
		drawer.append(c);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_LEFT: drawer.decrementPos();
		case KeyEvent.VK_RIGHT: drawer.incrementPos();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {}
}
