package net.intercept.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.io.InputStream;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Window extends JFrame {

	private Drawer canvas;
	
	public void paint(Graphics g) {
		canvas.paint(g);
	}
	
	protected Window() {
		this.setVisible(false);
		this.setTitle("Intercept");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setBackground(Color.BLACK);
		this.setSize(800, 600);
		this.getContentPane().add(canvas = new Drawer(this));
		this.getContentPane().validate();
		this.setVisible(true);
		this.requestFocus();
		this.repaint();
	}
	protected InputStream getSTDIN() {
		return canvas.getSTDIN();
	}
}
